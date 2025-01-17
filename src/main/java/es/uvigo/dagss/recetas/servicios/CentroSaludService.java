package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CentroSaludService {
    List<CentroSalud> buscarCentrosPorLocalidad(String localidad);
    List<CentroSalud> buscarCentrosPorNombre(String nombre);
    List<CentroSalud> buscarCentrosConFiltros(String nombre, String localidad);
    CentroSalud crearCentro(CentroSalud centroSalud);
    CentroSalud editarCentro(Long id, CentroSalud datos);
    void desactivarCentro(Long id);

}
