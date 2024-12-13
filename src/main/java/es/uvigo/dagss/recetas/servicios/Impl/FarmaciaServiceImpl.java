package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.repositorios.FarmaciaRepository;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FarmaciaServiceImpl implements FarmaciaService {
    private final FarmaciaRepository farmaciaRepository;

    public FarmaciaServiceImpl(FarmaciaRepository farmaciaRepository) {
        this.farmaciaRepository = farmaciaRepository;
    }

    @Override
    public Farmacia findFarmaciaById(Long id) {
        // TODO: comprobar el uso de Optional
        Optional<Farmacia> farmacia = farmaciaRepository.findById(id);
        return farmacia.orElseGet(Farmacia::new);
    }
}
