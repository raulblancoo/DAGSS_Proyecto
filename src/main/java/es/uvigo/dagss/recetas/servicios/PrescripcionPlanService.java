package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.PrescripcionPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrescripcionPlanService {

    List<PrescripcionPlan> buscarPlanesPorPrescripcion(Prescripcion prescripcion);

}
