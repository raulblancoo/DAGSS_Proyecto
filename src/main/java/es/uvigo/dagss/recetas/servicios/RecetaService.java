package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecetaService {
    List<Receta> buscarPlanesPorPrescripcionId(Long prescripcionId);
    List<Receta> buscarRecetasPorTarjetaSanitaria(String tarjetaSanitaria);
    List<Receta> buscarRecetasPendientes(String numSegSocial);
    void anularRecetaAsociada(Long prescripcionId);
}
