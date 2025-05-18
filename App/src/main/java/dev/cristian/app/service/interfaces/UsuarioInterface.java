package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.usuario.*;
import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsuarioInterface {

    ResponseEntity<UsuarioDetalleDto> crear(UsuarioCrearDto dto);

    ResponseEntity<UsuarioDetalleDto> obtenerPorId(Long id);

    ResponseEntity<List<UsuarioDetalleDto>> listarTodos();

    ResponseEntity<UsuarioDetalleDto> actualizar(Long id, UsuarioActualizarDto dto);

    ResponseEntity<UsuarioDetalleDto> actualizarRolYEstatus(Long id, UsuarioActualizarRolEstatusDto dto);

    ResponseEntity<UsuarioDetalleDto> actualizarContrasena(Long id, UsuarioActualizarContrasenaDto dto);

    ResponseEntity<UsuarioDetalleDto> eliminar(Long id);

    ResponseEntity<List<UsuarioDetalleDto>> filtrarPorRol(Rol rol);

    ResponseEntity<List<UsuarioDetalleDto>> filtrarPorEstatus(EstatusUsuario estatus);

    ResponseEntity<List<UsuarioDetalleDto>> buscarPorNombreOApellidos(String nombre, String apellidos);

    ResponseEntity<List<UsuarioDetalleDto>> buscarPorNombreRolYEstatus(String nombre, Rol rol, EstatusUsuario estatus);

    ResponseEntity<List<UsuarioDetalleDto>> filtrarPorRolYEstatus(Rol rol, EstatusUsuario estatus);
}
