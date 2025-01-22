package es.uvigo.dagss.recetas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicoDto {
    private String nombre;
    private String apellidos;
    // TODO: revisar mapeo
//    private CentroSaludDto centroSalud;
    private String centro;
    private String localidad;
    private String provincia;
    private boolean activo;
}
