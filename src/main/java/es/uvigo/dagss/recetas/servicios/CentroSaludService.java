package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CentroSaludService {
    List<CentroSalud> listarCentrosSalud();
    List<CentroSalud> buscarCentrosPorCriterio(String criterio);
    List<CentroSalud> buscarCentrosPorLocalidad(String localidad);
    List<CentroSalud> buscarCentrosPorNombre(String nombre);
    CentroSalud crearCentro(CentroSalud centroSalud);
    CentroSalud editarCentro(Long id, CentroSalud datos);
    void desactivarCentro(Long id);

}
