package dev.cristian.app.entity;

import dev.cristian.app.enums.EstatusBien;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "area_comun")
    private AreaComun areaComun;

    @ManyToOne
    @JoinColumn(name = "Marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "modelo")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "tipo_de_bien")
    private TipoDeBien tipoDeBien;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @Column
    @Enumerated(EnumType.STRING)
    private EstatusBien estatusBien;
}
