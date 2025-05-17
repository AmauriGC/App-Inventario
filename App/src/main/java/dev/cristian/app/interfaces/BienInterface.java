package dev.cristian.app.interfaces;

import dev.cristian.app.dto.bien.BienActualizarDto;
import dev.cristian.app.dto.bien.BienCrearDto;
import dev.cristian.app.dto.bien.BienDetalleDto;
import dev.cristian.app.enums.EstatusBien;

import java.util.List;

public interface BienInterface {
    BienDetalleDto crear(BienCrearDto dto);

    BienDetalleDto obtenerPorId(Long id);

    List<BienDetalleDto> listarTodos();

    void actualizar(Long id, BienActualizarDto dto);

    void eliminar(Long id);

    List<BienDetalleDto> buscarPorAreaOMarca(Long idArea, Long idMarca);

    List<BienDetalleDto> filtrarPorEstatus(EstatusBien estatus);
}
