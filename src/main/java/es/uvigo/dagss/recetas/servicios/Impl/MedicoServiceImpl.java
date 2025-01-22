package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.CrearMedicoRequest;
import es.uvigo.dagss.recetas.dtos.DireccionDto;
import es.uvigo.dagss.recetas.dtos.UpdateMedicoProfileRequest;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.excepciones.PasswordProblemException;
import es.uvigo.dagss.recetas.excepciones.ResourceAlreadyExistsException;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.MedicoRepository;
import es.uvigo.dagss.recetas.servicios.CentroSaludService;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private  MedicoRepository medicoRepository;
    @Autowired
    private CentroSaludService centroSaludService;

    @Override
    public List<Medico> buscarMedicosConFiltros(String nombre, String localidad, Long centroSaludId) {
        if(nombre == null && localidad == null && centroSaludId == null) {
            return medicoRepository.findAll();
        } else if(nombre != null && localidad == null && centroSaludId == null) {
            return medicoRepository.findByNombreContaining(nombre);
        } else if(nombre == null && localidad != null && centroSaludId == null) {
            return medicoRepository.findByDireccion_LocalidadContaining(localidad);
        } else if(nombre == null && localidad == null && centroSaludId != null) {
            return medicoRepository.findByCentroSalud_Id(centroSaludId);
        } else {
            throw new WrongParameterException("Solo se puede proporcionar un parámetro de filtro a la vez.");
        }
    }

    @Transactional
    @Override
    public Medico crearMedico(CrearMedicoRequest request) {

        if(medicoRepository.existsByDniOrNumeroColegiado(request.getDni(), request.getNumeroColegiado())) {
            throw new ResourceAlreadyExistsException("Ya existe un médico con los datos proporcionados.");
        }

        Medico medico = getMedicoFromRequest(new Medico(), request);
        return medicoRepository.save(medico);
    }

    @Transactional
    @Override
    public Medico actualizarMedico(Long id, CrearMedicoRequest request) {
        Medico medico = medicoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe el medico con id: " + id));

        Medico modifiedMedico = getMedicoFromRequest(medico, request);
        return medicoRepository.save(modifiedMedico);
    }

    @Transactional
    @Override
    public void eliminarMedico(Long id) {
        Medico medicoExistente = medicoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe el medico con id: " + id));
        medicoExistente.desactivar();
        medicoRepository.save(medicoExistente);
    }

    private Medico getMedicoFromRequest(Medico medico, CrearMedicoRequest request) {
        // Ya tiene el manejo de la excepción en caso de que no exista
        CentroSalud centroSalud = centroSaludService.buscarCentroPorId(request.getCentroSaludId());

        medico.setLogin(request.getLogin());
        medico.setPassword(request.getNumeroColegiado());
        medico.setNombre(request.getNombre());
        medico.setApellidos(request.getApellidos());
        medico.setDni(request.getDni());
        medico.setNumeroColegiado(request.getNumeroColegiado());
        medico.setTelefono(request.getTelefono());
        medico.setEmail(request.getEmail());
        medico.setCentroSalud(centroSalud);
        if(request.getDireccion() != null) {
            medico.setDireccion(request.getDireccion());
        }

        return medico;
    }


    @Override
    public List<String> getHomeOptions() {
        return List.of("Mi agenda", "Mi perfil", "Desconectar");
    }

    @Override
    public Medico getPerfil(String numColegiado) {
        return getCurrentMedico(numColegiado);
    }

    @Override
    @Transactional
    public void updatePerfil(UpdateMedicoProfileRequest request, String numColegiado) {
        Medico medico = getCurrentMedico(numColegiado);

        medico.setNombre(request.getNombre());
        medico.setApellidos(request.getApellidos());
        medico.setTelefono(request.getTelefono());
        medico.setEmail(request.getEmail());

        Direccion direccion = medico.getDireccion();
        DireccionDto direccionDto = request.getDireccion();
        if (direccionDto != null) {
            direccion.setDomicilio(direccionDto.getDomicilio());
            direccion.setLocalidad(direccionDto.getLocalidad());
            direccion.setCodigoPostal(direccionDto.getCodigoPostal());
            direccion.setProvincia(direccionDto.getProvincia());
        }

        medicoRepository.save(medico);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request, String numColegiado) {
        if(!medicoRepository.existsByNumeroColegiado(numColegiado)) {
            throw new ResourceNotFoundException("No existe el medico con el número de colegiado: " + numColegiado);
        } else {
            Medico medico = getCurrentMedico(numColegiado);

            if(medico.getPassword().equals(request.getCurrentPassword())) {
                if(request.getNewPassword().equals(request.getConfirmNewPassword())){
                    medico.setPassword(request.getNewPassword());
                    medicoRepository.save(medico);
                }
                else {
                    throw new PasswordProblemException("La nueva contraseña y su confirmación no coinciden");
                }
            } else {
                throw new PasswordProblemException("La nueva contraseña y su confirmación no coinciden");
            }
        }
    }

    @Override
    public boolean existsMedicoById(Long medicoId) {
        return medicoRepository.existsById(medicoId);
    }

    @Override
    public Medico findMedicoById(Long medicoId) {
        return medicoRepository.findById(medicoId).
                orElseThrow(() -> new ResourceNotFoundException("No existe el medico con id:" + medicoId));
    }

    @Override
    public Medico findMedicoByNumColegiado(String numColegiado) {
        return medicoRepository.findByNumeroColegiado(numColegiado);
    }

    @Override
    public boolean estaAsignadoACentro(Long centroId, Long medicoId) {
        return findMedicoById(medicoId).getCentroSalud().getId().equals(centroId);
    }

    private Medico getCurrentMedico(String numColegiado) {
        return medicoRepository.findByNumeroColegiado(numColegiado);
    }
}
