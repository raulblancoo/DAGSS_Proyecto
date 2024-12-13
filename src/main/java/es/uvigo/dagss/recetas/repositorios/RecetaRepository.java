package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    // TODO: comprobar si este método puede sustituír al de debajo
    // Obtener recetas activas por paciente
    List<Receta> findByPacienteAndEstadoAndFechaFinAfter(Paciente paciente, Receta.EstadoReceta estado, LocalDate fechaActual);

    // Obtener recetas planificadas disponibles
    @Query("SELECT r FROM Receta r WHERE r.estado = 'PLANIFICADA' AND " +
            "r.fechaInicio <= :fechaActual AND r.fechaFin >= :fechaActual")
    List<Receta> findRecetasDisponibles(LocalDate fechaActual);


    // TODO: Seguramente haya ye filtrar también por el estado 'PLANIFICADA'
    @Query("SELECT new es.uvigo.dagss.recetas.dtos.RecetaDto( " +
            "me.nombreComercial, m.nombre, r.fechaInicio, r.fechaFin, r.estado) " +
            "FROM Receta r " +
            "JOIN r.medico m " +
            "JOIN r.medicamento me " +
            "WHERE r.paciente = :paciente " +
            "AND r.fechaInicio <= :fechaActual " +
            "AND r.fechaFin >= :fechaActual")
    List<RecetaDto> findRecetasActivasPorPaciente(Paciente paciente, LocalDate fechaActual);

    // Buscar una receta por ID y validar que esté en estado PLANIFICADA y dentro del periodo de validez
    @Query("SELECT r FROM Receta r WHERE r.id = :id AND r.estado = 'PLANIFICADA' " +
            "AND r.fechaInicio <= :fechaActual AND r.fechaFin >= :fechaActual")
    Optional<Receta> findRecetaPlanificadaValida(Long id, LocalDate fechaActual);
}