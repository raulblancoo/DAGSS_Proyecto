package es.uvigo.dagss.recetas.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import es.uvigo.dagss.recetas.entidades.Cita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDto {
    @JsonView({Vistas.VistaCitaAdmin.class, Vistas.VistaCitaMedico.class})
    private String pacienteNombre;

    @JsonView(Vistas.VistaCitaAdmin.class)
    private String medicoNombre;

    @JsonView(Vistas.VistaCitaAdmin.class)
    private String centroSaludNombre;

    @JsonView({Vistas.VistaCitaAdmin.class, Vistas.VistaCitaMedico.class})
    private LocalDateTime fechaHoraInicio;

    @JsonView(Vistas.VistaCitaMedico.class)
    private Integer duracionMinutos;

    @JsonView({Vistas.VistaCitaAdmin.class, Vistas.VistaCitaMedico.class})
    private Cita.EstadoCita estado;
}
