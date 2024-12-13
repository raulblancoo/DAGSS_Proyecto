package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDto {
    private Paciente paciente;
    private Medico medico;
    private CentroDeSalud centroDeSalud;
    private LocalDateTime fechaHoraInicio;
    private Integer duracionMinutos;
    private Cita.EstadoCita estado;
}
