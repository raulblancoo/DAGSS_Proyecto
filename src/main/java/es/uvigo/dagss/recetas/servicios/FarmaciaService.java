package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import org.springframework.stereotype.Service;

@Service
public interface FarmaciaService {
    Farmacia findFarmaciaById(Long id);
}
