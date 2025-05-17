package dev.cristian.app.mapper;

import dev.cristian.app.dto.usuario.*;
import dev.cristian.app.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDetalleDto toDetalleDto(Usuario usuario) {
        UsuarioDetalleDto dto = new UsuarioDetalleDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());
        dto.setEstatusUsuario(usuario.getEstatusUsuario());
        return dto;
    }

    public UsuarioCrearDto toCrearDto(Usuario usuario) {
        UsuarioCrearDto dto = new UsuarioCrearDto();
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setCorreo(usuario.getCorreo());
        dto.setContrasena(usuario.getContrasena());
        dto.setRol(usuario.getRol());
        dto.setEstatusUsuario(usuario.getEstatusUsuario());
        return dto;
    }

    public Usuario toEntity(UsuarioCrearDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(dto.getRol());
        usuario.setEstatusUsuario(dto.getEstatusUsuario());
        return usuario;
    }

    public UsuarioActualizarDto toActualizarDto(Usuario usuario) {
        UsuarioActualizarDto dto = new UsuarioActualizarDto();
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setCorreo(usuario.getCorreo());
        return dto;
    }

    public void actualizarDesdeDto(Usuario usuario, UsuarioActualizarDto dto) {
        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getApellidos() != null) {
            usuario.setApellidos(dto.getApellidos());
        }
        if (dto.getCorreo() != null) {
            usuario.setCorreo(dto.getCorreo());
        }
    }

    public void actualizarRolYEstatus(Usuario usuario, UsuarioActualizarRolEstatusDto dto) {
        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }
        if (dto.getEstatusUsuario() != null) {
            usuario.setEstatusUsuario(dto.getEstatusUsuario());
        }
    }

    public void actualizarContrasena(Usuario usuario, UsuarioActualizarContrasenaDto dto) {
        if (dto.getContrasena() != null) {
            usuario.setContrasena(dto.getContrasena());
        }
    }
}
