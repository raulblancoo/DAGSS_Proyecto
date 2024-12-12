package es.uvigo.dagss.recetas.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CentroDeSalud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "centroDeSalud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medico> medicos;

    @OneToMany(mappedBy = "centroDeSalud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paciente> pacientes;
}
