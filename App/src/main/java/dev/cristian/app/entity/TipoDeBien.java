package dev.cristian.app.entity;

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
    private Tipo tipo;

    @Column
    @Enumerated(EnumType.STRING)
    private Estatus estatus;
}
