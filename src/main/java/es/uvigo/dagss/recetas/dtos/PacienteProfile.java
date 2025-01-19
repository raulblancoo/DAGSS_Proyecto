package es.uvigo.dagss.recetas.dtos;

import lombok.Data;

@Data
public class PacienteProfile {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private DireccionDto direccion;
}
