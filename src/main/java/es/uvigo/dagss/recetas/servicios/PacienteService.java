package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PacienteService {
    Paciente findByTarjetaSanitaria(String tarjetaSanitaria);

    List<Paciente> listarPacientes();
    List<Paciente> buscarPacientesPorLocalidad(String localidad);
    List<Paciente> buscarPacientesPorNombre(String nombre);
    List<Paciente> buscarPacientesPorCentroSalud(Long centroSaludId);
    List<Paciente> buscarPacientesPorMedico(Long medicoId);
    List<Paciente> buscarPacientesConFiltros(String nombre, String localidad, Long centroSaludId, Long medicoId);

    Paciente crearPaciente(Paciente medico);
    Paciente editarPaciente(Long id, Paciente paciente);
    void eliminarPaciente(Long id);
}
