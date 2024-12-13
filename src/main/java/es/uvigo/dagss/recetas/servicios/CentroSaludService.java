package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CentroSaludService {
    public List<CentroSalud> listarCentrosSalud();
    public List<CentroSalud> buscarCentrosPorCriterio(String criterio);
    public CentroSalud crearCentro(CentroSalud centroSalud);
    public CentroSalud editarCentro(Long id, CentroSalud datos);
    public void desactivarCentro(Long id);
}
