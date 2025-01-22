package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.CrearPacienteRequest;
import es.uvigo.dagss.recetas.dtos.UpdatePacienteProfileRequest;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PacienteService {

    /* Métodos administrador*/
    List<Paciente> buscarPacientesConFiltros(String nombre, String localidad, Long centroSaludId, Long medicoId);
    Paciente crearPaciente(CrearPacienteRequest request);
    Paciente actualizarPaciente(Long id, CrearPacienteRequest request);
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
