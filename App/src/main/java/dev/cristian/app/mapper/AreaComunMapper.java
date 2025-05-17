package dev.cristian.app.mapper;

import dev.cristian.app.dto.areacomun.AreaComunActualizarDto;
import dev.cristian.app.dto.areacomun.AreaComunCrearDto;
import dev.cristian.app.dto.areacomun.AreaComunDetalleDto;
import dev.cristian.app.entity.AreaComun;
import org.springframework.stereotype.Component;

@Component
public class AreaComunMapper {

    public AreaComun toEntity(AreaComunCrearDto dto) {
        AreaComun area = new AreaComun();
        area.setNombreDelArea(dto.getNombreDelArea());
        area.setTipoDeArea(dto.getTipoDeArea());
        area.setEstatusAreaComun(dto.getEstatusAreaComun());
        return area;
    }

    public AreaComunCrearDto toCrearDto(AreaComun area) {
        AreaComunCrearDto dto = new AreaComunCrearDto();
        dto.setNombreDelArea(area.getNombreDelArea());
        dto.setTipoDeArea(area.getTipoDeArea());
        dto.setEstatusAreaComun(area.getEstatusAreaComun());
        return dto;
    }

    public AreaComunDetalleDto toDetalleDto(AreaComun area) {
        AreaComunDetalleDto dto = new AreaComunDetalleDto();
        dto.setId(area.getId());
        dto.setNombreDelArea(area.getNombreDelArea());
        dto.setTipoDeArea(area.getTipoDeArea());
        dto.setEstatusAreaComun(area.getEstatusAreaComun());
        return dto;
    }

    public AreaComunActualizarDto toActualizarDto(AreaComun area) {
        AreaComunActualizarDto dto = new AreaComunActualizarDto();
        dto.setNombreDelArea(area.getNombreDelArea());
        dto.setTipoDeArea(area.getTipoDeArea());
        dto.setEstatusAreaComun(area.getEstatusAreaComun());
        return dto;
    }

    public void actualizarDesdeDto(AreaComun area, AreaComunActualizarDto dto) {
        if (dto.getNombreDelArea() != null) {
            area.setNombreDelArea(dto.getNombreDelArea());
        }
        if (dto.getTipoDeArea() != null) {
            area.setTipoDeArea(dto.getTipoDeArea());
        }
        if (dto.getEstatusAreaComun() != null) {
            area.setEstatusAreaComun(dto.getEstatusAreaComun());
        }
    }
}
