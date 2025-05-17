package dev.cristian.app.dto.tipodebien;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoDeBienActualizarDto {

    private TipoBien tipoBien;

    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
