package dev.cristian.app.service;

import dev.cristian.app.dto.tipodebien.TipoDeBienActualizarDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienCrearDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienDetalleDto;
import dev.cristian.app.entity.TipoDeBien;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.mapper.TipoDeBienMapper;
import dev.cristian.app.repository.tipoDeBienRepository;
import dev.cristian.app.service.interfaces.TipoDeBienInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoDeBienService implements TipoDeBienInterface {

    private static final Logger logger = LoggerFactory.getLogger(TipoDeBienService.class);

    private final tipoDeBienRepository tipoDeBienRepository;
    private final TipoDeBienMapper tipoDeBienMapper;

    public TipoDeBienService(tipoDeBienRepository tipoDeBienRepository,
                             TipoDeBienMapper tipoDeBienMapper) {
        this.tipoDeBienRepository = tipoDeBienRepository;
        this.tipoDeBienMapper = tipoDeBienMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<TipoDeBienDetalleDto> crear(TipoDeBienCrearDto dto) {
        logger.info("Intentando crear nuevo tipo de bien: {}", dto.getTipoBien());

        boolean existeTipo = tipoDeBienRepository.findByTipoBien(dto.getTipoBien())
                .stream()
                .anyMatch(t -> t.getTipoBien() == dto.getTipoBien());

        if (existeTipo) {
            logger.warn("Intento de crear tipo de bien duplicado: {}", dto.getTipoBien());
            throw new RecursoDuplicadoException("Ya existe un tipo de bien: " + dto.getTipoBien());
        }

        TipoDeBien tipoDeBien = tipoDeBienMapper.toEntity(dto);
        TipoDeBien tipoGuardado = tipoDeBienRepository.save(tipoDeBien);
        logger.info("Tipo de bien creado exitosamente con ID: {}", tipoGuardado.getId());

        return new ResponseEntity<>(tipoDeBienMapper.toDetalleDto(tipoGuardado), HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<TipoDeBienDetalleDto> obtenerPorId(Long id) {
        logger.info("Buscando tipo de bien con ID: {}", id);

        TipoDeBien tipoDeBien = tipoDeBienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Tipo de bien con ID {} no encontrado", id);
                    return new RecursoNoEncontradoException("Tipo de bien con ID " + id + " no encontrado");
                });

        return ResponseEntity.ok(tipoDeBienMapper.toDetalleDto(tipoDeBien));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<TipoDeBienDetalleDto>> listarTodos() {
        logger.info("Listando todos los tipos de bien");

        List<TipoDeBienDetalleDto> tipos = tipoDeBienRepository.findAll().stream()
                .map(tipoDeBienMapper::toDetalleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tipos);
    }

    @Override
    @Transactional
    public ResponseEntity<TipoDeBienDetalleDto> actualizar(Long id, TipoDeBienActualizarDto dto) {
        logger.info("Actualizando tipo de bien con ID: {}", id);

        TipoDeBien tipoDeBien = tipoDeBienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Tipo de bien con ID {} no encontrado para actualización", id);
                    return new RecursoNoEncontradoException("Tipo de bien con ID " + id + " no encontrado");
                });

        if (dto.getTipoBien() != null &&
                dto.getTipoBien() != tipoDeBien.getTipoBien() &&
                tipoDeBienRepository.findByTipoBien(dto.getTipoBien())
                        .stream()
                        .anyMatch(t -> t.getTipoBien() == dto.getTipoBien())) {
            logger.warn("Intento de actualizar tipo de bien duplicado: {}", dto.getTipoBien());
            throw new RecursoDuplicadoException("Ya existe un tipo de bien: " + dto.getTipoBien());
        }

        tipoDeBienMapper.actualizarDesdeDto(tipoDeBien, dto);
        TipoDeBien tipoActualizado = tipoDeBienRepository.save(tipoDeBien);
        logger.info("Tipo de bien con ID {} actualizado exitosamente", id);

        return ResponseEntity.ok(tipoDeBienMapper.toDetalleDto(tipoActualizado));
    }

    @Override
    @Transactional
    public ResponseEntity<TipoDeBienDetalleDto> eliminar(Long id) {
        logger.info("Eliminando tipo de bien con ID: {}", id);

        TipoDeBien tipoDeBien = tipoDeBienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Tipo de bien con ID {} no encontrado para eliminación", id);
                    return new RecursoNoEncontradoException("Tipo de bien con ID " + id + " no encontrado");
                });

        tipoDeBienRepository.delete(tipoDeBien);
        logger.info("Tipo de bien con ID {} eliminado exitosamente", id);

        return ResponseEntity.ok(tipoDeBienMapper.toDetalleDto(tipoDeBien));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<TipoDeBienDetalleDto>> buscarPorTipoYEstado(TipoBien tipo, EstatusMarcaModeloTipoDeBien estatus) {
        logger.info("Buscando tipos de bien por tipo {} y estado {}", tipo, estatus);

        List<TipoDeBienDetalleDto> tipos = tipoDeBienRepository
                .findByTipoBienAndEstatusMarcaModeloTipoDeBien(tipo, estatus).stream()
                .map(tipoDeBienMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (tipos.isEmpty()) {
            logger.warn("No se encontraron tipos de bien con tipo {} y estado {}", tipo, estatus);
            throw new RecursoNoEncontradoException(
                    String.format("No se encontraron tipos de bien con tipo %s y estado %s", tipo, estatus));
        }

        return ResponseEntity.ok(tipos);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<TipoDeBienDetalleDto>> buscarPorTipo(TipoBien tipo) {
        logger.info("Buscando tipos de bien por tipo: {}", tipo);

        List<TipoDeBienDetalleDto> tipos = tipoDeBienRepository.findByTipoBien(tipo).stream()
                .map(tipoDeBienMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (tipos.isEmpty()) {
            logger.warn("No se encontraron tipos de bien con tipo: {}", tipo);
            throw new RecursoNoEncontradoException("No se encontraron tipos de bien con tipo: " + tipo);
        }

        return ResponseEntity.ok(tipos);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<TipoDeBienDetalleDto>> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus) {
        logger.info("Filtrando tipos de bien por estatus: {}", estatus);

        List<TipoDeBienDetalleDto> tipos = tipoDeBienRepository.findByEstatusMarcaModeloTipoDeBien(estatus).stream()
                .map(tipoDeBienMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (tipos.isEmpty()) {
            logger.warn("No se encontraron tipos de bien con estatus: {}", estatus);
            throw new RecursoNoEncontradoException("No se encontraron tipos de bien con estatus: " + estatus);
        }

        return ResponseEntity.ok(tipos);
    }
}