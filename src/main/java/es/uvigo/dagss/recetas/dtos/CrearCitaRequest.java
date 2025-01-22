package es.uvigo.dagss.recetas.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CrearCitaRequest {
    @NotNull(message = "La fecha y hora de la cita son obligatorias.")
    @Future(message = "La fecha y hora de la cita deben ser futuras.")
    private LocalDateTime fechaHora;
}

