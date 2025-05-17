package dev.cristian.app.dto.asignacion;

import dev.cristian.app.enums.EstatusAsignacion;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AsignacionCrearDto {

    @NotNull(message = "Debe de escoger un usuario para asignar")
    private Long usuarioId;

    @NotNull(message = "Debe de escoger un bien para asignar")
    private Long bienId;

    @NotNull(message = "Debe de escoger el estatus de la asignacion")
    private EstatusAsignacion estatusAsignacion;

    @NotNull(message = "Debe de escoger una fecha para la asignacion")
    @FutureOrPresent(message = "La fecha de asignación debe ser presente o futura")
    private LocalDateTime fechaDeAsignacion;

    private LocalDateTime fechaDeRevocacion;
}
