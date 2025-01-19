package es.uvigo.dagss.recetas.dtos;

import lombok.Data;

@Data
public class FarmaciaProfile {
    private String nombreEstablecimiento;
    private String telefono;
    private String email;
    private DireccionDto direccion;
}
