package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@DiscriminatorValue(value = "FARMACIA")
@EqualsAndHashCode(callSuper = true)
public class Farmacia extends Usuario {

    @Column(nullable = false, length = 100)
    private String nombreEstablecimiento;

    @Column(nullable = false, length = 50)
    private String nombreFarmaceutico;

    @Column(nullable = false, length = 100)
    private String apellidosFarmaceutico;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroColegiado;

    @Column(nullable = false)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "farmacia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receta> recetas;

    public Farmacia() {
        super(TipoUsuario.FARMACIA);
    }

    public Farmacia(String nombreEstablecimiento, String nombreFarmaceutico, String apellidosFarmaceutico, String dni, String numeroColegiado, String direccion, String telefono, String email) {
        super(TipoUsuario.FARMACIA);
        this.nombreEstablecimiento = nombreEstablecimiento;
        this.nombreFarmaceutico = nombreFarmaceutico;
        this.apellidosFarmaceutico = apellidosFarmaceutico;
        this.dni = dni;
        this.numeroColegiado = numeroColegiado;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
}
