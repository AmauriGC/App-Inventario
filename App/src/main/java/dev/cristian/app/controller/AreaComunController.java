package dev.cristian.app.controller;

import dev.cristian.app.dto.areacomun.AreaComunActualizarDto;
import dev.cristian.app.dto.areacomun.AreaComunCrearDto;
import dev.cristian.app.dto.areacomun.AreaComunDetalleDto;
import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;
import dev.cristian.app.service.AreaComunService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/area-comun")
@CrossOrigin("http://localhost:5173")
public class AreaComunController {

    private final AreaComunService areaComunService;

    public AreaComunController(AreaComunService areaComunService) {
        this.areaComunService = areaComunService;
    }

    @GetMapping
    public ResponseEntity<List<AreaComunDetalleDto>> listarTodas() {
        return areaComunService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaComunDetalleDto> obtenerPorId(@PathVariable Long id) {
        return areaComunService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<AreaComunDetalleDto> crear(@Valid @RequestBody AreaComunCrearDto dto) {
        return areaComunService.crear(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaComunDetalleDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AreaComunActualizarDto dto) {
        return areaComunService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AreaComunDetalleDto> eliminar(@PathVariable Long id) {
        return areaComunService.eliminar(id);
    }

    @GetMapping("/tipo-estado")
    public ResponseEntity<List<AreaComunDetalleDto>> buscarPorTipoYEstado(
            @RequestParam TipoDeArea tipo,
            @RequestParam EstatusAreaComun estatus) {
        return areaComunService.buscarPorTipoYEstado(tipo, estatus);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AreaComunDetalleDto>> buscarPorNombre(
            @RequestParam String nombre) {
        return areaComunService.buscarPorNombre(nombre);
    }

    @GetMapping("/estado")
    public ResponseEntity<List<AreaComunDetalleDto>> filtrarPorEstado(
            @RequestParam EstatusAreaComun estatus) {
        return areaComunService.buscarPorEstado(estatus);
    }
}