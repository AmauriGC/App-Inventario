package dev.cristian.app.dto.marca;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaActualizarDto {

    @Size(min = 2, max = 50, message = "El nombre de la marca debe tener entre 2 y 50 caracteres")
    private String nombreDeMarca;

    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
