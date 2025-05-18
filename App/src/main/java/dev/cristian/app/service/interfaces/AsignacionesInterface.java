package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.asignacion.AsignacionActualizarDto;
import dev.cristian.app.dto.asignacion.AsignacionCrearDto;
import dev.cristian.app.dto.asignacion.AsignacionDetalleDto;
import dev.cristian.app.enums.EstatusAsignacion;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface AsignacionesInterface {
    ResponseEntity<ApiResponse<AsignacionDetalleDto>> asignar(AsignacionCrearDto dto);

    ResponseEntity<ApiResponse<AsignacionDetalleDto>> obtenerPorId(Long id);

    ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> listarTodas();

    ResponseEntity<ApiResponse<AsignacionDetalleDto>> actualizar(Long id, AsignacionActualizarDto dto);

    ResponseEntity<ApiResponse<AsignacionDetalleDto>> eliminar(Long id);

    ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> buscarPorUsuarioOBien(Long idUsuario, Long idBien);

    ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> filtrarPorEstatus(EstatusAsignacion estatus);

    ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> buscarAsignacionesActivasPorUsuario(Long usuarioId);

    ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> buscarAsignacionesActivasPorBien(Long bienId);

    ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> buscarAsignacionesPorUsuarioYRangoFechas(Long usuarioId, LocalDateTime inicio, LocalDateTime fin);
}