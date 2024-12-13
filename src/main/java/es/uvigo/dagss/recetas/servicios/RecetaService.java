package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecetaService {
    List<RecetaDto>  buscarRecetasActivasPorTarjetaSanitaria(String tarjetaSanitaria);
    Receta anotarRecetaServida(Long recetaId, Long farmaciaId);
}
