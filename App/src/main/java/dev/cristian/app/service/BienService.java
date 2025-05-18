package dev.cristian.app.service;

import dev.cristian.app.dto.bien.BienActualizarDto;
import dev.cristian.app.dto.bien.BienCrearDto;
import dev.cristian.app.dto.bien.BienDetalleDto;
import dev.cristian.app.entity.*;
import dev.cristian.app.enums.EstatusBien;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.mapper.BienMapper;
import dev.cristian.app.repository.*;
import dev.cristian.app.service.interfaces.BienInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BienService implements BienInterface {

    private static final Logger logger = LoggerFactory.getLogger(BienService.class);

    private final BienRepository bienRepository;
    private final AreaComunRepository areaComunRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final TipoDeBienRepository tipoDeBienRepository;
    private final UsuarioRepository usuarioRepository;
    private final BienMapper bienMapper;

    public BienService(BienRepository bienRepository,
                       AreaComunRepository areaComunRepository,
                       MarcaRepository marcaRepository,
                       ModeloRepository modeloRepository,
                       TipoDeBienRepository tipoDeBienRepository,
                       UsuarioRepository usuarioRepository,
                       BienMapper bienMapper) {
        this.bienRepository = bienRepository;
        this.areaComunRepository = areaComunRepository;
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
        this.tipoDeBienRepository = tipoDeBienRepository;
        this.usuarioRepository = usuarioRepository;
        this.bienMapper = bienMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<BienDetalleDto> crear(BienCrearDto dto) {
        logger.info("Intentando crear nuevo bien");

        AreaComun areaComun = areaComunRepository.findById(dto.getAreaComunId())
                .orElseThrow(() -> {
                    logger.error("Área común con ID {} no encontrada", dto.getAreaComunId());
                    return new RecursoNoEncontradoException("Área común con ID " + dto.getAreaComunId() + " no encontrada");
                });

        Marca marca = marcaRepository.findById(dto.getMarcaId())
                .orElseThrow(() -> {
                    logger.error("Marca con ID {} no encontrada", dto.getMarcaId());
                    return new RecursoNoEncontradoException("Marca con ID " + dto.getMarcaId() + " no encontrada");
                });

        Modelo modelo = modeloRepository.findById(dto.getModeloId())
                .orElseThrow(() -> {
                    logger.error("Modelo con ID {} no encontrado", dto.getModeloId());
                    return new RecursoNoEncontradoException("Modelo con ID " + dto.getModeloId() + " no encontrado");
                });

        TipoDeBien tipoDeBien = tipoDeBienRepository.findById(dto.getTipoDeBienId())
                .orElseThrow(() -> {
                    logger.error("Tipo de bien con ID {} no encontrado", dto.getTipoDeBienId());
                    return new RecursoNoEncontradoException("Tipo de bien con ID " + dto.getTipoDeBienId() + " no encontrado");
                });

        Usuario usuario = dto.getUsuarioId() != null ?
                usuarioRepository.findById(dto.getUsuarioId())
                        .orElseThrow(() -> {
                            logger.error("Usuario con ID {} no encontrado", dto.getUsuarioId());
                            return new RecursoNoEncontradoException("Usuario con ID " + dto.getUsuarioId() + " no encontrado");
                        }) : null;

        Bien bien = bienMapper.toEntity(dto, areaComun, marca, modelo, tipoDeBien, usuario);
        Bien bienGuardado = bienRepository.save(bien);
        logger.info("Bien creado exitosamente con ID: {}", bienGuardado.getId());

        return new ResponseEntity<>(bienMapper.toDetalleDto(bienGuardado), HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<BienDetalleDto> obtenerPorId(Long id) {
        logger.info("Buscando bien con ID: {}", id);

        Bien bien = bienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Bien con ID {} no encontrado", id);
                    return new RecursoNoEncontradoException("Bien con ID " + id + " no encontrado");
                });

        return ResponseEntity.ok(bienMapper.toDetalleDto(bien));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<BienDetalleDto>> listarTodos() {
        logger.info("Listando todos los bienes");

        List<BienDetalleDto> bienes = bienRepository.findAll().stream()
                .map(bienMapper::toDetalleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bienes);
    }

    @Override
    @Transactional
    public ResponseEntity<BienDetalleDto> actualizar(Long id, BienActualizarDto dto) {
        logger.info("Actualizando bien con ID: {}", id);

        Bien bien = bienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Bien con ID {} no encontrado para actualización", id);
                    return new RecursoNoEncontradoException("Bien con ID " + id + " no encontrado");
                });

        bienMapper.actualizarDesdeDto(bien, dto);
        Bien bienActualizado = bienRepository.save(bien);
        logger.info("Bien con ID {} actualizado exitosamente", id);

        return ResponseEntity.ok(bienMapper.toDetalleDto(bienActualizado));
    }

    @Override
    @Transactional
    public ResponseEntity<BienDetalleDto> eliminar(Long id) {
        logger.info("Eliminando bien con ID: {}", id);

        Bien bien = bienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Bien con ID {} no encontrado para eliminación", id);
                    return new RecursoNoEncontradoException("Bien con ID " + id + " no encontrado");
                });

        bienRepository.delete(bien);
        logger.info("Bien con ID {} eliminado exitosamente", id);

        return ResponseEntity.ok(bienMapper.toDetalleDto(bien));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<BienDetalleDto>> buscarPorAreaOMarca(Long idArea, Long idMarca) {
        if (idArea != null) {
            logger.info("Buscando bienes por área común ID: {}", idArea);
            return buscarPorAreaComun(idArea);
        } else if (idMarca != null) {
            logger.info("Buscando bienes por marca ID: {}", idMarca);
            return buscarPorMarca(idMarca);
        }
        logger.info("Búsqueda sin parámetros, retornando lista vacía");
        return ResponseEntity.ok(List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<BienDetalleDto>> filtrarPorEstatus(EstatusBien estatus) {
        logger.info("Filtrando bienes por estatus: {}", estatus);

        List<BienDetalleDto> bienes = bienRepository.findAll().stream()
                .filter(b -> b.getEstatusBien() == estatus)
                .map(bienMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (bienes.isEmpty()) {
            logger.warn("No se encontraron bienes con estatus {}", estatus);
            throw new RecursoNoEncontradoException("No se encontraron bienes con estatus: " + estatus);
        }

        return ResponseEntity.ok(bienes);
    }

    private ResponseEntity<List<BienDetalleDto>> buscarPorAreaComun(Long areaComunId) {
        List<BienDetalleDto> bienes = bienRepository.findByAreaComunIdAndEstatusBien(areaComunId, EstatusBien.Activo).stream()
                .map(bienMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (bienes.isEmpty()) {
            logger.warn("No se encontraron bienes activos para área común ID: {}", areaComunId);
            throw new RecursoNoEncontradoException("No se encontraron bienes activos para el área común especificada");
        }

        return ResponseEntity.ok(bienes);
    }

    private ResponseEntity<List<BienDetalleDto>> buscarPorMarca(Long marcaId) {
        List<BienDetalleDto> bienes = bienRepository.findByMarcaIdAndModeloId(marcaId, null).stream()
                .map(bienMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (bienes.isEmpty()) {
            logger.warn("No se encontraron bienes para marca ID: {}", marcaId);
            throw new RecursoNoEncontradoException("No se encontraron bienes para la marca especificada");
        }

        return ResponseEntity.ok(bienes);
    }
}