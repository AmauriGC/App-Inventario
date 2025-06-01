package dev.cristian.app.repository;

import dev.cristian.app.entity.Personas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Personas, Long> {
    boolean existsByCurp(String curp);

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);
}
