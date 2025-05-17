package dev.cristian.app.entity;

import dev.cristian.app.enums.EstatusMarcaModeloTipoDeBien;
import dev.cristian.app.enums.TipoBien;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_de_bien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoDeBien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoBien tipoBien;

    @Column
    @Enumerated(EnumType.STRING)
    private EstatusMarcaModeloTipoDeBien estatusMarcaModeloTipoDeBien;
}
