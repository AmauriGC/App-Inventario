package dev.cristian.app.repository;

import dev.cristian.app.entity.Usuario;
import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface usuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);

    List<Usuario> findByCorreoContainingIgnoreCase(String correo);

    List<Usuario> findByEstatusUsuario(EstatusUsuario estatus);

    List<Usuario> findByRol(Rol rol);

    List<Usuario> findByRolAndEstatusUsuario(Rol rol, EstatusUsuario estatus);

    List<Usuario> findByNombreContainingIgnoreCaseAndRolAndEstatusUsuario(
            String nombre,
            Rol rol,
            EstatusUsuario estatus
    );
}
