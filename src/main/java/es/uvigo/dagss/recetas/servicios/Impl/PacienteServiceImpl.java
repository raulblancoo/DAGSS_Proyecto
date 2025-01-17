package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.excepciones.ResourceAlreadyExistsException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.PacienteRepository;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente findByTarjetaSanitaria(String tarjetaSanitaria) {
        return pacienteRepository.findByTarjetaSanitaria(tarjetaSanitaria);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public List<Paciente> buscarPacientesPorLocalidad(String localidad) {
        return pacienteRepository.findByLocalidadLike(localidad);
    }

    @Override
    public List<Paciente> buscarPacientesPorNombre(String nombre) {
        return pacienteRepository.findByNombreLike(nombre);
    }

    @Override
    public List<Paciente> buscarPacientesPorCentroSalud(Long centroSaludId) {
        return pacienteRepository.findByCentroSalud_Id(centroSaludId);
    }

    @Override
    public List<Paciente> buscarPacientesPorMedico(Long medicoId) {
        return pacienteRepository.findByMedico_Id(medicoId);
    }

    @Override
    public List<Paciente> buscarPacientesConFiltros(String nombre, String localidad, Long centroSaludId, Long medicoId) {
        if(nombre == null && localidad == null && centroSaludId == null && medicoId == null) {
            return pacienteRepository.findAll();
        } else if(nombre != null && localidad == null && centroSaludId == null && medicoId == null)  {
            return pacienteRepository.findByNombreLike(nombre);
        } else if(nombre == null && localidad != null && centroSaludId == null && medicoId == null) {
            return pacienteRepository.findByLocalidadLike(localidad);
        } else if(nombre == null && localidad == null && centroSaludId != null && medicoId == null) {
            return pacienteRepository.findByCentroSalud_Id(centroSaludId);
        } else if(nombre == null && localidad == null && centroSaludId == null && medicoId != null) {
            return pacienteRepository.findByMedico_Id(medicoId);
        } else {
            throw new WrongParameterException("Solo se puede proporcionar un parámetro de filtro a la vez.");
        }
    }

    @Transactional
    @Override
    public Paciente crearPaciente(Paciente paciente) {
        if (pacienteRepository.existsByTarjetaSanitaria(paciente.getTarjetaSanitaria())) {
            throw new ResourceAlreadyExistsException(
                    "Ya existe un paciente con la tarjeta sanitaria proporcionada: " + paciente.getTarjetaSanitaria()
            );
        }

        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente editarPaciente(Long id, Paciente paciente) {
        return null;
    }

    @Override
    public void eliminarPaciente(Long id) {
        Paciente pacienteExistente = pacienteRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("aaaaa"));
        pacienteExistente.desactivar();
        pacienteRepository.save(pacienteExistente);
    }
}
