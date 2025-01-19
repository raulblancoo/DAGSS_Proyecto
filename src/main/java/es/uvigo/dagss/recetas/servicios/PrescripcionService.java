package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.CrearPrescripcionRequest;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrescripcionService {
    void crearPrescripcion(CrearPrescripcionRequest request);
    List<Prescripcion> findByPacienteAndEstado(Long pacienteId, Prescripcion.Estado estado);
    void actualizarEstadoPrescripcion(Long prescripcionId, Prescripcion.Estado estado);
}
