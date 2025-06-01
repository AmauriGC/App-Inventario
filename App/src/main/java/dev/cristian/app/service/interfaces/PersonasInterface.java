package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.personas.ExcelUploadResponse;
import dev.cristian.app.dto.personas.PersonasDetalleDto;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PersonasInterface {

    ResponseEntity<ApiResponse<List<PersonasDetalleDto>>> listarTodas();

    ResponseEntity<ApiResponse<ExcelUploadResponse>> cargarExcel(MultipartFile file);

    ResponseEntity<ApiResponse<byte[]>> generarArchivoErrores(List<Map<String, String>> registrosFallidos, List<String> errores);
}