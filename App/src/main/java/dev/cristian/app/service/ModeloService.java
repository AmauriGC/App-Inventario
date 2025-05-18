package dev.cristian.app.service;

import dev.cristian.app.dto.modelo.ModeloActualizarDto;
import dev.cristian.app.dto.modelo.ModeloCrearDto;
import dev.cristian.app.dto.modelo.ModeloDetalleDto;
import dev.cristian.app.entity.Modelo;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.mapper.ModeloMapper;
import dev.cristian.app.repository.ModeloRepository;
import dev.cristian.app.service.interfaces.ModeloInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeloService implements ModeloInterface {

    private static final Logger logger = LoggerFactory.getLogger(ModeloService.class);

    private final ModeloRepository modeloRepository;
    private final ModeloMapper modeloMapper;

    public ModeloService(ModeloRepository modeloRepository, ModeloMapper modeloMapper) {
        this.modeloRepository = modeloRepository;
        this.modeloMapper = modeloMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<ModeloDetalleDto> crear(ModeloCrearDto dto) {
        logger.info("Intentando crear nuevo modelo: {}", dto.getNombreDelModelo());

        boolean existeModelo = modeloRepository.findByNombreDelModeloContainingIgnoreCase(dto.getNombreDelModelo())
                .stream()
                .anyMatch(m -> m.getNombreDelModelo().equalsIgnoreCase(dto.getNombreDelModelo()));

        if (existeModelo) {
            logger.warn("Intento de crear modelo duplicado: {}", dto.getNombreDelModelo());
            throw new RecursoDuplicadoException("Ya existe un modelo con el nombre: " + dto.getNombreDelModelo());
        }

        Modelo modelo = modeloMapper.toEntity(dto);
        Modelo modeloGuardado = modeloRepository.save(modelo);
        logger.info("Modelo creado exitosamente con ID: {}", modeloGuardado.getId());

        return new ResponseEntity<>(modeloMapper.toDetalleDto(modeloGuardado), HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ModeloDetalleDto> obtenerPorId(Long id) {
        logger.info("Buscando modelo con ID: {}", id);

        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Modelo con ID {} no encontrado", id);
                    return new RecursoNoEncontradoException("Modelo con ID " + id + " no encontrado");
                });

        return ResponseEntity.ok(modeloMapper.toDetalleDto(modelo));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ModeloDetalleDto>> listarTodos() {
        logger.info("Listando todos los modelos");

        List<ModeloDetalleDto> modelos = modeloRepository.findAll().stream()
                .map(modeloMapper::toDetalleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(modelos);
    }

    @Override
    @Transactional
    public ResponseEntity<ModeloDetalleDto> actualizar(Long id, ModeloActualizarDto dto) {
        logger.info("Actualizando modelo con ID: {}", id);

        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Modelo con ID {} no encontrado para actualización", id);
                    return new RecursoNoEncontradoException("Modelo con ID " + id + " no encontrado");
                });

        if (dto.getNombreDelModelo() != null &&
                !dto.getNombreDelModelo().equalsIgnoreCase(modelo.getNombreDelModelo()) &&
                modeloRepository.findByNombreDelModeloContainingIgnoreCase(dto.getNombreDelModelo())
                        .stream()
                        .anyMatch(m -> m.getNombreDelModelo().equalsIgnoreCase(dto.getNombreDelModelo()))) {
            logger.warn("Intento de actualizar modelo con nombre duplicado: {}", dto.getNombreDelModelo());
            throw new RecursoDuplicadoException("Ya existe un modelo con el nombre: " + dto.getNombreDelModelo());
        }

        modeloMapper.actualizarDesdeDto(modelo, dto);
        Modelo modeloActualizado = modeloRepository.save(modelo);
        logger.info("Modelo con ID {} actualizado exitosamente", id);

        return ResponseEntity.ok(modeloMapper.toDetalleDto(modeloActualizado));
    }

    @Override
    @Transactional
    public ResponseEntity<ModeloDetalleDto> eliminar(Long id) {
        logger.info("Eliminando modelo con ID: {}", id);

        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Modelo con ID {} no encontrado para eliminación", id);
                    return new RecursoNoEncontradoException("Modelo con ID " + id + " no encontrado");
                });

        modeloRepository.delete(modelo);
        logger.info("Modelo con ID {} eliminado exitosamente", id);

        return ResponseEntity.ok(modeloMapper.toDetalleDto(modelo));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ModeloDetalleDto>> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus) {
        logger.info("Filtrando modelos por estatus: {}", estatus);

        List<ModeloDetalleDto> modelos = modeloRepository.findByEstatusMarcaModeloTipoDeBien(estatus).stream()
                .map(modeloMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (modelos.isEmpty()) {
            logger.warn("No se encontraron modelos con estatus {}", estatus);
            throw new RecursoNoEncontradoException("No se encontraron modelos con estatus: " + estatus);
        }

        return ResponseEntity.ok(modelos);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ModeloDetalleDto>> buscarPorNombre(String nombre) {
        logger.info("Buscando modelos que contengan en el nombre: {}", nombre);

        List<ModeloDetalleDto> modelos = modeloRepository.findByNombreDelModeloContainingIgnoreCase(nombre).stream()
                .map(modeloMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (modelos.isEmpty()) {
            logger.warn("No se encontraron modelos con nombre que contenga: {}", nombre);
            throw new RecursoNoEncontradoException("No se encontraron modelos que contengan: " + nombre);
        }

        return ResponseEntity.ok(modelos);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ModeloDetalleDto>> buscarPorNombreYEstatus(String nombre, EstatusMarcaModeloTipoDeBien estatus) {
        logger.info("Buscando modelos que contengan '{}' y estatus {}", nombre, estatus);

        List<ModeloDetalleDto> modelos = modeloRepository
                .findByNombreDelModeloContainingIgnoreCaseAndEstatusMarcaModeloTipoDeBien(nombre, estatus).stream()
                .map(modeloMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (modelos.isEmpty()) {
            logger.warn("No se encontraron modelos con nombre '{}' y estatus {}", nombre, estatus);
            throw new RecursoNoEncontradoException(
                    String.format("No se encontraron modelos con nombre '%s' y estatus %s", nombre, estatus));
        }

        return ResponseEntity.ok(modelos);
    }
}