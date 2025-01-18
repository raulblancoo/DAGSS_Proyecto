package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    /**
     * Encuentra todas las citas dentro de una fecha específica.
     */
    List<Cita> findByFechaHoraInicioBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Sort sort);

    /**
     * Encuentra todas las citas dentro de una fecha específica y filtradas por médico.
     */
    List<Cita> findByFechaHoraInicioBetweenAndMedicoId(LocalDateTime startOfDay, LocalDateTime endOfDay, Long medicoId, Sort sort);

    /**
     * Encuentra todas las citas dentro de una fecha específica y filtradas por paciente.
     */
    List<Cita> findByFechaHoraInicioBetweenAndPacienteId(LocalDateTime startOfDay, LocalDateTime endOfDay, Long pacienteId, Sort sort);

    /**
     * Encuentra todas las citas dentro de una fecha específica y filtradas por médico y paciente.
     */
    List<Cita> findByFechaHoraInicioBetweenAndMedicoIdAndPacienteId(LocalDateTime startOfDay, LocalDateTime endOfDay, Long medicoId, Long pacienteId, Sort sort);

    List<Cita> findByMedico_NumeroColegiado(String numeroColegiado);



    // NEW
    List<Cita> findByMedicoAndFechaHoraInicioBetween(Medico medico, LocalDateTime start, LocalDateTime end);
    Optional<Cita> findByIdAndPaciente(Long id, Paciente paciente);
    List<Cita> findByPacienteAndEstado(Paciente paciente, Cita.EstadoCita estado);
}
