package dev.cristian.app.repository;

import dev.cristian.app.entity.Bien;
import dev.cristian.app.enums.EstatusBien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BienRepository extends JpaRepository<Bien, Long> {

    List<Bien> findByUsuarioIdAndEstatusBien(Long usuarioId, EstatusBien estatus);

    List<Bien> findByAreaComunIdAndEstatusBien(Long areaComunId, EstatusBien estatus);

    List<Bien> findByMarcaIdAndModeloId(Long marcaId, Long modeloId);

    List<Bien> findByTipoDeBienIdAndEstatusBien(Long tipoDeBienId, EstatusBien estatus);

    List<Bien> findByMarcaIdAndModeloIdAndTipoDeBienId(Long marcaId, Long modeloId, Long tipoDeBienId);
}
