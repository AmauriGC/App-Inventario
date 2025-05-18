package dev.cristian.app.controller;

import dev.cristian.app.dto.marca.MarcaActualizarDto;
import dev.cristian.app.dto.marca.MarcaCrearDto;
import dev.cristian.app.dto.marca.MarcaDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<MarcaDetalleDto>>> listarTodas() {
        return marcaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MarcaDetalleDto>> obtenerPorId(@PathVariable Long id) {
        return marcaService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MarcaDetalleDto>> crear(@Valid @RequestBody MarcaCrearDto dto) {
        return marcaService.crear(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MarcaDetalleDto>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MarcaActualizarDto dto) {
        return marcaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<MarcaDetalleDto>> eliminar(@PathVariable Long id) {
        return marcaService.eliminar(id);
    }

    @GetMapping("/filtro")
    public ResponseEntity<ApiResponse<List<MarcaDetalleDto>>> filtrarPorEstatus(
            @RequestParam EstatusMarcaModeloTipoDeBien estatus) {
        return marcaService.filtrarPorEstatus(estatus);
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<MarcaDetalleDto>>> buscarPorNombre(
            @RequestParam String nombre) {
        return marcaService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar-estatus")
    public ResponseEntity<ApiResponse<List<MarcaDetalleDto>>> buscarPorNombreYEstatus(
            @RequestParam String nombre,
            @RequestParam EstatusMarcaModeloTipoDeBien estatus) {
        return marcaService.buscarPorNombreYEstatus(nombre, estatus);
    }
}