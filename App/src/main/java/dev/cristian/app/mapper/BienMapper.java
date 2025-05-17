package dev.cristian.app.mapper;

import dev.cristian.app.dto.bien.BienActualizarDto;
import dev.cristian.app.dto.bien.BienCrearDto;
import dev.cristian.app.dto.bien.BienDetalleDto;
import dev.cristian.app.entity.*;
import org.springframework.stereotype.Component;

@Component
public class BienMapper {

    public Bien toEntity(BienCrearDto dto, AreaComun areaComun, Marca marca, Modelo modelo, TipoDeBien tipoDeBien, Usuario usuario) {
        Bien bien = new Bien();
        bien.setAreaComun(areaComun);
        bien.setMarca(marca);
        bien.setModelo(modelo);
        bien.setTipoDeBien(tipoDeBien);
        bien.setUsuario(usuario);
        bien.setEstatusBien(dto.getEstatusBien());
        return bien;
    }

    public BienDetalleDto toDetalleDto(Bien bien) {
        BienDetalleDto dto = new BienDetalleDto();
        dto.setId(bien.getId());
        dto.setAreaComunId(bien.getAreaComun().getId());
        dto.setMarcaId(bien.getMarca().getId());
        dto.setModeloId(bien.getModelo().getId());
        dto.setTipoDeBienId(bien.getTipoDeBien().getId());
        dto.setUsuarioId(bien.getUsuario() != null ? bien.getUsuario().getId() : null);
        dto.setEstatusBien(bien.getEstatusBien());
        return dto;
    }

    public BienActualizarDto toActualizarDto(Bien bien) {
        BienActualizarDto dto = new BienActualizarDto();
        dto.setEstatusBien(bien.getEstatusBien());
        return dto;
    }

    public void actualizarDesdeDto(Bien bien, BienActualizarDto dto) {
        if (dto.getEstatusBien() != null) {
            bien.setEstatusBien(dto.getEstatusBien());
        }
    }
}
