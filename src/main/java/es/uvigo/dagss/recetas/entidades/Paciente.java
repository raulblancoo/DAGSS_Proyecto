package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Usuario {

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDOS", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "DNI", nullable = false, unique = true, length = 20)
    private String dni;

    @Column(name = "TARJETA_SANITARIA", nullable = false, unique = true, length = 20)
    private String tarjetaSanitaria;

    @Column(name = "NUMERO_SEGURIDAD_SOCIAL", nullable = false, unique = true, length = 20)
    private String numeroSeguridadSocial;

    @Column(name = "DOMICILIO", length = 60)
    private String domicilio;

    @Column(name = "LOCALIDAD", length = 30)
    private String localidad;

    @Column(name = "CODIGO_POSTAL", length = 5)
    private int codigoPostal;

    @Column(name = "PROVINCIA", length = 30)
    private String provincia;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "FECHA_NACIMIENTO", nullable = false)
    private Date fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "CENTRO_ID", nullable = false)
    private CentroSalud centroSalud;

    @ManyToOne
    @JoinColumn(name = "MEDICO_ID", nullable = false)
    private Medico medico;


    public Paciente() {
        super(TipoUsuario.PACIENTE);
    }

    // Constructor adicional para inicializar tipo de usuario
    public Paciente(String login, String password, String nombre, String apellidos, String dni,
                    String tarjetaSanitaria, String numeroSeguridadSocial, String domicilio, String localidad,
                    int codigoPostal, String provincia, String telefono, String email, Date fechaNacimiento,
                    CentroSalud centroSalud, Medico medico) {
        super(TipoUsuario.PACIENTE, login, password);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.tarjetaSanitaria = tarjetaSanitaria;
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
        this.telefono = telefono;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.centroSalud = centroSalud;
        this.medico = medico;
    }
}
