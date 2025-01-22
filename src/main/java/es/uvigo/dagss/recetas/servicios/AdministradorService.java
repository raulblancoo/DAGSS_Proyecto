package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.CrearAdminRequest;
import es.uvigo.dagss.recetas.dtos.UpdateAdminRequest;
import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdministradorService {
    List<String> getHomeOptions();
    List<Administrador> buscarTodos();
    void crearAdministrador(CrearAdminRequest request);
    void actualizarAdministrador(UpdateAdminRequest request, Long adminId);
    void cambiarPassword(ChangePasswordRequest request, Long adminId);
    void eliminarAdministrador(Long id);
}
