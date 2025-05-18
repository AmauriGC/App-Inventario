package dev.cristian.app.controller;

import dev.cristian.app.dto.usuario.*;
import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;
import dev.cristian.app.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("http://localhost:5173")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDetalleDto>> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetalleDto> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<UsuarioDetalleDto> crear(@Valid @RequestBody UsuarioCrearDto dto) {
        return usuarioService.crear(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDetalleDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioActualizarDto dto) {
        return usuarioService.actualizar(id, dto);
    }

    @PutMapping("/{id}/rol-estatus")
    public ResponseEntity<UsuarioDetalleDto> actualizarRolYEstatus(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioActualizarRolEstatusDto dto) {
        return usuarioService.actualizarRolYEstatus(id, dto);
    }

    @PutMapping("/{id}/contraseñ")
    public ResponseEntity<UsuarioDetalleDto> actualizarContrasena(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioActualizarContrasenaDto dto) {
        return usuarioService.actualizarContrasena(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioDetalleDto> eliminar(@PathVariable Long id) {
        return usuarioService.eliminar(id);
    }

    @GetMapping("/filtro/rol")
    public ResponseEntity<List<UsuarioDetalleDto>> filtrarPorRol(
            @RequestParam Rol rol) {
        return usuarioService.filtrarPorRol(rol);
    }

    @GetMapping("/filtro/estatus")
    public ResponseEntity<List<UsuarioDetalleDto>> filtrarPorEstatus(
            @RequestParam EstatusUsuario estatus) {
        return usuarioService.filtrarPorEstatus(estatus);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioDetalleDto>> buscarPorNombreOApellidos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellidos) {
        return usuarioService.buscarPorNombreOApellidos(nombre, apellidos);
    }

    @GetMapping("/buscar-avanzado")
    public ResponseEntity<List<UsuarioDetalleDto>> buscarPorNombreRolYEstatus(
            @RequestParam String nombre,
            @RequestParam Rol rol,
            @RequestParam EstatusUsuario estatus) {
        return usuarioService.buscarPorNombreRolYEstatus(nombre, rol, estatus);
    }

    @GetMapping("/filtrar-rol-estatus")
    public ResponseEntity<List<UsuarioDetalleDto>> filtrarPorRolYEstatus(
            @RequestParam Rol rol,
            @RequestParam EstatusUsuario estatus) {
        return usuarioService.filtrarPorRolYEstatus(rol, estatus);
    }
}