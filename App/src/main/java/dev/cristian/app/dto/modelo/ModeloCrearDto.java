package dev.cristian.app.dto.modelo;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeloCrearDto {

    @NotBlank(message = "El nombre del modelo no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre del modelo debe tener entre 2 y 50 caracteres")
    private String nombreDelModelo;

    @NotNull(message = "El estatus del modelo no puede ser nulo")
    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
