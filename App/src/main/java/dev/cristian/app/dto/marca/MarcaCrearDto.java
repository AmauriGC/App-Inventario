package dev.cristian.app.dto.marca;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaCrearDto {

    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre de la marca debe tener entre 2 y 50 caracteres")
    private String nombreDeMarca;

    @NotNull(message = "Debe de escoger el estatus de la marca")
    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
