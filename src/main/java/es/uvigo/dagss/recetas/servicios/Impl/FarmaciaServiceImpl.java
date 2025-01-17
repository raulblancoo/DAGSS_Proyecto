package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.excepciones.ResourceAlreadyExistsException;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.FarmaciaRepository;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Farmacia> buscarFarmaciasConFiltros(String nombreEstablecimiento, String localidad) {
        if(nombreEstablecimiento == null && localidad == null) {
            return farmaciaRepository.findAll();
        } else if (nombreEstablecimiento != null && localidad == null) {
            return farmaciaRepository.findByNombreEstablecimientoLike(nombreEstablecimiento);
        } else if(nombreEstablecimiento == null && localidad != null) {
            return farmaciaRepository.findByDireccion_LocalidadLike(localidad);
        } else {
            throw new WrongParameterException("Solo se puede proporcionar un parámetro de filtro a la vez.");
        }
    }

    @Transactional
    @Override
    public Farmacia crearFarmacia(Farmacia farmacia) {
        if(farmaciaRepository.existsByDniOrNumeroColegiado(farmacia.getDni(), farmacia.getNumeroColegiado())) {
            throw new ResourceAlreadyExistsException("Ya existe una farmacia con los datos proporcionados.");
        }

        return farmaciaRepository.save(farmacia);
    }

    @Override
    public Farmacia editarFarmacia(Long id, Farmacia farmacia) {
        return null;
    }

    @Transactional
    @Override
    public void eliminarFarmacia(Long id) {
        Farmacia farmaciaExistente = farmaciaRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe la farmacia con id:" + id));
        farmaciaExistente.desactivar();
        farmaciaRepository.save(farmaciaExistente);
    }
}
