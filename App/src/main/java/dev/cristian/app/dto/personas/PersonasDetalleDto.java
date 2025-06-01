package dev.cristian.app.dto.personas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonasDetalleDto {

    private String nombre;

    private String apellidos;

    private String curp;

    private String telefono;

    private Long edad;

    private String correo;
}
