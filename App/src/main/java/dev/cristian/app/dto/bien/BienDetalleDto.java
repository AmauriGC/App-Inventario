package dev.cristian.app.dto.bien;

import dev.cristian.app.enums.EstatusBien;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BienDetalleDto {
    private Long id;
    private Long areaComunId;
    private Long marcaId;
    private Long modeloId;
    private Long tipoDeBienId;
    private Long usuarioId;
    private EstatusBien estatusBien;
}
