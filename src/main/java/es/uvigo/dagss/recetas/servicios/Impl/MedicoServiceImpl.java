package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.DireccionDto;
import es.uvigo.dagss.recetas.dtos.UpdateMedicoProfileRequest;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.excepciones.ResourceAlreadyExistsException;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.CitaRepository;
import es.uvigo.dagss.recetas.repositorios.MedicoRepository;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private  MedicoRepository medicoRepository;

    @Override
    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    @Override
    public List<Medico> buscarMedicosPorLocalidad(String localidad) {
        return medicoRepository.findByCentroSalud_Direccion_LocalidadLike(localidad);
    }

    @Override
    public List<Medico> buscarMedicosPorNombre(String nombre) {
        return medicoRepository.findByNombreLike(nombre);
    }

    @Override
    public List<Medico> buscarMedicosPorCentroSalud(Long centroSaludId) {
        return medicoRepository.findByCentroSalud_Id(centroSaludId);
    }

    @Override
    public List<Medico> buscarMedicosConFiltros(String nombre, String localidad, Long centroSaludId) {
        if(nombre == null && localidad == null && centroSaludId == null) {
            return medicoRepository.findAll();
        } else if(nombre != null && localidad == null && centroSaludId == null) {
            return medicoRepository.findByNombreLike(nombre);
        } else if(nombre == null && localidad != null && centroSaludId == null) {
            return medicoRepository.findByCentroSalud_Direccion_LocalidadLike(localidad);
        } else if(nombre == null && localidad == null && centroSaludId != null) {
            return medicoRepository.findByCentroSalud_Id(centroSaludId);
        } else {
            throw new WrongParameterException("Solo se puede proporcionar un parámetro de filtro a la vez.");
        }
    }

    @Transactional
    @Override
    public Medico crearMedico(Medico medico) {

        if(medicoRepository.existsByDniOrNumeroColegiado(medico.getDni(), medico.getNumeroColegiado())) {
            throw new ResourceAlreadyExistsException("Ya existe un médico con los datos proporcionados.");
        }

        // TODO: validacion de que centro de salud es válido???'

        Medico newMedico = new Medico(
                medico.getLogin(), medico.getNumeroColegiado(),
                medico.getNombre(), medico.getApellidos(), medico.getDni(),
                medico.getNumeroColegiado(), medico.getDireccion(), medico.getTelefono(), medico.getEmail(), medico.getCentroSalud()
        );

        return medicoRepository.save(newMedico);
    }

    // TODO: corregir, no funciona
    @Transactional
    @Override
    public Medico editarMedico(Long id, Medico datosMedico) {
        Optional<Medico> medico = medicoRepository.findById(id);

        if(medico.isPresent()) {
            Medico medicoEdit = medico.get();
            medicoEdit.setNombre(datosMedico.getNombre());
            medicoEdit.setApellidos(datosMedico.getApellidos());
            medicoEdit.setDni(datosMedico.getDni());
            medicoEdit.setNumeroColegiado(datosMedico.getNumeroColegiado());
            medicoEdit.setTelefono(datosMedico.getTelefono());
            medicoEdit.setEmail(datosMedico.getEmail());
            medicoEdit.setCentroSalud(datosMedico.getCentroSalud());
//            medicoEdit.setPacientes(datosMedico.getPacientes());
//            medicoEdit.setRecetas(datosMedico.getRecetas());
            medicoEdit.setCitas(datosMedico.getCitas());
            medicoEdit.setActivo(datosMedico.getActivo());
            medicoEdit.setLogin(datosMedico.getLogin());
            medicoEdit.setPassword(datosMedico.getPassword());

            return medicoRepository.save(medicoEdit);
        } else {
            throw new IllegalArgumentException("El médico no existe");
        }
    }

    @Transactional
    @Override
    public void eliminarMedico(Long id) {
        Medico medicoExistente = medicoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe el medico con id: " + id));
        medicoExistente.desactivar();
        medicoRepository.save(medicoExistente);
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
        Medico medico = getCurrentMedico(numColegiado);

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

    // TODO: validaciones de existencia de médico
    private Medico getCurrentMedico(String numColegiado) {
        return medicoRepository.findByNumeroColegiado(numColegiado);

//        String nombreUsuario = SecurityUtils.getCurrentUsername();
//        User user = userRepository.findByNombreUsuario(nombreUsuario)
//                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
//        return medicoRepository.findByUser(user)
//                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado."));
    }
}
