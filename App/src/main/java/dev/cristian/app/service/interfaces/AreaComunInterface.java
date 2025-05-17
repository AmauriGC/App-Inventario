package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.areacomun.AreaComunActualizarDto;
import dev.cristian.app.dto.areacomun.AreaComunCrearDto;
import dev.cristian.app.dto.areacomun.AreaComunDetalleDto;
import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AreaComunInterface {
    ResponseEntity<AreaComunDetalleDto> crear(AreaComunCrearDto dto);

    ResponseEntity<AreaComunDetalleDto> obtenerPorId(Long id);

    ResponseEntity<List<AreaComunDetalleDto>> listarTodas();

    ResponseEntity<AreaComunDetalleDto> actualizar(Long id, AreaComunActualizarDto dto);

    ResponseEntity<AreaComunDetalleDto> eliminar(Long id);

    ResponseEntity<List<AreaComunDetalleDto>> buscarPorTipoYEstado(TipoDeArea tipo, EstatusAreaComun estatus);

    ResponseEntity<List<AreaComunDetalleDto>> buscarPorNombre(String nombre);

    ResponseEntity<List<AreaComunDetalleDto>> buscarPorEstado(EstatusAreaComun estatus);
}