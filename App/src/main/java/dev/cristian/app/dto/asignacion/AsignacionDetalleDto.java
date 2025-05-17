package dev.cristian.app.dto.asignacion;

import dev.cristian.app.enums.EstatusAsignacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AsignacionDetalleDto {
    private Long id;
    private Long usuarioId;
    private Long bienId;
    private EstatusAsignacion estatusAsignacion;
    private LocalDateTime fechaDeAsignacion;
    private LocalDateTime fechaDeRevocacion;
}
