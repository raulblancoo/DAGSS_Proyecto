package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicoService {

    List<Medico> listarMedicos();
    List<Medico> buscarMedicosPorLocalidad(String localidad);
    List<Medico> buscarMedicosPorNombre(String nombre);
    List<Medico> buscarMedicosPorCentroSalud(Long centroSaludId);
    List<Medico> buscarMedicosConFiltros(String nombre, String localidad, Long centroSaludId);

    Medico crearMedico(Medico medico);
    Medico editarMedico(Long id, Medico medicamento);
    void eliminarMedico(Long id);
}
