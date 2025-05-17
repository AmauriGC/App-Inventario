package dev.cristian.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "asignaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asignaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "bien")
    private Bien bien;

    @Column
    @Enumerated(EnumType.STRING)
    private Estatus estatus;

    @Column
    private LocalDateTime fechaDeAsignacion;

    @Column
    private LocalDateTime fechaDeRevocacion;
}
