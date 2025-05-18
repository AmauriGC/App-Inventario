package dev.cristian.app.repository;

import dev.cristian.app.entity.Modelo;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    List<Modelo> findByNombreDelModeloContainingIgnoreCase(String nombreDelModelo);

    List<Modelo> findByEstatusMarcaModeloTipoDeBien(EstatusMarcaModeloTipoDeBien estatus);

    List<Modelo> findByNombreDelModeloContainingIgnoreCaseAndEstatusMarcaModeloTipoDeBien(
            String nombreDelModelo,
            EstatusMarcaModeloTipoDeBien estatus
    );
}
