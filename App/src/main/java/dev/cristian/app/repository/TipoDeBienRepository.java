package dev.cristian.app.repository;

import dev.cristian.app.entity.TipoDeBien;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoDeBienRepository extends JpaRepository<TipoDeBien, Long> {

    List<TipoDeBien> findByTipoBien(TipoBien tipoBien);

    List<TipoDeBien> findByEstatusMarcaModeloTipoDeBien(EstatusMarcaModeloTipoDeBien estatus);

    List<TipoDeBien> findByTipoBienAndEstatusMarcaModeloTipoDeBien(
            TipoBien tipoBien,
            EstatusMarcaModeloTipoDeBien estatus
    );
}
