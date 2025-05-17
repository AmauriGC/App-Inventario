package dev.cristian.app.dto.bien;

import dev.cristian.app.enums.EstatusBien;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BienCrearDto {

    @NotNull(message = "Debe de escoger un área común")
    private Long areaComunId;

    @NotNull(message = "Debe de escoger una marca")
    private Long marcaId;

    @NotNull(message = "Debe de escoger un modelo")
    private Long modeloId;

    @NotNull(message = "Debe de escoger un tipo de bien")
    private Long tipoDeBienId;

    @NotNull(message = "Debe de escoger un usuario")
    private Long usuarioId;

    @NotNull(message = "Debe de escoger el estatus para el bien")
    private EstatusBien estatusBien;
}
