package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Direccion implements Serializable {
    private String domicilio;
    private String localidad;
    private int codigoPostal;
    private String provincia;
}
