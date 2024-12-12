package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value = "ADMINISTRADOR")
public class Administrador extends Usuario {

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "FECHA_CREACION")
    private java.util.Date fechaCreacion;

    public Administrador() {
        super(TipoUsuario.ADMINISTRADOR);
        this.fechaCreacion = new java.util.Date();
    }

    public Administrador(String login, String password, String nombre, String email) {
        super(TipoUsuario.ADMINISTRADOR, login, password);
        this.nombre = nombre;
        this.email = email;
        this.fechaCreacion = new java.util.Date();
    }
}
