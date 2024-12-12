package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    // Obtener recetas activas por paciente
    List<Receta> findByPacienteAndEstadoAndFechaFinAfter(Paciente paciente, Receta.EstadoReceta estado, LocalDate fechaActual);

    // Obtener recetas planificadas disponibles
    @Query("SELECT r FROM Receta r WHERE r.estado = 'PLANIFICADA' AND " +
            "r.fechaInicio <= :fechaActual AND r.fechaFin >= :fechaActual")
    List<Receta> findRecetasDisponibles(LocalDate fechaActual);
}