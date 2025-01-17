package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.CentroSaludRepository;
import es.uvigo.dagss.recetas.servicios.CentroSaludService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroSaludServiceImpl implements CentroSaludService {

    private final CentroSaludRepository centroSaludRepository;

    public CentroSaludServiceImpl(CentroSaludRepository centroSaludRepository) {
        this.centroSaludRepository = centroSaludRepository;
    }

    @Override
    public List<CentroSalud> buscarCentrosPorLocalidad(String localidad) {
        return centroSaludRepository.findByDireccion_LocalidadLike(localidad);
    }

    @Override
    public List<CentroSalud> buscarCentrosPorNombre(String nombre) {
        return centroSaludRepository.findByNombreLike(nombre);
    }

    @Override
    public List<CentroSalud> buscarCentrosConFiltros(String nombre, String localidad) {
        if (nombre == null && localidad == null) {
            return centroSaludRepository.findAll();
        } else if (nombre != null && localidad == null) {
            return centroSaludRepository.findByNombreLike(nombre);
        } else if (nombre == null && localidad != null) {
            return centroSaludRepository.findByDireccion_LocalidadLike(localidad);
        } else {
            throw new WrongParameterException("Solo se puede proporcionar un parámetro de filtro a la vez.");
        }
    }

    @Transactional
    @Override
    public CentroSalud crearCentro(CentroSalud centroSalud) {
        return centroSaludRepository.save(centroSalud);
    }

    // TODO: comprobar como hacer edit con dirección
    @Transactional
    @Override
    public CentroSalud editarCentro(Long id, CentroSalud datos) {
//        CentroSalud centroExistente = centroSaludRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Centro de salud no encontrado"));
//        centroExistente.setNombre(datos.getNombre());
//        centroExistente.setDomicilio(datos.getDomicilio());
//        centroExistente.setLocalidad(datos.getLocalidad());
//        centroExistente.setCodigoPostal(datos.getCodigoPostal());
//        centroExistente.setProvincia(datos.getProvincia());
//        centroExistente.setTelefono(datos.getTelefono());
//        centroExistente.setEmail(datos.getEmail());
//        centroExistente.setActivo(datos.getActivo());
//        return centroSaludRepository.save(centroExistente);

        return null;
    }

    @Transactional
    @Override
    public void desactivarCentro(Long id) {
        CentroSalud centroExistente = centroSaludRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el centro de salud con id:" + id));
        centroExistente.setActivo(false);
        centroSaludRepository.save(centroExistente);
    }
}
