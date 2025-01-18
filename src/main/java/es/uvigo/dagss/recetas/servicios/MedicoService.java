package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.UpdateMedicoProfileRequest;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MedicoService {

    /* Métodos para administrador */
    List<Medico> listarMedicos();
    List<Medico> buscarMedicosPorLocalidad(String localidad);
    List<Medico> buscarMedicosPorNombre(String nombre);
    List<Medico> buscarMedicosPorCentroSalud(Long centroSaludId);
    List<Medico> buscarMedicosConFiltros(String nombre, String localidad, Long centroSaludId);

    Medico crearMedico(Medico medico);
    Medico editarMedico(Long id, Medico medicamento);
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
}
