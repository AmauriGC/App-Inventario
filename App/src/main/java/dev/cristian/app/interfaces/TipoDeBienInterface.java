package dev.cristian.app.interfaces;

import dev.cristian.app.dto.tipodebien.TipoDeBienActualizarDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienCrearDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;

import java.util.List;

public interface TipoDeBienInterface {
    TipoDeBienDetalleDto crear(TipoDeBienCrearDto dto);

    TipoDeBienDetalleDto obtenerPorId(Long id);

    List<TipoDeBienDetalleDto> listarTodos();

    void actualizar(Long id, TipoDeBienActualizarDto dto);

    void eliminar(Long id);

    List<TipoDeBienDetalleDto> buscarPorTipoYEstado(TipoBien tipo, EstatusMarcaModeloTipoDeBien estatus);
}
