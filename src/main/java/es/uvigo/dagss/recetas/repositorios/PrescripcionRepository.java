package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrescripcionRepository extends JpaRepository<Prescripcion, Long> {
    List<Prescripcion> findByPaciente_IdAndEstado(Long pacienteId, Prescripcion.Estado estado);
}