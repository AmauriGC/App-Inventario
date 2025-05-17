package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.asignacion.AsignacionActualizarDto;
import dev.cristian.app.dto.asignacion.AsignacionCrearDto;
import dev.cristian.app.dto.asignacion.AsignacionDetalleDto;
import dev.cristian.app.enums.EstatusAsignacion;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface AsignacionesInterface {
    ResponseEntity<AsignacionDetalleDto> asignar(AsignacionCrearDto dto);

    ResponseEntity<AsignacionDetalleDto> obtenerPorId(Long id);

    ResponseEntity<List<AsignacionDetalleDto>> listarTodas();

    ResponseEntity<AsignacionDetalleDto> actualizar(Long id, AsignacionActualizarDto dto);

    ResponseEntity<AsignacionDetalleDto> eliminar(Long id);

    ResponseEntity<List<AsignacionDetalleDto>> buscarPorUsuarioOBien(Long idUsuario, Long idBien);

    ResponseEntity<List<AsignacionDetalleDto>> filtrarPorEstatus(EstatusAsignacion estatus);

    ResponseEntity<List<AsignacionDetalleDto>> buscarAsignacionesActivasPorUsuario(Long usuarioId);

    ResponseEntity<List<AsignacionDetalleDto>> buscarAsignacionesActivasPorBien(Long bienId);

    ResponseEntity<List<AsignacionDetalleDto>> buscarAsignacionesPorUsuarioYRangoFechas(Long usuarioId, LocalDateTime inicio, LocalDateTime fin);
}