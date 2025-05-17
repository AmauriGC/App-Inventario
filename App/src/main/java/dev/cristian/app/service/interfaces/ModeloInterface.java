package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.modelo.ModeloActualizarDto;
import dev.cristian.app.dto.modelo.ModeloCrearDto;
import dev.cristian.app.dto.modelo.ModeloDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ModeloInterface {
    ResponseEntity<ModeloDetalleDto> crear(ModeloCrearDto dto);

    ResponseEntity<ModeloDetalleDto> obtenerPorId(Long id);

    ResponseEntity<List<ModeloDetalleDto>> listarTodos();

    ResponseEntity<ModeloDetalleDto> actualizar(Long id, ModeloActualizarDto dto);

    ResponseEntity<ModeloDetalleDto> eliminar(Long id);

    ResponseEntity<List<ModeloDetalleDto>> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus);
}
