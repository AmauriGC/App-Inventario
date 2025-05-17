package dev.cristian.app.mapper;

import dev.cristian.app.dto.modelo.ModeloActualizarDto;
import dev.cristian.app.dto.modelo.ModeloCrearDto;
import dev.cristian.app.dto.modelo.ModeloDetalleDto;
import dev.cristian.app.entity.Modelo;
import org.springframework.stereotype.Component;

@Component
public class ModeloMapper {

    public Modelo toEntity(ModeloCrearDto dto) {
        Modelo modelo = new Modelo();
        modelo.setNombreDelModelo(dto.getNombreDelModelo());
        modelo.setEstatusMarcaModeloTipoDeBien(dto.getEstatusMarcaModeloTipoDeBien());
        return modelo;
    }

    public ModeloDetalleDto toDetalleDto(Modelo modelo) {
        ModeloDetalleDto dto = new ModeloDetalleDto();
        dto.setId(modelo.getId());
        dto.setNombreDelModelo(modelo.getNombreDelModelo());
        dto.setEstatusMarcaModeloTipoDeBien(modelo.getEstatusMarcaModeloTipoDeBien());
        return dto;
    }

    public ModeloActualizarDto toActualizarDto(Modelo modelo) {
        ModeloActualizarDto dto = new ModeloActualizarDto();
        dto.setNombreDelModelo(modelo.getNombreDelModelo());
        dto.setEstatusMarcaModeloTipoDeBien(modelo.getEstatusMarcaModeloTipoDeBien());
        return dto;
    }

    public void actualizarDesdeDto(Modelo modelo, ModeloActualizarDto dto) {
        if (dto.getNombreDelModelo() != null) {
            modelo.setNombreDelModelo(dto.getNombreDelModelo());
        }
        if (dto.getEstatusMarcaModeloTipoDeBien() != null) {
            modelo.setEstatusMarcaModeloTipoDeBien(dto.getEstatusMarcaModeloTipoDeBien());
        }
    }
}
