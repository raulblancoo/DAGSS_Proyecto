package es.uvigo.dagss.recetas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "La contraseña actual es obligatoria.")
    private String currentPassword;

    @NotBlank(message = "La nueva contraseña es obligatoria.")
    @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres.")
    private String newPassword;

    @NotBlank(message = "La confirmación de la nueva contraseña es obligatoria.")
    private String confirmNewPassword;
}
