package dev.cristian.app.dto.personas;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonasActualizarDto {

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Size(min = 2, max = 50, message = "Los apellidos deben tener entre 2 y 50 caracteres")
    private String apellidos;

    @Size(min = 2, max = 50, message = "El CURP debe tener entre 2 y 50 caracteres")
    private String curp;

    @Size(min = 2, max = 50, message = "El teléfono debe tener entre 2 y 50 caracteres")
    private String telefono;

    @Min(value = 0, message = "La edad debe ser mayor o igual a 0")
    @Max(value = 120, message = "La edad debe ser menor o igual a 120")
    private Long edad;

    @Email(message = "Debe proporcionar un correo électronico valido")
    @Size(min = 2, max = 50, message = "El correo debe tener entre 2 y 50 caracteres")
    private String correo;
}
