package dev.cristian.app.service;

import dev.cristian.app.dto.asignacion.AsignacionActualizarDto;
import dev.cristian.app.dto.asignacion.AsignacionCrearDto;
import dev.cristian.app.dto.asignacion.AsignacionDetalleDto;
import dev.cristian.app.entity.Asignaciones;
import dev.cristian.app.entity.Bien;
import dev.cristian.app.entity.Usuario;
import dev.cristian.app.enums.EstatusAsignacion;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.mapper.AsignacionesMapper;
import dev.cristian.app.repository.AsignacionesRepository;
import dev.cristian.app.repository.BienRepository;
import dev.cristian.app.repository.UsuarioRepository;
import dev.cristian.app.response.ApiResponse;
import dev.cristian.app.service.interfaces.AsignacionesInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionService implements AsignacionesInterface {

    private static final Logger logger = LoggerFactory.getLogger(AsignacionService.class);

    private final AsignacionesRepository asignacionesRepository;
    private final UsuarioRepository usuarioRepository;
    private final BienRepository bienRepository;
    private final AsignacionesMapper asignacionesMapper;

    public AsignacionService(AsignacionesRepository asignacionesRepository,
                             UsuarioRepository usuarioRepository,
                             BienRepository bienRepository,
                             AsignacionesMapper asignacionesMapper) {
        this.asignacionesRepository = asignacionesRepository;
        this.usuarioRepository = usuarioRepository;
        this.bienRepository = bienRepository;
        this.asignacionesMapper = asignacionesMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<AsignacionDetalleDto>> asignar(AsignacionCrearDto dto) {
        logger.info("Intentando crear nueva asignación para usuario {} y bien {}", dto.getUsuarioId(), dto.getBienId());

        // Verificar si ya existe una asignación activa para este bien
        if (asignacionesRepository.existsByBienIdAndEstatusAsignacion(dto.getBienId(), EstatusAsignacion.Asignada)) {
            logger.warn("Intento de asignar un bien ya asignado: {}", dto.getBienId());
            throw new RecursoDuplicadoException("El bien con ID " + dto.getBienId() + " ya tiene una asignación activa");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> {
                    logger.error("Usuario con ID {} no encontrado", dto.getUsuarioId());
                    return new RecursoNoEncontradoException("Usuario con ID " + dto.getUsuarioId() + " no encontrado.");
                });

        Bien bien = bienRepository.findById(dto.getBienId())
                .orElseThrow(() -> {
                    logger.error("Bien con ID {} no encontrado", dto.getBienId());
                    return new RecursoNoEncontradoException("Bien con ID " + dto.getBienId() + " no encontrado.");
                });

        Asignaciones asignacion = asignacionesMapper.toEntity(dto, usuario, bien);
        asignacion = asignacionesRepository.save(asignacion);
        logger.info("Asignación creada exitosamente con ID: {}", asignacion.getId());

        return ResponseEntity.status(201)
                .body(ApiResponse.ok("Asignación creada exitosamente", asignacionesMapper.toDetalleDto(asignacion)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<AsignacionDetalleDto>> obtenerPorId(Long id) {
        logger.info("Buscando asignación con ID: {}", id);

        Asignaciones asignacion = asignacionesRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Asignación con ID {} no encontrada", id);
                    return new RecursoNoEncontradoException("Asignación con ID " + id + " no encontrada.");
                });

        return ResponseEntity.ok(ApiResponse.ok("Asignación encontrada", asignacionesMapper.toDetalleDto(asignacion)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> listarTodas() {
        logger.info("Listando todas las asignaciones");

        List<AsignacionDetalleDto> asignaciones = asignacionesRepository.findAll().stream()
                .map(asignacionesMapper::toDetalleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok("Listado de asignaciones exitoso", asignaciones));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<AsignacionDetalleDto>> actualizar(Long id, AsignacionActualizarDto dto) {
        logger.info("Actualizando asignación con ID: {}", id);

        Asignaciones asignacion = asignacionesRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Asignación con ID {} no encontrada para actualización", id);
                    return new RecursoNoEncontradoException("Asignación con ID " + id + " no encontrada.");
                });

        if (dto.getEstatusAsignacion() != null) {
            asignacion.setEstatusAsignacion(dto.getEstatusAsignacion());
        }
        if (dto.getFechaDeRevocacion() != null) {
            asignacion.setFechaDeRevocacion(dto.getFechaDeRevocacion());
        }

        Asignaciones actualizada = asignacionesRepository.save(asignacion);
        logger.info("Asignación con ID {} actualizada exitosamente", id);

        return ResponseEntity.ok(ApiResponse.ok("Asignación actualizada exitosamente", asignacionesMapper.toDetalleDto(actualizada)));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<AsignacionDetalleDto>> eliminar(Long id) {
        logger.info("Eliminando asignación con ID: {}", id);

        Asignaciones asignacion = asignacionesRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Asignación con ID {} no encontrada para eliminación", id);
                    return new RecursoNoEncontradoException("Asignación con ID " + id + " no encontrada.");
                });

        asignacionesRepository.delete(asignacion);
        logger.info("Asignación con ID {} eliminada exitosamente", id);

        return ResponseEntity.ok(ApiResponse.ok("Asignación eliminada exitosamente", asignacionesMapper.toDetalleDto(asignacion)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> buscarPorUsuarioOBien(Long idUsuario, Long idBien) {
        if (idUsuario != null) {
            logger.info("Buscando asignaciones para usuario ID: {}", idUsuario);
            return buscarAsignacionesActivasPorUsuario(idUsuario);
        } else if (idBien != null) {
            logger.info("Buscando asignaciones para bien ID: {}", idBien);
            return buscarAsignacionesActivasPorBien(idBien);
        }
        logger.info("Búsqueda sin parámetros, retornando lista vacía");
        return ResponseEntity.ok(ApiResponse.ok("No se proporcionaron criterios de búsqueda", List.of()));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> filtrarPorEstatus(EstatusAsignacion estatus) {
        logger.info("Filtrando asignaciones por estatus: {}", estatus);

        List<AsignacionDetalleDto> asignaciones = asignacionesRepository
                .findByEstatusAsignacion(estatus).stream()
                .map(asignacionesMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (asignaciones.isEmpty()) {
            logger.info("No se encontraron asignaciones con estatus {}", estatus);
            throw new RecursoNoEncontradoException("No se encontraron asignaciones con estatus: " + estatus);
        }

        return ResponseEntity.ok(ApiResponse.ok("Asignaciones encontradas", asignaciones));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> buscarAsignacionesActivasPorUsuario(Long usuarioId) {
        logger.info("Buscando asignaciones activas para usuario ID: {}", usuarioId);

        List<AsignacionDetalleDto> asignaciones = asignacionesRepository
                .findByUsuarioIdAndEstatusAsignacion(usuarioId, EstatusAsignacion.Asignada).stream()
                .map(asignacionesMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (asignaciones.isEmpty()) {
            logger.info("No se encontraron asignaciones activas para usuario ID: {}", usuarioId);
            throw new RecursoNoEncontradoException("No se encontraron asignaciones activas para usuario con ID: " + usuarioId);
        }

        return ResponseEntity.ok(ApiResponse.ok("Asignaciones activas encontradas", asignaciones));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> buscarAsignacionesActivasPorBien(Long bienId) {
        logger.info("Buscando asignaciones activas para bien ID: {}", bienId);

        List<AsignacionDetalleDto> asignaciones = asignacionesRepository
                .findByBienIdAndEstatusAsignacion(bienId, EstatusAsignacion.Asignada).stream()
                .map(asignacionesMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (asignaciones.isEmpty()) {
            logger.info("No se encontraron asignaciones activas para bien ID: {}", bienId);
            throw new RecursoNoEncontradoException("No se encontraron asignaciones activas para bien con ID: " + bienId);
        }

        return ResponseEntity.ok(ApiResponse.ok("Asignaciones activas encontradas", asignaciones));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AsignacionDetalleDto>>> buscarAsignacionesPorUsuarioYRangoFechas(
            Long usuarioId, LocalDateTime inicio, LocalDateTime fin) {
        logger.info("Buscando asignaciones para usuario ID {} entre {} y {}", usuarioId, inicio, fin);

        List<AsignacionDetalleDto> asignaciones = asignacionesRepository
                .findByUsuarioIdAndFechaDeAsignacionBetween(usuarioId, inicio, fin).stream()
                .map(asignacionesMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (asignaciones.isEmpty()) {
            logger.info("No se encontraron asignaciones para usuario ID {} en el rango de fechas especificado", usuarioId);
            throw new RecursoNoEncontradoException("No se encontraron asignaciones para el usuario en el rango de fechas especificado");
        }

        return ResponseEntity.ok(ApiResponse.ok("Asignaciones encontradas en el rango de fechas", asignaciones));
    }
}