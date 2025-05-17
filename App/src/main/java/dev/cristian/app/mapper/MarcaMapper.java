package dev.cristian.app.mapper;

import dev.cristian.app.dto.marca.MarcaActualizarDto;
import dev.cristian.app.dto.marca.MarcaCrearDto;
import dev.cristian.app.dto.marca.MarcaDetalleDto;
import dev.cristian.app.entity.Marca;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {

    public Marca toEntity(MarcaCrearDto dto) {
        Marca marca = new Marca();
        marca.setNombreDeMarca(dto.getNombreDeMarca());
        marca.setEstatusMarcaModeloTipoDeBien(dto.getEstatusMarcaModeloTipoDeBien());
        return marca;
    }

    public MarcaDetalleDto toDetalleDto(Marca marca) {
        MarcaDetalleDto dto = new MarcaDetalleDto();
        dto.setId(marca.getId());
        dto.setNombreDeMarca(marca.getNombreDeMarca());
        dto.setEstatusMarcaModeloTipoDeBien(marca.getEstatusMarcaModeloTipoDeBien());
        return dto;
    }

    public MarcaActualizarDto toActualizarDto(Marca marca) {
        MarcaActualizarDto dto = new MarcaActualizarDto();
        dto.setNombreDeMarca(marca.getNombreDeMarca());
        dto.setEstatusMarcaModeloTipoDeBien(marca.getEstatusMarcaModeloTipoDeBien());
        return dto;
    }

    public void actualizarDesdeDto(Marca marca, MarcaActualizarDto dto) {
        if (dto.getNombreDeMarca() != null) {
            marca.setNombreDeMarca(dto.getNombreDeMarca());
        }
        if (dto.getEstatusMarcaModeloTipoDeBien() != null) {
            marca.setEstatusMarcaModeloTipoDeBien(dto.getEstatusMarcaModeloTipoDeBien());
        }
    }
}
