package dev.cristian.app.interfaces;

import dev.cristian.app.dto.modelo.ModeloActualizarDto;
import dev.cristian.app.dto.modelo.ModeloCrearDto;
import dev.cristian.app.dto.modelo.ModeloDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;

import java.util.List;

public interface ModeloInterface {
    ModeloDetalleDto crear(ModeloCrearDto dto);

    ModeloDetalleDto obtenerPorId(Long id);

    List<ModeloDetalleDto> listarTodos();

    void actualizar(Long id, ModeloActualizarDto dto);

    void eliminar(Long id);

    List<ModeloDetalleDto> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus);
}
