package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.marca.MarcaActualizarDto;
import dev.cristian.app.dto.marca.MarcaCrearDto;
import dev.cristian.app.dto.marca.MarcaDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarcaInterface {
    ResponseEntity<ApiResponse<MarcaDetalleDto>> crear(MarcaCrearDto dto);

    ResponseEntity<ApiResponse<MarcaDetalleDto>> obtenerPorId(Long id);

    ResponseEntity<ApiResponse<List<MarcaDetalleDto>>> listarTodas();

    ResponseEntity<ApiResponse<MarcaDetalleDto>> actualizar(Long id, MarcaActualizarDto dto);

    ResponseEntity<ApiResponse<MarcaDetalleDto>> eliminar(Long id);

    ResponseEntity<ApiResponse<List<MarcaDetalleDto>>> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus);
}
