package dev.cristian.app.interfaces;

import dev.cristian.app.dto.usuario.UsuarioActualizarDto;
import dev.cristian.app.dto.usuario.UsuarioActualizarRolEstatusDto;
import dev.cristian.app.dto.usuario.UsuarioCrearDto;
import dev.cristian.app.dto.usuario.UsuarioDetalleDto;
import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;

import java.util.List;

public interface UsuarioInterface {

    UsuarioDetalleDto crear(UsuarioCrearDto dto);

    UsuarioDetalleDto obtenerPorId(Long id);

    List<UsuarioDetalleDto> listarTodos();

    void actualizar(Long id, UsuarioActualizarDto dto);

    void actualizarRolYEstatus(Long id, UsuarioActualizarRolEstatusDto dto);

    void eliminar(Long id);

    List<UsuarioDetalleDto> filtrarPorRol(Rol rol);

    List<UsuarioDetalleDto> filtrarPorEstatus(EstatusUsuario estatus);
}
