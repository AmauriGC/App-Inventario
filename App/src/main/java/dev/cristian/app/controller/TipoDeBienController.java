package dev.cristian.app.controller;

import dev.cristian.app.dto.tipodebien.TipoDeBienActualizarDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienCrearDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;
import dev.cristian.app.service.TipoDeBienService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-bien")
@CrossOrigin("http://localhost:5173")
public class TipoDeBienController {

    private final TipoDeBienService tipoDeBienService;

    public TipoDeBienController(TipoDeBienService tipoDeBienService) {
        this.tipoDeBienService = tipoDeBienService;
    }

    @GetMapping
    public ResponseEntity<List<TipoDeBienDetalleDto>> listarTodos() {
        return tipoDeBienService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDeBienDetalleDto> obtenerPorId(@PathVariable Long id) {
        return tipoDeBienService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<TipoDeBienDetalleDto> crear(@Valid @RequestBody TipoDeBienCrearDto dto) {
        return tipoDeBienService.crear(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDeBienDetalleDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TipoDeBienActualizarDto dto) {
        return tipoDeBienService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TipoDeBienDetalleDto> eliminar(@PathVariable Long id) {
        return tipoDeBienService.eliminar(id);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TipoDeBienDetalleDto>> buscarPorTipoYEstado(
            @RequestParam TipoBien tipo,
            @RequestParam EstatusMarcaModeloTipoDeBien estatus) {
        return tipoDeBienService.buscarPorTipoYEstado(tipo, estatus);
    }

    @GetMapping("/filtro-tipo")
    public ResponseEntity<List<TipoDeBienDetalleDto>> buscarPorTipo(
            @RequestParam TipoBien tipo) {
        return tipoDeBienService.buscarPorTipo(tipo);
    }

    @GetMapping("/filtro-estatus")
    public ResponseEntity<List<TipoDeBienDetalleDto>> filtrarPorEstatus(
            @RequestParam EstatusMarcaModeloTipoDeBien estatus) {
        return tipoDeBienService.filtrarPorEstatus(estatus);
    }
}