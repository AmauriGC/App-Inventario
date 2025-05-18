package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.modelo.ModeloActualizarDto;
import dev.cristian.app.dto.modelo.ModeloCrearDto;
import dev.cristian.app.dto.modelo.ModeloDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ModeloInterface {
    ResponseEntity<ApiResponse<ModeloDetalleDto>> crear(ModeloCrearDto dto);

    ResponseEntity<ApiResponse<ModeloDetalleDto>> obtenerPorId(Long id);

    ResponseEntity<ApiResponse<List<ModeloDetalleDto>>> listarTodos();

    ResponseEntity<ApiResponse<ModeloDetalleDto>> actualizar(Long id, ModeloActualizarDto dto);

    ResponseEntity<ApiResponse<ModeloDetalleDto>> eliminar(Long id);

    ResponseEntity<ApiResponse<List<ModeloDetalleDto>>> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus);
}
