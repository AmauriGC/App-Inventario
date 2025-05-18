package dev.cristian.app.repository;

import dev.cristian.app.entity.Asignaciones;
import dev.cristian.app.enums.EstatusAsignacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AsignacionesRepository extends JpaRepository<Asignaciones, Long> {

    List<Asignaciones> findByUsuarioIdAndEstatusAsignacion(Long usuarioId, EstatusAsignacion estatus);

    List<Asignaciones> findByBienIdAndEstatusAsignacion(Long bienId, EstatusAsignacion estatus);

    List<Asignaciones> findByEstatusAsignacion(EstatusAsignacion estatus);

    List<Asignaciones> findByUsuarioIdAndFechaDeAsignacionBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fin);

    boolean existsByBienIdAndEstatusAsignacion(Long bienId, EstatusAsignacion estatusAsignacion);
}
