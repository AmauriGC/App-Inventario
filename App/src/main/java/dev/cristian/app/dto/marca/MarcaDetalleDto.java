package dev.cristian.app.dto.marca;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaDetalleDto {
    private Long id;
    private String nombreDeMarca;
    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
