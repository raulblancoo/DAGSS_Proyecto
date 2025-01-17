package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FarmaciaService {
    Farmacia findFarmaciaById(Long id);

    List<Farmacia> buscarFarmaciasConFiltros(String nombreEstablecimiento, String localidad);
    Farmacia crearFarmacia(Farmacia farmacia);
    Farmacia editarFarmacia(Long id, Farmacia farmacia);
    void eliminarFarmacia(Long id);


}
