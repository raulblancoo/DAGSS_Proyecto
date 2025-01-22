package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Usuario {

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(nullable = false, unique = true, length = 20)
    private String tarjetaSanitaria;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroSeguridadSocial;

    @Embedded
    private Direccion direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private CentroSalud centroSalud;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Medico medico;

    public Paciente() {
        super(TipoUsuario.PACIENTE);
    }

    // Constructor adicional para inicializar tipo de usuario
    public Paciente(String login, String password, String nombre, String apellidos, String dni,
                    String tarjetaSanitaria, String numeroSeguridadSocial,Direccion direccion, String telefono, String email, Date fechaNacimiento,
                    CentroSalud centroSalud, Medico medico) {
        super(TipoUsuario.PACIENTE, login, password);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.tarjetaSanitaria = tarjetaSanitaria;
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.centroSalud = centroSalud;
        this.medico = medico;
    }
}
