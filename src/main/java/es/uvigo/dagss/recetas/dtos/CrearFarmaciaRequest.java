package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Direccion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CrearFarmaciaRequest {
    @NotBlank(message = "El login es obligatorio.")
    @Size(max = 50, message = "El login no puede exceder los 50 caracteres.")
    private String login;

    @NotBlank(message = "El nombre del establecimiento es obligatorio.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    private String nombreEstablecimiento;

    @NotBlank(message = "El nombre del farmacéutico es obligatorio.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String nombreFarmaceutico;

    @NotBlank(message = "Los apellidos del farmacéutico son obligatorios.")
    @Size(max = 100, message = "Los apellidos no pueden exceder los 100 caracteres.")
    private String apellidosFarmaceutico;

    @NotBlank(message = "El DNI/NIF del farmacéutico es obligatorio.")
    @Size(max = 15, message = "El DNI/NIF no puede exceder los 15 caracteres.")
    private String dniFarmaceutico;

    @NotBlank(message = "El número de colegiado del farmacéutico es obligatorio.")
    @Size(max = 20, message = "El número de colegiado no puede exceder los 20 caracteres.")
    private String numeroColegiadoFarmaceutico;

    private Direccion direccion;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 15, message = "El teléfono no puede exceder los 15 caracteres.")
    private String telefono;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "Debe proporcionar un email válido.")
    @Size(max = 150, message = "El email no puede exceder los 150 caracteres.")
    private String email;
}
