package es.uvigo.dagss.recetas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PacienteDto {
    private String nombre;
    private String apellidos;
    private CentroSaludDto centroSalud;
    private String localidad;
    private String provincia;
    private boolean activo;
}
