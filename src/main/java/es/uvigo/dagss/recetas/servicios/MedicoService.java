package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.CrearMedicoRequest;
import es.uvigo.dagss.recetas.dtos.UpdateMedicoProfileRequest;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicoService {

    /* Métodos para administrador */
    List<Medico> buscarMedicosConFiltros(String nombre, String localidad, Long centroSaludId);
    Medico crearMedico(CrearMedicoRequest request);
    Medico actualizarMedico(Long id, CrearMedicoRequest request);
    void eliminarMedico(Long id);

    /* Métodos para médico */
    List<String> getHomeOptions();
    Medico getPerfil(String numColegiado);
    void updatePerfil(UpdateMedicoProfileRequest request, String numColegiado);
    void changePassword(ChangePasswordRequest request, String numColegiado);

    /* Métodos generales */
    boolean existsMedicoById(Long medicoId);
    Medico findMedicoById(Long medicoId);
    Medico findMedicoByNumColegiado(String numColegiado);
    boolean estaAsignadoACentro(Long centroId, Long medicoId);
}
