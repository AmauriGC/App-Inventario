package dev.cristian.app.repository;

import dev.cristian.app.entity.AreaComun;
import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface areaComunRepository extends JpaRepository<AreaComun, Long> {

    List<AreaComun> findByTipoDeAreaAndEstatusAreaComun(TipoDeArea tipoDeArea, EstatusAreaComun estatus);

    List<AreaComun> findByNombreDelAreaContainingIgnoreCase(String nombre);

    List<AreaComun> findByEstatusAreaComun(EstatusAreaComun estatus);
}
