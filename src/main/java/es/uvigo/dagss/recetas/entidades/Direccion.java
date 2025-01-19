package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Direccion implements Serializable {
    private String domicilio;
    private String localidad;
    private int codigoPostal;
    private String provincia;

    @Override
    public String toString() {
        return domicilio + ", " +  localidad + ", " +  codigoPostal + ", " +  provincia;
    }
}
