package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.tipodebien.TipoDeBienActualizarDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienCrearDto;
import dev.cristian.app.dto.tipodebien.TipoDeBienDetalleDto;
import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TipoDeBienInterface {
    ResponseEntity<TipoDeBienDetalleDto> crear(TipoDeBienCrearDto dto);

    ResponseEntity<TipoDeBienDetalleDto> obtenerPorId(Long id);

    ResponseEntity<List<TipoDeBienDetalleDto>> listarTodos();

    ResponseEntity<TipoDeBienDetalleDto> actualizar(Long id, TipoDeBienActualizarDto dto);

    ResponseEntity<TipoDeBienDetalleDto> eliminar(Long id);

    ResponseEntity<List<TipoDeBienDetalleDto>> buscarPorTipoYEstado(TipoBien tipo, EstatusMarcaModeloTipoDeBien estatus);
}
