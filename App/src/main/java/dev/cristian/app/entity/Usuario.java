package dev.cristian.app.entity;

import dev.cristian.app.enums.EstatusUsuario;
import dev.cristian.app.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellidos;

    @Column
    private String correo;

    @Column
    private String contrasena;

    @Column
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column
    @Enumerated(EnumType.STRING)
    private EstatusUsuario estatusUsuario;
}
