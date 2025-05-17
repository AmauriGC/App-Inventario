package dev.cristian.app.controller;

import dev.cristian.app.dto.asignacion.AsignacionActualizarDto;
import dev.cristian.app.dto.asignacion.AsignacionCrearDto;
import dev.cristian.app.dto.asignacion.AsignacionDetalleDto;
import dev.cristian.app.enums.EstatusAsignacion;
import dev.cristian.app.service.AsignacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
@CrossOrigin("http://localhost:5173")
public class AsignacionesController {

    private final AsignacionService asignacionService;

    public AsignacionesController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @GetMapping
    public ResponseEntity<List<AsignacionDetalleDto>> listarTodas() {
        return asignacionService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignacionDetalleDto> obtenerPorId(@PathVariable Long id) {
        return asignacionService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<AsignacionDetalleDto> crear(@Valid @RequestBody AsignacionCrearDto dto) {
        return asignacionService.asignar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignacionDetalleDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AsignacionActualizarDto dto) {
        return asignacionService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AsignacionDetalleDto> eliminar(@PathVariable Long id) {
        return asignacionService.eliminar(id);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AsignacionDetalleDto>> buscarPorUsuarioOBien(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long bienId) {
        return asignacionService.buscarPorUsuarioOBien(usuarioId, bienId);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<AsignacionDetalleDto>> filtrarPorEstatus(
            @RequestParam EstatusAsignacion estatus) {
        return asignacionService.filtrarPorEstatus(estatus);
    }

    @GetMapping("/usuario-fechas")
    public ResponseEntity<List<AsignacionDetalleDto>> buscarPorUsuarioYRangoFechas(
            @RequestParam Long usuarioId,
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return asignacionService.buscarAsignacionesPorUsuarioYRangoFechas(usuarioId, inicio, fin);
    }
}