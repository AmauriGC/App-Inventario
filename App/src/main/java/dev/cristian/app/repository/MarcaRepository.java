package dev.cristian.app.repository;

import dev.cristian.app.entity.Marca;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    List<Marca> findByNombreDeMarcaContainingIgnoreCase(String nombreDeMarca);

    List<Marca> findByEstatusMarcaModeloTipoDeBien(EstatusMarcaModeloTipoDeBien estatus);

    List<Marca> findByNombreDeMarcaContainingIgnoreCaseAndEstatusMarcaModeloTipoDeBien(
            String nombreDeMarca,
            EstatusMarcaModeloTipoDeBien estatus
    );
}
