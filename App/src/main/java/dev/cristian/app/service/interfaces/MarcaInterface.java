package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.marca.MarcaActualizarDto;
import dev.cristian.app.dto.marca.MarcaCrearDto;
import dev.cristian.app.dto.marca.MarcaDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarcaInterface {
    ResponseEntity<MarcaDetalleDto> crear(MarcaCrearDto dto);

    ResponseEntity<MarcaDetalleDto> obtenerPorId(Long id);

    ResponseEntity<List<MarcaDetalleDto>> listarTodas();

    ResponseEntity<MarcaDetalleDto> actualizar(Long id, MarcaActualizarDto dto);

    ResponseEntity<MarcaDetalleDto> eliminar(Long id);

    ResponseEntity<List<MarcaDetalleDto>> filtrarPorEstatus(EstatusMarcaModeloTipoDeBien estatus);
}
