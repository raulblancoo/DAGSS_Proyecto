package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    /**
     * Encuentra todas las citas dentro de una fecha específica.
     *
     * @param startOfDay Inicio del día.
     * @param endOfDay   Fin del día.
     * @param sort       Ordenamiento.
     * @return Lista de citas.
     */
    List<Cita> findByFechaHoraInicioBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Sort sort);

    /**
     * Encuentra todas las citas dentro de una fecha específica y filtradas por médico.
     *
     * @param startOfDay Inicio del día.
     * @param endOfDay   Fin del día.
     * @param medicoId   ID del médico.
     * @param sort       Ordenamiento.
     * @return Lista de citas.
     */
    List<Cita> findByFechaHoraInicioBetweenAndMedicoId(LocalDateTime startOfDay, LocalDateTime endOfDay, Long medicoId, Sort sort);

    /**
     * Encuentra todas las citas dentro de una fecha específica y filtradas por paciente.
     *
     * @param startOfDay  Inicio del día.
     * @param endOfDay    Fin del día.
     * @param pacienteId  ID del paciente.
     * @param sort        Ordenamiento.
     * @return Lista de citas.
     */
    List<Cita> findByFechaHoraInicioBetweenAndPacienteId(LocalDateTime startOfDay, LocalDateTime endOfDay, Long pacienteId, Sort sort);

    /**
     * Encuentra todas las citas dentro de una fecha específica y filtradas por médico y paciente.
     *
     * @param startOfDay  Inicio del día.
     * @param endOfDay    Fin del día.
     * @param medicoId    ID del médico.
     * @param pacienteId  ID del paciente.
     * @param sort        Ordenamiento.
     * @return Lista de citas.
     */
    List<Cita> findByFechaHoraInicioBetweenAndMedicoIdAndPacienteId(LocalDateTime startOfDay, LocalDateTime endOfDay, Long medicoId, Long pacienteId, Sort sort);
}
