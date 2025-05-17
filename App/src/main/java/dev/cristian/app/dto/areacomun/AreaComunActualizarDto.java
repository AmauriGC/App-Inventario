package dev.cristian.app.dto.areacomun;

import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaComunActualizarDto {

    private TipoDeArea tipoDeArea;

    @Size(min = 3, max = 100, message = "El nombre del área debe tener entre 3 y 100 caracteres")
    private String nombreDelArea;

    private EstatusAreaComun estatusAreaComun;
}
