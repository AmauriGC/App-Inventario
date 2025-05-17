package dev.cristian.app.interfaces;

import dev.cristian.app.dto.marca.MarcaActualizarDto;
import dev.cristian.app.dto.marca.MarcaCrearDto;
import dev.cristian.app.dto.marca.MarcaDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;

import java.util.List;

public interface MarcaInterface {
    MarcaDetalleDto crear(MarcaCrearDto dto);

    MarcaDetalleDto obtenerPorId(Long id);

    List<MarcaDetalleDto> listarTodas();

    void actualizar(Long id, MarcaActualizarDto dto);

    void eliminar(Long id);

    List<MarcaDetalleDto> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus);
}
