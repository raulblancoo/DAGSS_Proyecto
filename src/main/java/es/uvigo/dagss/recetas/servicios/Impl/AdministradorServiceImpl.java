package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.CrearAdminRequest;
import es.uvigo.dagss.recetas.dtos.UpdateAdminRequest;
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.excepciones.PasswordProblemException;
import es.uvigo.dagss.recetas.excepciones.ResourceAlreadyExistsException;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.repositorios.AdministradorRepository;
import es.uvigo.dagss.recetas.repositorios.UsuarioRepository;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public List<String> getHomeOptions() {
        return List.of(
                "Administradores",
                "Centros de salud",
                "Médicos",
                "Pacientes",
                "Farmacias",
                "Citas",
                "Medicamentos",
                "Desconectar"
        );
    }

    @Override
    public List<Administrador> buscarTodos() {
        return administradorRepository.findByActivo(true);
    }

    @Transactional
    @Override
    public void crearAdministrador(CrearAdminRequest request) {
        if(administradorRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("El administrador con email " + request.getEmail() + "ya existe");
        }

        Administrador administrador = new Administrador(request.getLogin(), request.getPassword(), request.getNombre(), request.getEmail());
        administradorRepository.save(administrador);
    }

    @Transactional
    @Override
    public void actualizarAdministrador(UpdateAdminRequest request, Long adminId) {
        Administrador admin = administradorRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el administrador con id: " + adminId));

        if(!administradorRepository.existsByEmail(request.getEmail())) {
            admin.setNombre(request.getNombre());
            admin.setEmail(request.getEmail());
            administradorRepository.save(admin);
        } else {
            throw new ResourceAlreadyExistsException("Ya existe un administrador con email: " + request.getEmail());
        }
    }

    @Override
    public void cambiarPassword(ChangePasswordRequest request, Long adminId) {
        Administrador admin = administradorRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el administrador con id: " + adminId));

        if(admin.getPassword().equals(request.getCurrentPassword())) {
            if(request.getNewPassword().equals(request.getConfirmNewPassword())){
                admin.setPassword(request.getNewPassword());
                administradorRepository.save(admin);
            } else {
                throw new PasswordProblemException("La nueva contraseña y su confirmación no coinciden");
            }
        } else {
            throw new PasswordProblemException("La contraseña actual para el administrador no es correcta");
        }

    }

    @Transactional
    @Override
    public void eliminarAdministrador(Long id) {
        Administrador admin = administradorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el administrador: " + id));
        admin.desactivar();
        administradorRepository.save(admin);
    }
}
