package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.CrearFarmaciaRequest;
import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.dtos.UpdateFarmaciaProfileRequest;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FarmaciaService {
    /* Métodos para administrador */
    List<Farmacia> buscarFarmaciasConFiltros(String nombreEstablecimiento, String localidad);
    Farmacia crearFarmacia(CrearFarmaciaRequest request);
    Farmacia actualizarFarmacia(Long id, CrearFarmaciaRequest request);
    void eliminarFarmacia(Long id);

    /* Métodos para farmacia */
    List<String> getHomeOptions();
    Farmacia getPerfil(String numeroColegiado);
    void updatePerfil(UpdateFarmaciaProfileRequest request, String numeroColegiado);
    void changePassword(ChangePasswordRequest request, String numeroColegiado);
    void servirReceta(Long receta_id, Long farmaciaId);
    void puedeServirReceta(List<RecetaDto> recetas);

    /* Métodos generales */
    Farmacia findFarmaciaById(Long id);
}
