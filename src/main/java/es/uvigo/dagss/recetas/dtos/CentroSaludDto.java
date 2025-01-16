package es.uvigo.dagss.recetas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CentroSaludDto {
    private String nombre;
    private String localidad;
    private String provincia;
    private boolean activo;
}
