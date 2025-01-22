package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.CrearCentroSaludRequest;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.CentroSaludRepository;
import es.uvigo.dagss.recetas.servicios.CentroSaludService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroSaludServiceImpl implements CentroSaludService {

    private final CentroSaludRepository centroSaludRepository;

    public CentroSaludServiceImpl(CentroSaludRepository centroSaludRepository) {
        this.centroSaludRepository = centroSaludRepository;
    }

    @Override
    public List<CentroSalud> buscarCentrosConFiltros(String nombre, String localidad) {
        if (nombre == null && localidad == null) {
            return centroSaludRepository.findAll();
        } else if (nombre != null && localidad == null) {
            return centroSaludRepository.findByNombreContaining(nombre);
        } else if (nombre == null && localidad != null) {
            return centroSaludRepository.findByDireccion_LocalidadContaining(localidad);
        } else {
            throw new WrongParameterException("Solo se puede proporcionar un parámetro de filtro a la vez.");
        }
    }

    @Transactional
    @Override
    public CentroSalud crearCentro(CrearCentroSaludRequest request) {
        CentroSalud centroSalud = new CentroSalud();
        centroSalud.setNombre(request.getNombre());
        centroSalud.setTelefono(request.getTelefono());
        centroSalud.setEmail(request.getEmail());
        if(request.getDireccion() != null){
            centroSalud.setDireccion(request.getDireccion());
        }

        return centroSaludRepository.save(centroSalud);
    }

    @Transactional
    @Override
    public CentroSalud actualizarCentro(Long id, CrearCentroSaludRequest request) {
        CentroSalud centro = centroSaludRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Centro salud con id " + id + " no encontrado"));

        centro.setNombre(request.getNombre());
        centro.setTelefono(request.getTelefono());
        centro.setEmail(request.getEmail());
        if(request.getDireccion() != null){
            centro.setDireccion(request.getDireccion());
        }

        return centroSaludRepository.save(centro);
    }

    @Transactional
    @Override
    public void eliminarCentro(Long id) {
        CentroSalud centroExistente = centroSaludRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el centro de salud con id:" + id));
        centroExistente.setActivo(false);
        centroSaludRepository.save(centroExistente);
    }

    @Override
    public CentroSalud buscarCentroPorId(Long id) {
        return centroSaludRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe el centro de salud con id:" + id));
    }
}
