package dev.cristian.app.dto.personas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ExcelUploadResponse {
    private int totalRegistros;
    private int registrosExitosos;
    private int registrosFallidos;
    private List<String> errores;
    private List<Map<String, String>> registrosFallidosData;
    private String errorFileId;
    private boolean hasErrorFile;
}