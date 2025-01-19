package es.uvigo.dagss.recetas.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServirRecetaRequest {
    @NotNull(message = "El ID de la receta es obligatorio.")
    private Long recetaId;

    @NotNull(message = "El ID de la farmacia es obligatorio.")
    private Long farmaciaId;
}
