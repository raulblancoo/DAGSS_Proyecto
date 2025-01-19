package es.uvigo.dagss.recetas.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Direccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDto {
    @JsonView({Vistas.VistaCitaAdmin.class, Vistas.VistaCitaAgendaMedico.class, Vistas.VistaCitaDetalleMedico.class})
    private String paciente;

    @JsonView(Vistas.VistaCitaDetalleMedico.class)
    private Date fechaNacimiento;

    @JsonView(Vistas.VistaCitaDetalleMedico.class)
    private String direccion;

    @JsonView({Vistas.VistaCitaAdmin.class, Vistas.VistaCitaDetallePaciente.class})
    private String medico;

    @JsonView({Vistas.VistaCitaAdmin.class, Vistas.VistaCitaDetallePaciente.class})
    private String centroSalud;

    @JsonView({Vistas.VistaCitaAdmin.class, Vistas.VistaCitaAgendaMedico.class, Vistas.VistaCitaDetallePaciente.class})
    private LocalDateTime fechaHoraInicio;

    @JsonView({Vistas.VistaCitaAgendaMedico.class,Vistas.VistaCitaDetallePaciente.class})
    private Integer duracionMinutos;

    @JsonView({Vistas.VistaCitaAdmin.class, Vistas.VistaCitaAgendaMedico.class, Vistas.VistaCitaDetallePaciente.class})
    private Cita.EstadoCita estado;
}
