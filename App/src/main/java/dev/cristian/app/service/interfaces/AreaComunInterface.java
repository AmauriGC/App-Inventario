package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.areacomun.AreaComunActualizarDto;
import dev.cristian.app.dto.areacomun.AreaComunCrearDto;
import dev.cristian.app.dto.areacomun.AreaComunDetalleDto;
import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AreaComunInterface {
    ResponseEntity<ApiResponse<AreaComunDetalleDto>> crear(AreaComunCrearDto dto);

    ResponseEntity<ApiResponse<AreaComunDetalleDto>> obtenerPorId(Long id);

    ResponseEntity<ApiResponse<List<AreaComunDetalleDto>>> listarTodas();

    ResponseEntity<ApiResponse<AreaComunDetalleDto>> actualizar(Long id, AreaComunActualizarDto dto);

    ResponseEntity<ApiResponse<AreaComunDetalleDto>> eliminar(Long id);

    ResponseEntity<ApiResponse<List<AreaComunDetalleDto>>> buscarPorTipoYEstado(TipoDeArea tipo, EstatusAreaComun estatus);

    ResponseEntity<ApiResponse<List<AreaComunDetalleDto>>> buscarPorNombre(String nombre);

    ResponseEntity<ApiResponse<List<AreaComunDetalleDto>>> buscarPorEstado(EstatusAreaComun estatus);
}