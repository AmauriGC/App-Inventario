package dev.cristian.app.dto.modelo;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloDetalleDto {
    private Long id;
    private String nombreDelModelo;
    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
