package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.usuario.*;
import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;
import dev.cristian.app.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsuarioInterface {

    ResponseEntity<ApiResponse<UsuarioDetalleDto>> crear(UsuarioCrearDto dto);

    ResponseEntity<ApiResponse<UsuarioDetalleDto>> obtenerPorId(Long id);

    ResponseEntity<ApiResponse<List<UsuarioDetalleDto>>> listarTodos();

    ResponseEntity<ApiResponse<UsuarioDetalleDto>> actualizar(Long id, UsuarioActualizarDto dto);

    ResponseEntity<ApiResponse<UsuarioDetalleDto>> actualizarRolYEstatus(Long id, UsuarioActualizarRolEstatusDto dto);

    ResponseEntity<ApiResponse<UsuarioDetalleDto>> actualizarContrasena(Long id, UsuarioActualizarContrasenaDto dto);

    ResponseEntity<ApiResponse<UsuarioDetalleDto>> eliminar(Long id);

    ResponseEntity<ApiResponse<List<UsuarioDetalleDto>>> filtrarPorRol(Rol rol);

    ResponseEntity<ApiResponse<List<UsuarioDetalleDto>>> filtrarPorEstatus(EstatusUsuario estatus);

    ResponseEntity<ApiResponse<List<UsuarioDetalleDto>>> buscarPorNombreOApellidos(String nombre, String apellidos);

    ResponseEntity<ApiResponse<List<UsuarioDetalleDto>>> buscarPorNombreRolYEstatus(String nombre, Rol rol, EstatusUsuario estatus);

    ResponseEntity<ApiResponse<List<UsuarioDetalleDto>>> filtrarPorRolYEstatus(Rol rol, EstatusUsuario estatus);
}
