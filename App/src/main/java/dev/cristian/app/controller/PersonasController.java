package dev.cristian.app.controller;

import dev.cristian.app.dto.personas.ExcelUploadResponse;
import dev.cristian.app.dto.personas.PersonasDetalleDto;
import dev.cristian.app.response.ApiResponse;
import dev.cristian.app.service.PersonasService;
import dev.cristian.app.service.interfaces.PersonasInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin("http://localhost:5173")
public class PersonasController {

    private final PersonasInterface personasService;
    private static final Logger logger = LoggerFactory.getLogger(PersonasService.class);

    public PersonasController(PersonasInterface personasService) {
        this.personasService = personasService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PersonasDetalleDto>>> listarPersonas() {
        return personasService.listarTodas();
    }

    @PostMapping("/cargar-excel")
    public ResponseEntity<ApiResponse<ExcelUploadResponse>> cargarExcel(
            @RequestParam("file") MultipartFile file) {
        return personasService.cargarExcel(file);
    }

    @PostMapping("/descargar-errores")
    public ResponseEntity<byte[]> descargarErrores(
            @RequestBody ExcelErrorRequest request) {
        ApiResponse<byte[]> response = personasService
                .generarArchivoErrores(request.getRegistrosFallidos(), request.getErrores())
                .getBody();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=errores.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response.getDatos());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ExcelErrorRequest {
        private List<Map<String, String>> registrosFallidos;
        private List<String> errores;
    }

    @PostMapping("/preview-excel")
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> previewExcel(
            @RequestParam("file") MultipartFile file) {
        try {
            // Validar que el archivo no esté vacío
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El archivo está vacío"));
            }

            // Validar extensión del archivo
            String filename = file.getOriginalFilename();
            if (filename == null ||
                    (!filename.endsWith(".xlsx") && !filename.endsWith(".xls"))) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Solo se permiten archivos Excel (.xlsx, .xls)"));
            }

            List<Map<String, String>> previewData = readExcelForPreview(file.getInputStream());
            return ResponseEntity.ok(ApiResponse.ok("Vista previa generada", previewData));
        } catch (Exception e) {
            logger.error("Error al generar vista previa: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al generar vista previa: " + e.getMessage()));
        }
    }

    public List<Map<String, String>> readExcelForPreview(InputStream is) throws IOException {
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
}