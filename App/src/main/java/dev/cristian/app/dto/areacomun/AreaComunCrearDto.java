package dev.cristian.app.dto.areacomun;

import dev.cristian.app.enums.EstatusAreaComun;
import dev.cristian.app.enums.TipoDeArea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaComunCrearDto {

    @NotNull(message = "Debe de escoger un tipo de área")
    private TipoDeArea tipoDeArea;

    @NotBlank(message = "El nombre del área no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del área debe tener entre 3 y 100 caracteres")
    private String nombreDelArea;

    @NotNull(message = "Debe de escoger un estatus para área")
    private EstatusAreaComun estatusAreaComun;
}
