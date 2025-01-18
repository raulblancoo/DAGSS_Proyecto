package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.DireccionDto;
import es.uvigo.dagss.recetas.dtos.UpdatePacienteProfileRequest;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.excepciones.ResourceAlreadyExistsException;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.CitaRepository;
import es.uvigo.dagss.recetas.repositorios.PacienteRepository;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public List<Paciente> buscarPacientesPorLocalidad(String localidad) {
        return pacienteRepository.findByDireccion_LocalidadLike(localidad);
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
            return pacienteRepository.findByDireccion_LocalidadLike(localidad);
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

    @Transactional
    @Override
    public void eliminarPaciente(Long id) {
        Paciente pacienteExistente = pacienteRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe el paciente con id:" + id));
        pacienteExistente.desactivar();
        pacienteRepository.save(pacienteExistente);
    }


    @Override
    public List<String> getHomeOptions(String numSegSocial) {
        return List.of("Mis citas", "Nueva cita", "Mis recetas", "Mi perfil", "Desconectar");
    }

    @Override
    public Paciente getPerfil(String numSegSocial) {
        return getCurrentPaciente(numSegSocial);
    }

    @Override
    public void updatePerfil(UpdatePacienteProfileRequest request, String numSegSocial) {
        Paciente paciente = getCurrentPaciente(numSegSocial);

        paciente.setNombre(request.getNombre());
        paciente.setApellidos(request.getApellidos());
        paciente.setTelefono(request.getTelefono());
        paciente.setEmail(request.getEmail());

        Direccion direccion = paciente.getDireccion();
        DireccionDto direccionDto = request.getDireccion();
        if (direccionDto != null) {
            direccion.setDomicilio(direccionDto.getDomicilio());
            direccion.setLocalidad(direccionDto.getLocalidad());
            direccion.setCodigoPostal(direccionDto.getCodigoPostal());
            direccion.setProvincia(direccionDto.getProvincia());
        }

        pacienteRepository.save(paciente);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String numSegSocial) {
        Paciente paciente = getCurrentPaciente(numSegSocial);

        //TODO: método cambiar contraseña
//        User user = medico.getUser();
//
//        // Verificar contraseña actual
//        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getContraseña())) {
//            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
//        }
//
//        // Verificar que la nueva contraseña y la confirmación coinciden
//        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
//            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden.");
//        }
//
//        // Encriptar y actualizar la contraseña
//        user.setContraseña(passwordEncoder.encode(request.getNewPassword()));
//        userRepository.save(user);
    }

    @Override
    public boolean existsPacienteById(Long pacienteId) {
        return pacienteRepository.existsById(pacienteId);
    }

    @Override
    public Paciente findPacienteById(Long id) {
        return pacienteRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe el paciente con id:" + id));
    }

    @Override
    public Paciente findByTarjetaSanitaria(String tarjetaSanitaria) {
        return pacienteRepository.findByTarjetaSanitaria(tarjetaSanitaria);
    }

    @Override
    public Paciente findPacienteByNumSeguridadSocial(String numSegSocial) {
        return pacienteRepository.findByNumeroSeguridadSocial(numSegSocial);
    }


    private Paciente getCurrentPaciente(String numSegsocial) {
        return pacienteRepository.findByNumeroSeguridadSocial(numSegsocial);
    }
}
