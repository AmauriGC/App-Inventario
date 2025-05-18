package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.bien.BienActualizarDto;
import dev.cristian.app.dto.bien.BienCrearDto;
import dev.cristian.app.dto.bien.BienDetalleDto;
import dev.cristian.app.enums.EstatusBien;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BienInterface {
    ResponseEntity<ApiResponse<BienDetalleDto>> crear(BienCrearDto dto);

    ResponseEntity<ApiResponse<BienDetalleDto>> obtenerPorId(Long id);

    ResponseEntity<ApiResponse<List<BienDetalleDto>>> listarTodos();

    ResponseEntity<ApiResponse<BienDetalleDto>> actualizar(Long id, BienActualizarDto dto);

    ResponseEntity<ApiResponse<BienDetalleDto>> eliminar(Long id);

    ResponseEntity<ApiResponse<List<BienDetalleDto>>> buscarPorAreaOMarca(Long idArea, Long idMarca);

    ResponseEntity<ApiResponse<List<BienDetalleDto>>> filtrarPorEstatus(EstatusBien estatus);
}
