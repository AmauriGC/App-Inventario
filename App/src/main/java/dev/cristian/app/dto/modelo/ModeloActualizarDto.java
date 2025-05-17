package dev.cristian.app.dto.modelo;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloActualizarDto {

    @Size(min = 2, max = 50, message = "El nombre del modelo debe tener entre 2 y 50 caracteres")
    private String nombreDelModelo;

    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
