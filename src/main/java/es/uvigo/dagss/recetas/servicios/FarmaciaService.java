package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.UpdateFarmaciaProfileRequest;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FarmaciaService {
    /* Métodos para administrador */
    List<Farmacia> buscarFarmaciasConFiltros(String nombreEstablecimiento, String localidad);
    Farmacia crearFarmacia(Farmacia farmacia);
    Farmacia editarFarmacia(Long id, Farmacia farmacia);
    void eliminarFarmacia(Long id);

    /* Métodos para farmacia */
    List<String> getHomeOptions();
    Farmacia getPerfil(String numeroColegiado);
    void updatePerfil(UpdateFarmaciaProfileRequest request, String numeroColegiado);
    void changePassword(ChangePasswordRequest request, String numeroColegiado);
    void servirReceta(Long receta_id, Long farmaciaId);

    /* Métodos generales */
    Farmacia findFarmaciaById(Long id);
}
