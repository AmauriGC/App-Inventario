package dev.cristian.app.service;

import dev.cristian.app.dto.marca.MarcaActualizarDto;
import dev.cristian.app.dto.marca.MarcaCrearDto;
import dev.cristian.app.dto.marca.MarcaDetalleDto;
import dev.cristian.app.entity.Marca;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.mapper.MarcaMapper;
import dev.cristian.app.repository.MarcaRepository;
import dev.cristian.app.service.interfaces.MarcaInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService implements MarcaInterface {

    private static final Logger logger = LoggerFactory.getLogger(MarcaService.class);

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    public MarcaService(MarcaRepository marcaRepository, MarcaMapper marcaMapper) {
        this.marcaRepository = marcaRepository;
        this.marcaMapper = marcaMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<MarcaDetalleDto> crear(MarcaCrearDto dto) {
        logger.info("Intentando crear nueva marca: {}", dto.getNombreDeMarca());

        boolean existeMarca = marcaRepository.findByNombreDeMarcaContainingIgnoreCase(dto.getNombreDeMarca())
                .stream()
                .anyMatch(m -> m.getNombreDeMarca().equalsIgnoreCase(dto.getNombreDeMarca()));

        if (existeMarca) {
            logger.warn("Intento de crear marca duplicada: {}", dto.getNombreDeMarca());
            throw new RecursoDuplicadoException("Ya existe una marca con el nombre: " + dto.getNombreDeMarca());
        }

        Marca marca = marcaMapper.toEntity(dto);
        Marca marcaGuardada = marcaRepository.save(marca);
        logger.info("Marca creada exitosamente con ID: {}", marcaGuardada.getId());

        return new ResponseEntity<>(marcaMapper.toDetalleDto(marcaGuardada), HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<MarcaDetalleDto> obtenerPorId(Long id) {
        logger.info("Buscando marca con ID: {}", id);

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Marca con ID {} no encontrada", id);
                    return new RecursoNoEncontradoException("Marca con ID " + id + " no encontrada");
                });

        return ResponseEntity.ok(marcaMapper.toDetalleDto(marca));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<MarcaDetalleDto>> listarTodas() {
        logger.info("Listando todas las marcas");

        List<MarcaDetalleDto> marcas = marcaRepository.findAll().stream()
                .map(marcaMapper::toDetalleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(marcas);
    }

    @Override
    @Transactional
    public ResponseEntity<MarcaDetalleDto> actualizar(Long id, MarcaActualizarDto dto) {
        logger.info("Actualizando marca con ID: {}", id);

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Marca con ID {} no encontrada para actualización", id);
                    return new RecursoNoEncontradoException("Marca con ID " + id + " no encontrada");
                });

        if (dto.getNombreDeMarca() != null &&
                !dto.getNombreDeMarca().equalsIgnoreCase(marca.getNombreDeMarca()) &&
                marcaRepository.findByNombreDeMarcaContainingIgnoreCase(dto.getNombreDeMarca())
                        .stream()
                        .anyMatch(m -> m.getNombreDeMarca().equalsIgnoreCase(dto.getNombreDeMarca()))) {
            logger.warn("Intento de actualizar marca con nombre duplicado: {}", dto.getNombreDeMarca());
            throw new RecursoDuplicadoException("Ya existe una marca con el nombre: " + dto.getNombreDeMarca());
        }

        marcaMapper.actualizarDesdeDto(marca, dto);
        Marca marcaActualizada = marcaRepository.save(marca);
        logger.info("Marca con ID {} actualizada exitosamente", id);

        return ResponseEntity.ok(marcaMapper.toDetalleDto(marcaActualizada));
    }

    @Override
    @Transactional
    public ResponseEntity<MarcaDetalleDto> eliminar(Long id) {
        logger.info("Eliminando marca con ID: {}", id);

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Marca con ID {} no encontrada para eliminación", id);
                    return new RecursoNoEncontradoException("Marca con ID " + id + " no encontrada");
                });

        marcaRepository.delete(marca);
        logger.info("Marca con ID {} eliminada exitosamente", id);

        return ResponseEntity.ok(marcaMapper.toDetalleDto(marca));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<MarcaDetalleDto>> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus) {
        logger.info("Filtrando marcas por estatus: {}", estatus);

        List<MarcaDetalleDto> marcas = marcaRepository.findByEstatusMarcaModeloTipoDeBien(estatus).stream()
                .map(marcaMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (marcas.isEmpty()) {
            logger.warn("No se encontraron marcas con estatus {}", estatus);
            throw new RecursoNoEncontradoException("No se encontraron marcas con estatus: " + estatus);
        }

        return ResponseEntity.ok(marcas);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<MarcaDetalleDto>> buscarPorNombre(String nombre) {
        logger.info("Buscando marcas que contengan en el nombre: {}", nombre);

        List<MarcaDetalleDto> marcas = marcaRepository.findByNombreDeMarcaContainingIgnoreCase(nombre).stream()
                .map(marcaMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (marcas.isEmpty()) {
            logger.warn("No se encontraron marcas con nombre que contenga: {}", nombre);
            throw new RecursoNoEncontradoException("No se encontraron marcas que contengan: " + nombre);
        }

        return ResponseEntity.ok(marcas);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<MarcaDetalleDto>> buscarPorNombreYEstatus(String nombre, EstatusMarcaModeloTipoDeBien estatus) {
        logger.info("Buscando marcas que contengan '{}' y estatus {}", nombre, estatus);

        List<MarcaDetalleDto> marcas = marcaRepository
                .findByNombreDeMarcaContainingIgnoreCaseAndEstatusMarcaModeloTipoDeBien(nombre, estatus).stream()
                .map(marcaMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (marcas.isEmpty()) {
            logger.warn("No se encontraron marcas con nombre '{}' y estatus {}", nombre, estatus);
            throw new RecursoNoEncontradoException(
                    String.format("No se encontraron marcas con nombre '%s' y estatus %s", nombre, estatus));
        }

        return ResponseEntity.ok(marcas);
    }
}