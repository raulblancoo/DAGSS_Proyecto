package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.repositorios.AdministradorRepository;
import es.uvigo.dagss.recetas.repositorios.UsuarioRepository;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    private final UsuarioRepository usuarioRepository;
    private final AdministradorRepository administradorRepository;

    public AdministradorServiceImpl(UsuarioRepository usuarioRepository, AdministradorRepository administradorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.administradorRepository = administradorRepository;
    }

    @Override
    public List<Administrador> buscarTodos() {
        return administradorRepository.findByActivo(true);
    }

    @Override
    public List<Administrador> buscarPorCriterio(String criterio) {
        return administradorRepository.findByNombreContainingIgnoreCaseOrLoginContainingIgnoreCaseOrEmailContainingIgnoreCase(
                criterio, criterio, criterio);
    }

    @Transactional
    @Override
    public Administrador crearAdministrador(String login, String password, String nombre, String email) {
        if (administradorRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }
        Administrador administrador = new Administrador(login, password, nombre, email);
        return administradorRepository.save(administrador);
    }

    @Override
    public Administrador editarAdministrador(Long id, String nombre, String email, boolean activo) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado"));
        administrador.setNombre(nombre);
        administrador.setEmail(email);
        administrador.setActivo(activo);
        return administradorRepository.save(administrador);
    }

    @Override
    public void desactivarAdministrador(Long id) {
        Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado"));
        administrador.desactivar();
        administradorRepository.save(administrador);
    }

}
