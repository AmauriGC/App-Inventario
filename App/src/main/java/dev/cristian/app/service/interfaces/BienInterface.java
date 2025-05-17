package dev.cristian.app.service.interfaces;

import dev.cristian.app.dto.bien.BienActualizarDto;
import dev.cristian.app.dto.bien.BienCrearDto;
import dev.cristian.app.dto.bien.BienDetalleDto;
import dev.cristian.app.enums.EstatusBien;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BienInterface {
    ResponseEntity<BienDetalleDto> crear(BienCrearDto dto);

    ResponseEntity<BienDetalleDto> obtenerPorId(Long id);

    ResponseEntity<List<BienDetalleDto>> listarTodos();

    ResponseEntity<BienDetalleDto> actualizar(Long id, BienActualizarDto dto);

    ResponseEntity<BienDetalleDto> eliminar(Long id);

    ResponseEntity<List<BienDetalleDto>> buscarPorAreaOMarca(Long idArea, Long idMarca);

    ResponseEntity<List<BienDetalleDto>> filtrarPorEstatus(EstatusBien estatus);
}
