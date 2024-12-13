package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Citas de un paciente en estado PLANIFICADA
    List<Cita> findByPacienteAndEstado(Paciente paciente, Cita.EstadoCita estado);

    // Citas planificadas para un médico en un día determinado
    @Query("SELECT c FROM Cita c WHERE c.medico = :medico AND " +
            "DATE(c.fechaHoraInicio) = DATE(:fecha)")
    List<Cita> findPlanificadasByMedicoAndFecha(Medico medico, LocalDateTime fecha);

    // Filtrar por estado y rango de fechas
    List<Cita> findByEstadoAndFechaHoraInicioBetween(Cita.EstadoCita estado, LocalDateTime inicio, LocalDateTime fin);
}
