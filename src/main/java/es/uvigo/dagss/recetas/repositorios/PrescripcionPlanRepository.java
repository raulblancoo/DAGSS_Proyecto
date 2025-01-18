package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.PrescripcionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescripcionPlanRepository extends JpaRepository<PrescripcionPlan, Long> {
    List<PrescripcionPlan> findByPrescripcion(Prescripcion prescripcion);
}
