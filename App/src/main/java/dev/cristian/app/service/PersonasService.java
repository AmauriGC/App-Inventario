package dev.cristian.app.service;

import dev.cristian.app.dto.personas.ExcelUploadResponse;
import dev.cristian.app.dto.personas.PersonasCreatDto;
import dev.cristian.app.dto.personas.PersonasDetalleDto;
import dev.cristian.app.entity.Personas;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.mapper.PersonasMapper;
import dev.cristian.app.repository.PersonaRepository;
import dev.cristian.app.response.ApiResponse;
import dev.cristian.app.service.interfaces.PersonasInterface;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonasService implements PersonasInterface {

    private static final Logger logger = LoggerFactory.getLogger(PersonasService.class);
    private static final List<String> REQUIRED_COLUMNS = Arrays.asList(
            "Nombre", "Apellidos", "CURP", "Teléfono", "Edad", "Correo"
    );

    private final PersonaRepository personaRepository;
    private final PersonasMapper personasMapper;

    public PersonasService(PersonaRepository personaRepository, PersonasMapper personasMapper) {
        this.personaRepository = personaRepository;
        this.personasMapper = personasMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<PersonasDetalleDto>>> listarTodas() {
        List<Personas> personas = personaRepository.findAll();

        if (personas.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay personas registradas");
        }

        List<PersonasDetalleDto> dtos = personas.stream()
                .map(personasMapper::toDetalleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok("Listado de personas obtenido", dtos));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<ExcelUploadResponse>> cargarExcel(MultipartFile file) {
        ExcelUploadResponse response = new ExcelUploadResponse();
        List<String> errores = new ArrayList<>();
        List<Map<String, String>> registrosFallidos = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // Validar encabezados
            List<String> headers = validarEncabezados(sheet);

            // Procesar filas
            procesarFilas(sheet, headers, response, errores, registrosFallidos);

            return ResponseEntity.ok(ApiResponse.ok("Archivo procesado con éxito", response));

        } catch (Exception e) {
            logger.error("Error al procesar Excel: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al procesar archivo: " + e.getMessage()));
        }
    }

    private List<String> validarEncabezados(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new IllegalArgumentException("El archivo no tiene encabezados");
        }

        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            String header = getCellValue(cell).trim();
            if (!header.isEmpty()) {
                headers.add(header);
            }
        }

        List<String> missingColumns = REQUIRED_COLUMNS.stream()
                .filter(col -> !headers.contains(col))
                .collect(Collectors.toList());

        if (!missingColumns.isEmpty()) {
            throw new IllegalArgumentException("Faltan columnas requeridas: " + String.join(", ", missingColumns));
        }

        return headers;
    }

    private void procesarFilas(Sheet sheet, List<String> headers,
                               ExcelUploadResponse response,
                               List<String> errores,
                               List<Map<String, String>> registrosFallidos) {

        Set<String> curpsProcesados = new HashSet<>();
        int totalRegistros = 0;
        int registrosExitosos = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || isRowEmpty(row)) continue;

            totalRegistros++;
            Map<String, String> rowData = extraerDatosFila(row, headers);

            try {
                validarYGuardarPersona(rowData, curpsProcesados);
                registrosExitosos++;
            } catch (Exception e) {
                manejarFilaFallida(rowData, errores, registrosFallidos, i + 1, e);
            }
        }

        response.setTotalRegistros(totalRegistros);
        response.setRegistrosExitosos(registrosExitosos);
        response.setRegistrosFallidos(totalRegistros - registrosExitosos);
        response.setErrores(errores);
        response.setRegistrosFallidosData(registrosFallidos);
    }

    // Métodos auxiliares (sin cambios)
    private Map<String, String> extraerDatosFila(Row row, List<String> headers) {
        Map<String, String> rowData = new LinkedHashMap<>();
        for (int j = 0; j < headers.size(); j++) {
            rowData.put(headers.get(j), j < row.getLastCellNum()
                    ? getCellValue(row.getCell(j))
                    : "");
        }
        return rowData;
    }

    private void validarYGuardarPersona(Map<String, String> rowData, Set<String> curpsProcesados) {
        PersonasCreatDto dto = new PersonasCreatDto();
        dto.setNombre(rowData.get("Nombre"));
        dto.setApellidos(rowData.get("Apellidos"));
        dto.setCurp(rowData.get("CURP").toUpperCase());
        dto.setTelefono(rowData.get("Teléfono"));
        dto.setCorreo(rowData.get("Correo").toLowerCase());

        // Validar edad
        String edadStr = rowData.get("Edad");
        try {
            long edad = Long.parseLong(edadStr);
            if (edad < 0 || edad > 120) {
                throw new IllegalArgumentException("La edad debe estar entre 0 y 120 años");
            }
            dto.setEdad(edad);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número válido");
        }

        // Validaciones adicionales
        validateRequiredFields(dto);
        validateCurpFormat(dto.getCurp());
        validateEmailFormat(dto.getCorreo());

        // Verificar duplicados en esta ejecución
        if (curpsProcesados.contains(dto.getCurp())) {
            throw new RecursoDuplicadoException("CURP duplicado en este archivo: " + dto.getCurp());
        }
        curpsProcesados.add(dto.getCurp());

        // Verificar duplicados en BD
        checkForDuplicates(dto);

        // Guardar en BD
        Personas persona = personasMapper.toEntity(dto);
        personaRepository.save(persona);
    }

    private void manejarFilaFallida(Map<String, String> rowData, List<String> errores,
                                    List<Map<String, String>> registrosFallidos,
                                    int rowNum, Exception e) {
        String errorMsg = "Fila " + rowNum + ": " + e.getMessage();
        errores.add(errorMsg);
        registrosFallidos.add(rowData);
        logger.error(errorMsg);
    }

    // Métodos auxiliares de validación
    private void validateRequiredFields(PersonasCreatDto dto) {
        if (dto.getNombre().isEmpty() || dto.getApellidos().isEmpty() ||
                dto.getCurp().isEmpty() || dto.getTelefono().isEmpty() ||
                dto.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son requeridos");
        }
    }

    private void validateCurpFormat(String curp) {
        if (!curp.matches("[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[A-Z0-9]{2}")) {
            throw new IllegalArgumentException("Formato de CURP inválido");
        }
    }

    private void validateEmailFormat(String email) {
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }
    }

    private void checkForDuplicates(PersonasCreatDto dto) {
        if (personaRepository.existsByCurp(dto.getCurp())) {
            throw new RecursoDuplicadoException("Ya existe una persona con el CURP: " + dto.getCurp());
        }
        if (personaRepository.existsByCorreo(dto.getCorreo())) {
            throw new RecursoDuplicadoException("Ya existe una persona con el correo: " + dto.getCorreo());
        }
        if (personaRepository.existsByTelefono(dto.getTelefono())) {
            throw new RecursoDuplicadoException("Ya existe una persona con el teléfono: " + dto.getTelefono());
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    @Override
    public ResponseEntity<ApiResponse<byte[]>> generarArchivoErrores(List<Map<String, String>> registrosFallidos, List<String> errores) {
        try {
            // Validar parámetros de entrada
            if (registrosFallidos == null || registrosFallidos.isEmpty()) {
                throw new IllegalArgumentException("No hay registros fallidos para generar el archivo");
            }

            if (errores == null || errores.size() != registrosFallidos.size()) {
                throw new IllegalArgumentException("La cantidad de errores no coincide con los registros fallidos");
            }

            // Generar el archivo Excel con el formato específico
            byte[] excelContent = generateErrorExcelWithFormat(registrosFallidos, errores);

            return ResponseEntity.ok()
                    .body(ApiResponse.ok("Archivo de errores generado con éxito", excelContent));
        } catch (Exception e) {
            logger.error("Error al generar archivo de errores: {}", e.getMessage());
            throw new RuntimeException("Error al generar archivo de errores: " + e.getMessage(), e);
        }
    }

    private byte[] generateErrorExcelWithFormat(List<Map<String, String>> failedRecords, List<String> errors) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Registros Fallidos");

            // Crear estilo para celdas de error
            CellStyle errorStyle = workbook.createCellStyle();
            Font errorFont = workbook.createFont();
            errorFont.setColor(IndexedColors.RED.getIndex());
            errorStyle.setFont(errorFont);

            // Crear encabezados en el orden específico
            String[] headers = {"Nombre", "Apellidos", "CURP", "Teléfono", "Edad", "Correo", "Error"};
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                if (i == headers.length - 1) { // Aplicar estilo solo a columna Error
                    cell.setCellStyle(errorStyle);
                }
            }

            // Llenar datos manteniendo el orden de columnas
            for (int i = 0; i < failedRecords.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Map<String, String> record = failedRecords.get(i);

                // Nombre
                row.createCell(0).setCellValue(record.get("Nombre"));
                // Apellidos
                row.createCell(1).setCellValue(record.get("Apellidos"));
                // CURP
                row.createCell(2).setCellValue(record.get("CURP"));
                // Teléfono
                row.createCell(3).setCellValue(record.get("Teléfono"));
                // Edad
                row.createCell(4).setCellValue(record.get("Edad"));
                // Correo
                row.createCell(5).setCellValue(record.get("Correo"));

                // Error - extraer solo el mensaje (sin "Fila X:")
                String errorMsg = errors.get(i);
                if (errorMsg.contains(":")) {
                    errorMsg = errorMsg.split(":", 2)[1].trim();
                }
                Cell errorCell = row.createCell(6);
                errorCell.setCellValue(errorMsg);
                errorCell.setCellStyle(errorStyle);
            }

            // Autoajustar columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private List<Map<String, String>> readExcelForPreview(InputStream is) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                throw new IllegalArgumentException("El archivo no tiene encabezados");
            }

            // Obtener nombres de columnas
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValue(cell));
            }

            // Leer datos (limitamos a 100 filas para la vista previa)
            int rowLimit = Math.min(sheet.getLastRowNum(), 100);
            for (int i = 1; i <= rowLimit; i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    String value = j < row.getLastCellNum() ?
                            getCellValue(row.getCell(j)) : "";
                    rowData.put(headers.get(j), value);
                }
                data.add(rowData);
            }
        }

        return data;
    }
}