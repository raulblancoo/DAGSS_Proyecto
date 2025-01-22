package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Prescripcion;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearPrescripcionRequest {
    @NotNull(message = "El ID del medicamento es obligatorio.")
    private Long medicamentoId;

    @NotNull(message = "El ID del paciente es obligatorio.")
    private Long pacienteId;

    @NotNull(message = "El ID del médico es obligatorio.")
    private Long medicoId;

    @NotNull(message = "La dosis diaria es obligatoria.")
    @Positive(message = "La dosis diaria debe ser un valor positivo.")
    private Double dosisDiaria;

    @NotBlank(message = "Las indicaciones son obligatorias.")
    @Size(max = 500, message = "Las indicaciones no pueden exceder los 500 caracteres.")
    private String indicaciones;

    @NotNull(message = "La fecha de fin es obligatoria.")
    @Future(message = "La fecha de fin debe ser una fecha futura.")
    private LocalDate fechaFin;

    @NotNull(message = "El estado de la prescripción es obligatorio.")
    private Prescripcion.Estado estado;
}