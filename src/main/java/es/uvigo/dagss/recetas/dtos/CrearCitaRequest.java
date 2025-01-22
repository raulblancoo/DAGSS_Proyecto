package es.uvigo.dagss.recetas.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Data
public class CrearCitaRequest {
    @NotNull(message = "La fecha y hora de la cita son obligatorias.")
    @Future(message = "La fecha y hora de la cita deben ser futuras.")
    private LocalDateTime fechaHora;

    // Lista de horarios permitidos
    private static final List<LocalTime> HORARIOS_PERMITIDOS = Arrays.asList(
            LocalTime.of(8, 30),
            LocalTime.of(8, 45),
            LocalTime.of(9, 0),
            LocalTime.of(9, 15),
            LocalTime.of(9, 30),
            LocalTime.of(9, 45),
            LocalTime.of(10, 0),
            LocalTime.of(10, 15),
            LocalTime.of(10, 30),
            LocalTime.of(10, 45),
            LocalTime.of(11, 0),
            LocalTime.of(11, 15),
            LocalTime.of(11, 30),
            LocalTime.of(11, 45),
            LocalTime.of(12, 0),
            LocalTime.of(12, 15),
            LocalTime.of(12, 30),
            LocalTime.of(12, 45),
            LocalTime.of(13, 0),
            LocalTime.of(13, 15),
            LocalTime.of(13, 30),
            LocalTime.of(13, 45),
            LocalTime.of(14, 0),
            LocalTime.of(14, 15),
            LocalTime.of(14, 30),
            LocalTime.of(14, 45),
            LocalTime.of(15, 0),
            LocalTime.of(15, 15)
    );

    @AssertTrue(message = "El horario no es válido. Horarios permitidos: 08:30, 08:45, ..., 15:15.")
    private boolean isFechaHoraValida() {
        if (fechaHora == null) {
            return true; // @NotNull se encargará de esto
        }
        LocalTime hora = fechaHora.toLocalTime().withSecond(0).withNano(0);
        return HORARIOS_PERMITIDOS.contains(hora);
    }
}
