package es.uvigo.dagss.recetas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DireccionDto {
    @NotBlank(message = "El domicilio es obligatorio.")
    @Size(max = 150, message = "El domicilio no puede exceder los 150 caracteres.")
    private String domicilio;

    @NotBlank(message = "La localidad es obligatoria.")
    @Size(max = 100, message = "La localidad no puede exceder los 100 caracteres.")
    private String localidad;

    @NotBlank(message = "El código postal es obligatorio.")
    @Size(max = 10, message = "El código postal no puede exceder los 10 caracteres.")
    private int codigoPostal;

    @NotBlank(message = "La provincia es obligatoria.")
    @Size(max = 100, message = "La provincia no puede exceder los 100 caracteres.")
    private String provincia;
}
