package dev.cristian.app.dto.areacomun;

import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaComunDetalleDto {
    private Long id;
    private TipoDeArea tipoDeArea;
    private String nombreDelArea;
    private EstatusAreaComun estatusAreaComun;
}
