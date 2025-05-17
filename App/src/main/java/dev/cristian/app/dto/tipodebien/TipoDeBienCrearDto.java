package dev.cristian.app.dto.tipodebien;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoDeBienCrearDto {

    @NotNull(message = "Debe de escoger un tipo de bien")
    private TipoBien tipoBien;

    @NotNull(message = "Debe de escoger el estatus para tipo de bien")
    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
