package dev.cristian.app.controller;

import dev.cristian.app.dto.modelo.ModeloActualizarDto;
import dev.cristian.app.dto.modelo.ModeloCrearDto;
import dev.cristian.app.dto.modelo.ModeloDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.service.ModeloService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelo")
@CrossOrigin("http://localhost:5173")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @GetMapping
    public ResponseEntity<List<ModeloDetalleDto>> listarTodos() {
        return modeloService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloDetalleDto> obtenerPorId(@PathVariable Long id) {
        return modeloService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<ModeloDetalleDto> crear(@Valid @RequestBody ModeloCrearDto dto) {
        return modeloService.crear(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloDetalleDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ModeloActualizarDto dto) {
        return modeloService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ModeloDetalleDto> eliminar(@PathVariable Long id) {
        return modeloService.eliminar(id);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ModeloDetalleDto>> filtrarPorEstatus(
            @RequestParam EstatusMarcaModeloTipoDeBien estatus) {
        return modeloService.filtrarPorEstatus(estatus);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ModeloDetalleDto>> buscarPorNombre(
            @RequestParam String nombre) {
        return modeloService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar-estatus")
    public ResponseEntity<List<ModeloDetalleDto>> buscarPorNombreYEstatus(
            @RequestParam String nombre,
            @RequestParam EstatusMarcaModeloTipoDeBien estatus) {
        return modeloService.buscarPorNombreYEstatus(nombre, estatus);
    }
}