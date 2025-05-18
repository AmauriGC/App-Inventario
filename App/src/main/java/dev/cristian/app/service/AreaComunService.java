package dev.cristian.app.service;

import dev.cristian.app.dto.areacomun.AreaComunActualizarDto;
import dev.cristian.app.dto.areacomun.AreaComunCrearDto;
import dev.cristian.app.dto.areacomun.AreaComunDetalleDto;
import dev.cristian.app.entity.AreaComun;
import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.mapper.AreaComunMapper;
import dev.cristian.app.repository.AreaComunRepository;
import dev.cristian.app.response.ApiResponse;
import dev.cristian.app.service.interfaces.AreaComunInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaComunService implements AreaComunInterface {

    private static final Logger logger = LoggerFactory.getLogger(AreaComunService.class);

    private final AreaComunRepository areaComunRepository;
    private final AreaComunMapper areaComunMapper;

    public AreaComunService(AreaComunRepository areaComunRepository, AreaComunMapper areaComunMapper) {
        this.areaComunRepository = areaComunRepository;
        this.areaComunMapper = areaComunMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<AreaComunDetalleDto>> crear(AreaComunCrearDto dto) {
        logger.info("Intentando crear nueva área común: {}", dto.getNombreDelArea());

        if (areaComunRepository.findByNombreDelAreaContainingIgnoreCase(dto.getNombreDelArea()).stream()
                .anyMatch(a -> a.getNombreDelArea().equalsIgnoreCase(dto.getNombreDelArea()))) {
            logger.warn("Intento de crear área común con nombre duplicado: {}", dto.getNombreDelArea());
            throw new RecursoDuplicadoException("Ya existe un área común con el nombre '" + dto.getNombreDelArea() + "'.");
        }

        AreaComun areaComun = areaComunMapper.toEntity(dto);
        AreaComun guardada = areaComunRepository.save(areaComun);
        logger.info("Área común creada exitosamente con ID: {}", guardada.getId());

        return ResponseEntity.status(201).body(ApiResponse.ok("Área común creada exitosamente", areaComunMapper.toDetalleDto(guardada)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<AreaComunDetalleDto>> obtenerPorId(Long id) {
        logger.info("Buscando área común con ID: {}", id);

        AreaComun areaComun = areaComunRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Área común con ID {} no encontrada", id);
                    return new RecursoNoEncontradoException("Área común con ID " + id + " no encontrada.");
                });

        return ResponseEntity.ok(ApiResponse.ok("Área común encontrada", areaComunMapper.toDetalleDto(areaComun)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AreaComunDetalleDto>>> listarTodas() {
        logger.info("Listando todas las áreas comunes");

        List<AreaComunDetalleDto> areas = areaComunRepository.findAll().stream()
                .map(areaComunMapper::toDetalleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok("Listado exitoso", areas));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<AreaComunDetalleDto>> actualizar(Long id, AreaComunActualizarDto dto) {
        logger.info("Actualizando área común con ID: {}", id);

        AreaComun existente = areaComunRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Área común con ID {} no encontrada para actualización", id);
                    return new RecursoNoEncontradoException("Área común con ID " + id + " no encontrada.");
                });

        if (dto.getNombreDelArea() != null &&
                !dto.getNombreDelArea().equalsIgnoreCase(existente.getNombreDelArea()) &&
                areaComunRepository.findByNombreDelAreaContainingIgnoreCase(dto.getNombreDelArea()).stream()
                        .anyMatch(a -> a.getNombreDelArea().equalsIgnoreCase(dto.getNombreDelArea()))) {
            logger.warn("Intento de actualizar área común con nombre duplicado: {}", dto.getNombreDelArea());
            throw new RecursoDuplicadoException("Ya existe un área común con el nombre '" + dto.getNombreDelArea() + "'.");
        }

        areaComunMapper.actualizarDesdeDto(existente, dto);
        AreaComun actualizada = areaComunRepository.save(existente);
        logger.info("Área común con ID {} actualizada exitosamente", id);

        return ResponseEntity.ok(ApiResponse.ok("Área común actualizada exitosamente", areaComunMapper.toDetalleDto(actualizada)));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<AreaComunDetalleDto>> eliminar(Long id) {
        logger.info("Eliminando área común con ID: {}", id);

        AreaComun areaComun = areaComunRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Área común con ID {} no encontrada para eliminación", id);
                    return new RecursoNoEncontradoException("Área común con ID " + id + " no encontrada.");
                });

        areaComunRepository.delete(areaComun);
        logger.info("Área común con ID {} eliminada exitosamente", id);

        return ResponseEntity.ok(ApiResponse.ok("Área común eliminada exitosamente", areaComunMapper.toDetalleDto(areaComun)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AreaComunDetalleDto>>> buscarPorTipoYEstado(TipoDeArea tipo, EstatusAreaComun estatus) {
        logger.info("Buscando áreas comunes por tipo {} y estado {}", tipo, estatus);

        List<AreaComunDetalleDto> areas = areaComunRepository.findByTipoDeAreaAndEstatusAreaComun(tipo, estatus).stream()
                .map(areaComunMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (areas.isEmpty()) {
            logger.warn("No se encontraron áreas comunes con tipo {} y estado {}", tipo, estatus);
            throw new RecursoNoEncontradoException("No se encontraron áreas comunes con los criterios especificados");
        }

        return ResponseEntity.ok(ApiResponse.ok("Áreas comunes encontradas", areas));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AreaComunDetalleDto>>> buscarPorNombre(String nombre) {
        logger.info("Buscando áreas comunes que contengan en el nombre: {}", nombre);

        List<AreaComunDetalleDto> areas = areaComunRepository.findByNombreDelAreaContainingIgnoreCase(nombre).stream()
                .map(areaComunMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (areas.isEmpty()) {
            logger.warn("No se encontraron áreas comunes con nombre que contenga: {}", nombre);
            throw new RecursoNoEncontradoException("No se encontraron áreas comunes que contengan: " + nombre);
        }

        logger.info("Se encontraron {} áreas comunes con nombre que contiene: {}", areas.size(), nombre);
        return ResponseEntity.ok(ApiResponse.ok("Áreas comunes encontradas", areas));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<AreaComunDetalleDto>>> buscarPorEstado(EstatusAreaComun estatus) {
        logger.info("Buscando áreas comunes con estado: {}", estatus);

        List<AreaComunDetalleDto> areas = areaComunRepository.findByEstatusAreaComun(estatus).stream()
                .map(areaComunMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (areas.isEmpty()) {
            logger.warn("No se encontraron áreas comunes con estado: {}", estatus);
            throw new RecursoNoEncontradoException("No se encontraron áreas comunes con estado: " + estatus);
        }

        logger.info("Se encontraron {} áreas comunes con estado: {}", areas.size(), estatus);
        return ResponseEntity.ok(ApiResponse.ok("Áreas comunes encontradas", areas));
    }
}
