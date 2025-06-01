package dev.cristian.app.dto.personas;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonasCreatDto {

    @NotBlank
    @NotNull
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 50, message = "Los apellidos deben tener entre 2 y 50 caracteres")
    private String apellidos;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 50, message = "El CURP debe tener entre 2 y 50 caracteres")
    private String curp;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 50, message = "El teléfono debe tener entre 2 y 50 caracteres")
    private String telefono;

    @NotNull
    @NotBlank
    @Min(value = 0, message = "La edad debe ser mayor o igual a 0")
    @Max(value = 120, message = "La edad debe ser menor o igual a 120")
    private Long edad;

    @NotBlank
    @NotNull
    @Email(message = "Debe proporcionar un correo électronico valido")
    @Size(min = 2, max = 50, message = "El correo debe tener entre 2 y 50 caracteres")
    private String correo;
}
