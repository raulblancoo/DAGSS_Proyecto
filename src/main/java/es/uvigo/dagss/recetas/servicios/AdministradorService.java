package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.AdministradorDto;
import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdministradorService {
    List<Administrador> buscarTodos();
    List<Administrador> buscarPorCriterio(String criterio);
    Administrador crearAdministrador(String login, String password, String nombre, String email);
    Administrador editarAdministrador(Long id, String nombre, String email, boolean activo);
    void desactivarAdministrador(Long id);
}
