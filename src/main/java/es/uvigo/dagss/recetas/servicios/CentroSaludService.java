package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.CrearCentroSaludRequest;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CentroSaludService {
    /* MÉTODOS ADMINISTRADOR */
    List<CentroSalud> buscarCentrosConFiltros(String nombre, String localidad);
    CentroSalud crearCentro(CrearCentroSaludRequest request);
    CentroSalud actualizarCentro(Long id, CrearCentroSaludRequest request);
    void eliminarCentro(Long id);

    /* OTROS MÉTODOS*/
    CentroSalud buscarCentroPorId(Long id);
    boolean existeCentroPorId(Long id);

}
