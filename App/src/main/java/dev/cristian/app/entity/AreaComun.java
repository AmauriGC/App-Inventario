package dev.cristian.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "area_comun")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaComun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoDeArea tipoDeArea;

    @Column
    private String nombreDelArea;

    @Column
    @Enumerated(EnumType.STRING)
    private Estatus estatus;
}
