package dev.cristian.app.dto.usuario;

import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioActualizarRolEstatusDto {

    @NotNull(message = "Debe de escoger el rol del usuario")
    private Rol rol;

    @NotNull(message = "Debe de escoger el estatus para el usuario")
    private EstatusUsuario estatusUsuario;
}
