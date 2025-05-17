package dev.cristian.app.interfaces;

import dev.cristian.app.dto.areacomun.AreaComunActualizarDto;
import dev.cristian.app.dto.areacomun.AreaComunCrearDto;
import dev.cristian.app.dto.areacomun.AreaComunDetalleDto;
import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;

import java.util.List;

public interface AreaComunInterface {
    AreaComunDetalleDto crear(AreaComunCrearDto dto);

    AreaComunDetalleDto obtenerPorId(Long id);

    List<AreaComunDetalleDto> listarTodas();

    void actualizar(Long id, AreaComunActualizarDto dto);

    void eliminar(Long id);

    List<AreaComunDetalleDto> buscarPorTipoYEstado(TipoDeArea tipo, EstatusAreaComun estatus);
}
