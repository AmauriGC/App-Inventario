package dev.cristian.app.controller;

import dev.cristian.app.dto.bien.BienActualizarDto;
import dev.cristian.app.dto.bien.BienCrearDto;
import dev.cristian.app.dto.bien.BienDetalleDto;
import dev.cristian.app.enums.EstatusBien;
import dev.cristian.app.service.BienService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bien")
@CrossOrigin("http://localhost:5173")
public class BienController {

    private final BienService bienService;

    public BienController(BienService bienService) {
        this.bienService = bienService;
    }

    @GetMapping
    public ResponseEntity<List<BienDetalleDto>> listarTodos() {
        return bienService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BienDetalleDto> obtenerPorId(@PathVariable Long id) {
        return bienService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<BienDetalleDto> crear(@Valid @RequestBody BienCrearDto dto) {
        return bienService.crear(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BienDetalleDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody BienActualizarDto dto) {
        return bienService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BienDetalleDto> eliminar(@PathVariable Long id) {
        return bienService.eliminar(id);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<BienDetalleDto>> buscarPorAreaOMarca(
            @RequestParam(required = false) Long areaId,
            @RequestParam(required = false) Long marcaId) {
        return bienService.buscarPorAreaOMarca(areaId, marcaId);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<BienDetalleDto>> filtrarPorEstatus(
            @RequestParam EstatusBien estatus) {
        return bienService.filtrarPorEstatus(estatus);
    }
}