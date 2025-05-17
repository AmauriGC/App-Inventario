package dev.cristian.app.controller;

import dev.cristian.app.dto.marca.MarcaActualizarDto;
import dev.cristian.app.dto.marca.MarcaCrearDto;
import dev.cristian.app.dto.marca.MarcaDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marca")
@CrossOrigin("http://localhost:5173")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<MarcaDetalleDto>> listarTodas() {
        return marcaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDetalleDto> obtenerPorId(@PathVariable Long id) {
        return marcaService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<MarcaDetalleDto> crear(@Valid @RequestBody MarcaCrearDto dto) {
        return marcaService.crear(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDetalleDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MarcaActualizarDto dto) {
        return marcaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MarcaDetalleDto> eliminar(@PathVariable Long id) {
        return marcaService.eliminar(id);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<MarcaDetalleDto>> filtrarPorEstatus(
            @RequestParam EstatusMarcaModeloTipoDeBien estatus) {
        return marcaService.filtrarPorEstatus(estatus);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MarcaDetalleDto>> buscarPorNombre(
            @RequestParam String nombre) {
        return marcaService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar-estatus")
    public ResponseEntity<List<MarcaDetalleDto>> buscarPorNombreYEstatus(
            @RequestParam String nombre,
            @RequestParam EstatusMarcaModeloTipoDeBien estatus) {
        return marcaService.buscarPorNombreYEstatus(nombre, estatus);
    }
}