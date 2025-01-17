package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CentroSalud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Embedded
    private Direccion direccion;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private Boolean activo = true;
}
