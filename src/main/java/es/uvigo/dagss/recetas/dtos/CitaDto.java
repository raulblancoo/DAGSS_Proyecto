package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Cita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDto {
    private Long id;
    private String pacienteNombre;
    private String medicoNombre;
    private String centroSaludNombre;
    private LocalDateTime fechaHoraInicio;
    private Integer duracionMinutos;
    private Cita.EstadoCita estado;
}
