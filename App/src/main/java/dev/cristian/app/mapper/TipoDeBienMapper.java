package dev.cristian.app.mapper;

import dev.cristian.app.dto.tipodebien.TipoDeBienActualizarDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienCrearDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienDetalleDto;
import dev.cristian.app.entity.TipoDeBien;
import org.springframework.stereotype.Component;

@Component
public class TipoDeBienMapper {

    public TipoDeBien toEntity(TipoDeBienCrearDto dto) {
        TipoDeBien tipo = new TipoDeBien();
        tipo.setTipoBien(dto.getTipoBien());
        tipo.setEstatusMarcaModeloTipoDeBien(dto.getEstatusMarcaModeloTipoDeBien());
        return tipo;
    }

    public TipoDeBienDetalleDto toDetalleDto(TipoDeBien tipo) {
        TipoDeBienDetalleDto dto = new TipoDeBienDetalleDto();
        dto.setId(tipo.getId());
        dto.setTipoBien(tipo.getTipoBien());
        dto.setEstatusMarcaModeloTipoDeBien(tipo.getEstatusMarcaModeloTipoDeBien());
        return dto;
    }

    public TipoDeBienActualizarDto toActualizarDto(TipoDeBien tipo) {
        TipoDeBienActualizarDto dto = new TipoDeBienActualizarDto();
        dto.setTipoBien(tipo.getTipoBien());
        dto.setEstatusMarcaModeloTipoDeBien(tipo.getEstatusMarcaModeloTipoDeBien());
        return dto;
    }

    public void actualizarDesdeDto(TipoDeBien tipo, TipoDeBienActualizarDto dto) {
        if (dto.getTipoBien() != null) {
            tipo.setTipoBien(dto.getTipoBien());
        }
        if (dto.getEstatusMarcaModeloTipoDeBien() != null) {
            tipo.setEstatusMarcaModeloTipoDeBien(dto.getEstatusMarcaModeloTipoDeBien());
        }
    }
}
