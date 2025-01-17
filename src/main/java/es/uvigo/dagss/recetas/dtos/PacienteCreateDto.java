package es.uvigo.dagss.recetas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PacienteCreateDto {
    private String nombre;
    private String apellidos;
    private String dni;
    private String tarjetaSanitaria;
    private String numeroSeguridadSocial;
    private String domicilio;
    private String localidad;
    private int codigoPostal;
    private String provincia;
    private String telefono;
    private String email;
    private Date fechaNacimiento;
    private Long centroSaludId;
    private Long medicoId;
}

