package dev.cristian.app.mapper;

import dev.cristian.app.dto.asignacion.AsignacionCrearDto;
import dev.cristian.app.dto.asignacion.AsignacionDetalleDto;
import dev.cristian.app.entity.Asignaciones;
import dev.cristian.app.entity.Bien;
import dev.cristian.app.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AsignacionesMapper {

    public Asignaciones toEntity(AsignacionCrearDto dto, Usuario usuario, Bien bien) {
        Asignaciones asignacion = new Asignaciones();
        asignacion.setUsuario(usuario);
        asignacion.setBien(bien);
        asignacion.setEstatusAsignacion(dto.getEstatusAsignacion());
        asignacion.setFechaDeAsignacion(dto.getFechaDeAsignacion());
        asignacion.setFechaDeRevocacion(dto.getFechaDeRevocacion());
        return asignacion;
    }

    public AsignacionDetalleDto toDetalleDto(Asignaciones asignacion) {
        AsignacionDetalleDto dto = new AsignacionDetalleDto();
        dto.setId(asignacion.getId());
        dto.setUsuarioId(asignacion.getUsuario().getId());
        dto.setBienId(asignacion.getBien().getId());
        dto.setEstatusAsignacion(asignacion.getEstatusAsignacion());
        dto.setFechaDeAsignacion(asignacion.getFechaDeAsignacion());
        dto.setFechaDeRevocacion(asignacion.getFechaDeRevocacion());
        return dto;
    }
}
