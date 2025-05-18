package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.tipodebien.TipoDeBienActualizarDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienCrearDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TipoDeBienInterface {
    ResponseEntity<ApiResponse<TipoDeBienDetalleDto>> crear(TipoDeBienCrearDto dto);

    ResponseEntity<ApiResponse<TipoDeBienDetalleDto>> obtenerPorId(Long id);

    ResponseEntity<ApiResponse<List<TipoDeBienDetalleDto>>> listarTodos();

    ResponseEntity<ApiResponse<TipoDeBienDetalleDto>> actualizar(Long id, TipoDeBienActualizarDto dto);

    ResponseEntity<ApiResponse<TipoDeBienDetalleDto>> eliminar(Long id);

    ResponseEntity<ApiResponse<List<TipoDeBienDetalleDto>>> buscarPorTipoYEstado(TipoBien tipo, EstatusMarcaModeloTipoDeBien estatus);
}
