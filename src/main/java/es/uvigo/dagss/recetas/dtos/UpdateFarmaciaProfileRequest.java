package es.uvigo.dagss.recetas.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateFarmaciaProfileRequest {
    @NotBlank(message = "El nombre del establecimiento es obligatorio.")
    @Size(max = 100, message = "El nombre del establecimiento no puede exceder los 100 caracteres.")
    private String nombreEstablecimiento;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 15, message = "El teléfono no puede exceder los 15 caracteres.")
    private String telefono;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "Debe proporcionar un email válido.")
    @Size(max = 150, message = "El email no puede exceder los 150 caracteres.")
    private String email;

    private DireccionDto direccion;
}
