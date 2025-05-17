package dev.cristian.app.dto.usuario;

import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDetalleDto {
    private Long id;
    private String nombre;
    private String apellidos;
    private String correo;
    private Rol rol;
    private EstatusUsuario estatusUsuario;
}
