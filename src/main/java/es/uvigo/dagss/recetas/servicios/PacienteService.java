package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.UpdatePacienteProfileRequest;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PacienteService {

    /* Métodos administrador*/
    List<Paciente> listarPacientes();
    List<Paciente> buscarPacientesPorLocalidad(String localidad);
    List<Paciente> buscarPacientesPorNombre(String nombre);
    List<Paciente> buscarPacientesPorCentroSalud(Long centroSaludId);
    List<Paciente> buscarPacientesPorMedico(Long medicoId);
    List<Paciente> buscarPacientesConFiltros(String nombre, String localidad, Long centroSaludId, Long medicoId);

    Paciente crearPaciente(Paciente medico);
    Paciente editarPaciente(Long id, Paciente paciente);
    void eliminarPaciente(Long id);

    /* Métodos de paciente */
    List<String> getHomeOptions(String numSegSocial);
    Paciente getPerfil(String numSegSocial);
    void updatePerfil(UpdatePacienteProfileRequest request, String numSegSocial);
    void changePassword(ChangePasswordRequest request, String numSegSocial);

    /* Métodos generales */
    boolean existsPacienteById(Long id);
    Paciente findPacienteById(Long id);
    Paciente findByTarjetaSanitaria(String tarjetaSanitaria);
    Paciente findPacienteByNumSeguridadSocial(String numSegSocial);
}
