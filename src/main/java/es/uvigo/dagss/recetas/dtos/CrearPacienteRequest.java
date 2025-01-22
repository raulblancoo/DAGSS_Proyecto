package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Direccion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CrearPacienteRequest {
    @NotBlank(message = "El login es obligatorio.")
    @Size(max = 50, message = "El login no puede exceder los 50 caracteres.")
    private String login;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(max = 100, message = "Los apellidos no pueden exceder los 100 caracteres.")
    private String apellidos;

    @NotBlank(message = "El DNI es obligatorio.")
    @Size(max = 15, message = "El DNI no puede exceder los 15 caracteres.")
    private String dni;

    @NotBlank(message = "El número de tarjeta sanitaria es obligatorio.")
    @Size(max = 20, message = "El número de tarjeta sanitaria no puede exceder los 20 caracteres.")
    private String tarjetaSanitaria;

    @NotBlank(message = "El número de la Seguridad Social es obligatorio.")
    @Size(max = 20, message = "El número de la Seguridad Social no puede exceder los 20 caracteres.")
    private String numeroSeguridadSocial;

    private Direccion direccion;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 15, message = "El teléfono no puede exceder los 15 caracteres.")
    private String telefono;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "Debe proporcionar un email válido.")
    @Size(max = 150, message = "El email no puede exceder los 150 caracteres.")
    private String email;

    private Date fechaNacimiento;

    @NotBlank(message = "El centro de salud asignado es obligatorio.")
    private Long centroSaludId;

    @NotBlank(message = "El médico asignado es obligatorio.")
    private Long medicoAsignadoId;
}
