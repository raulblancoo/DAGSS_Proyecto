package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.PrescripcionPlan;
import es.uvigo.dagss.recetas.repositorios.PrescripcionPlanRepository;
import es.uvigo.dagss.recetas.servicios.PrescripcionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescripcionPlanServiceImpl implements PrescripcionPlanService {

    @Autowired
    private PrescripcionPlanRepository prescripcionPlanRepository;

    @Override
    public List<PrescripcionPlan> buscarPlanesPorPrescripcion(Prescripcion prescripcion) {
        return prescripcionPlanRepository.findByPrescripcion(prescripcion);
    }
}
