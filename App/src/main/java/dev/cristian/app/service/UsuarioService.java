package dev.cristian.app.service;

import dev.cristian.app.dto.usuario.UsuarioActualizarDto;
import dev.cristian.app.dto.usuario.UsuarioActualizarRolEstatusDto;
import dev.cristian.app.dto.usuario.UsuarioCrearDto;
import dev.cristian.app.dto.usuario.UsuarioDetalleDto;
import dev.cristian.app.entity.Usuario;
import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;
import dev.cristian.app.exception.exceptions.RecursoDuplicadoException;
import dev.cristian.app.exception.exceptions.RecursoNoEncontradoException;
import dev.cristian.app.mapper.UsuarioMapper;
import dev.cristian.app.repository.usuarioRepository;
import dev.cristian.app.service.interfaces.UsuarioInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UsuarioInterface {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final usuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(usuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioDetalleDto> crear(UsuarioCrearDto dto) {
        logger.info("Intentando crear nuevo usuario: {}", dto.getCorreo());

        if (usuarioRepository.findByCorreoContainingIgnoreCase(dto.getCorreo()).stream()
                .anyMatch(u -> u.getCorreo().equalsIgnoreCase(dto.getCorreo()))) {
            logger.warn("Intento de crear usuario con correo duplicado: {}", dto.getCorreo());
            throw new RecursoDuplicadoException("Ya existe un usuario con el correo: " + dto.getCorreo());
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        logger.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getId());

        return new ResponseEntity<>(usuarioMapper.toDetalleDto(usuarioGuardado), HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UsuarioDetalleDto> obtenerPorId(Long id) {
        logger.info("Buscando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Usuario con ID {} no encontrado", id);
                    return new RecursoNoEncontradoException("Usuario con ID " + id + " no encontrado");
                });

        return ResponseEntity.ok(usuarioMapper.toDetalleDto(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioDetalleDto>> listarTodos() {
        logger.info("Listando todos los usuarios");

        List<UsuarioDetalleDto> usuarios = usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDetalleDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioDetalleDto> actualizar(Long id, UsuarioActualizarDto dto) {
        logger.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Usuario con ID {} no encontrado para actualización", id);
                    return new RecursoNoEncontradoException("Usuario con ID " + id + " no encontrado");
                });

        if (dto.getCorreo() != null &&
                !dto.getCorreo().equalsIgnoreCase(usuario.getCorreo()) &&
                usuarioRepository.findByCorreoContainingIgnoreCase(dto.getCorreo()).stream()
                        .anyMatch(u -> u.getCorreo().equalsIgnoreCase(dto.getCorreo()))) {
            logger.warn("Intento de actualizar usuario con correo duplicado: {}", dto.getCorreo());
            throw new RecursoDuplicadoException("Ya existe un usuario con el correo: " + dto.getCorreo());
        }

        usuarioMapper.actualizarDesdeDto(usuario, dto);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        logger.info("Usuario con ID {} actualizado exitosamente", id);

        return ResponseEntity.ok(usuarioMapper.toDetalleDto(usuarioActualizado));
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioDetalleDto> actualizarRolYEstatus(Long id, UsuarioActualizarRolEstatusDto dto) {
        logger.info("Actualizando rol y estatus de usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Usuario con ID {} no encontrado para actualización de rol/estatus", id);
                    return new RecursoNoEncontradoException("Usuario con ID " + id + " no encontrado");
                });

        usuarioMapper.actualizarRolYEstatus(usuario, dto);
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        logger.info("Rol y estatus de usuario con ID {} actualizados exitosamente", id);

        return ResponseEntity.ok(usuarioMapper.toDetalleDto(usuarioActualizado));
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioDetalleDto> eliminar(Long id) {
        logger.info("Eliminando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Usuario con ID {} no encontrado para eliminación", id);
                    return new RecursoNoEncontradoException("Usuario con ID " + id + " no encontrado");
                });

        usuarioRepository.delete(usuario);
        logger.info("Usuario con ID {} eliminado exitosamente", id);

        return ResponseEntity.ok(usuarioMapper.toDetalleDto(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioDetalleDto>> filtrarPorRol(Rol rol) {
        logger.info("Filtrando usuarios por rol: {}", rol);

        List<UsuarioDetalleDto> usuarios = usuarioRepository.findByRol(rol).stream()
                .map(usuarioMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios con rol: {}", rol);
            throw new RecursoNoEncontradoException("No se encontraron usuarios con rol: " + rol);
        }

        return ResponseEntity.ok(usuarios);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioDetalleDto>> filtrarPorEstatus(EstatusUsuario estatus) {
        logger.info("Filtrando usuarios por estatus: {}", estatus);

        List<UsuarioDetalleDto> usuarios = usuarioRepository.findByEstatusUsuario(estatus).stream()
                .map(usuarioMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios con estatus: {}", estatus);
            throw new RecursoNoEncontradoException("No se encontraron usuarios con estatus: " + estatus);
        }

        return ResponseEntity.ok(usuarios);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioDetalleDto>> buscarPorNombreOApellidos(String nombre, String apellidos) {
        logger.info("Buscando usuarios por nombre '{}' o apellidos '{}'", nombre, apellidos);

        List<UsuarioDetalleDto> usuarios = usuarioRepository
                .findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombre, apellidos).stream()
                .map(usuarioMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios con nombre '{}' o apellidos '{}'", nombre, apellidos);
            throw new RecursoNoEncontradoException(
                    String.format("No se encontraron usuarios con nombre '%s' o apellidos '%s'", nombre, apellidos));
        }

        return ResponseEntity.ok(usuarios);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<UsuarioDetalleDto>> buscarPorNombreRolYEstatus(String nombre, Rol rol, EstatusUsuario estatus) {
        logger.info("Buscando usuarios por nombre '{}', rol {} y estatus {}", nombre, rol, estatus);

        List<UsuarioDetalleDto> usuarios = usuarioRepository
                .findByNombreContainingIgnoreCaseAndRolAndEstatusUsuario(nombre, rol, estatus).stream()
                .map(usuarioMapper::toDetalleDto)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios con nombre '{}', rol {} y estatus {}", nombre, rol, estatus);
            throw new RecursoNoEncontradoException(
                    String.format("No se encontraron usuarios con nombre '%s', rol %s y estatus %s",
                            nombre, rol, estatus));
        }

        return ResponseEntity.ok(usuarios);
    }
}