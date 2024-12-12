package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@DiscriminatorValue(value = "MEDICO")
@EqualsAndHashCode(callSuper = true)
public class Medico extends Usuario {

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroColegiado;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "centro_id", nullable = false)
    private CentroDeSalud centroDeSalud;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paciente> pacientes;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receta> recetas;

    public Medico() {
        super(TipoUsuario.MEDICO);
    }

    public Medico(String nombre, String apellidos, String dni, String numeroColegiado, String telefono, String email, CentroDeSalud centroDeSalud) {
        super(TipoUsuario.MEDICO);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.numeroColegiado = numeroColegiado;
        this.telefono = telefono;
        this.email = email;
        this.centroDeSalud = centroDeSalud;
    }
}
