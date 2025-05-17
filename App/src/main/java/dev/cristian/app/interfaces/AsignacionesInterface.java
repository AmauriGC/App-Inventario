package dev.cristian.app.interfaces;

import dev.cristian.app.dto.asignacion.AsignacionActualizarDto;
import dev.cristian.app.dto.asignacion.AsignacionCrearDto;
import dev.cristian.app.dto.asignacion.AsignacionDetalleDto;
import dev.cristian.app.enums.EstatusAsignacion;

import java.util.List;

public interface AsignacionesInterface {
    AsignacionDetalleDto asignar(AsignacionCrearDto dto);

    AsignacionDetalleDto obtenerPorId(Long id);

    List<AsignacionDetalleDto> listarTodas();

    void actualizar(Long id, AsignacionActualizarDto dto);

    void eliminar(Long id);

    List<AsignacionDetalleDto> buscarPorUsuarioOLBien(Long idUsuario, Long idBien);

    List<AsignacionDetalleDto> filtrarPorEstatus(EstatusAsignacion estatus);
}
