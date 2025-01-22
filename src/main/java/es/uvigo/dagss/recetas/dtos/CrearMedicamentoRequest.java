package es.uvigo.dagss.recetas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CrearMedicamentoRequest {
    @NotBlank(message = "El nombre comercial es obligatorio.")
    @Size(max = 100, message = "El nombre comercial no puede exceder los 100 caracteres.")
    private String nombreComercial;

    @NotBlank(message = "El principio activo es obligatorio.")
    @Size(max = 100, message = "El principio activo no puede exceder los 100 caracteres.")
    private String principioActivo;

    @NotBlank(message = "El fabricante es obligatorio.")
    @Size(max = 100, message = "El fabricante no puede exceder los 100 caracteres.")
    private String fabricante;

    @NotBlank(message = "La familia es obligatoria.")
    @Size(max = 100, message = "La familia no puede exceder los 100 caracteres.")
    private String familia;

    @Positive(message = "El número de dosis debe ser positivo.")
    private Integer numeroDosis;
}
