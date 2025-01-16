package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
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

    public List<CentroSalud> listarCentrosSalud() {
        return centroSaludRepository.findAll();
    }

    public List<CentroSalud> buscarCentrosPorCriterio(String criterio) {
        return centroSaludRepository.buscarPorNombreOLocalidad(criterio);
    }

    @Override
    public List<CentroSalud> buscarCentrosPorLocalidad(String localidad) {
        return centroSaludRepository.findByLocalidadLike(localidad);
    }

    @Override
    public List<CentroSalud> buscarCentrosPorNombre(String nombre) {
        return centroSaludRepository.findByNombreLike(nombre);
    }

    @Transactional
    @Override
    public CentroSalud crearCentro(CentroSalud centroSalud) {
        return centroSaludRepository.save(centroSalud);
    }

    @Transactional
    @Override
    public CentroSalud editarCentro(Long id, CentroSalud datos) {
        CentroSalud centroExistente = centroSaludRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Centro de salud no encontrado"));
        centroExistente.setNombre(datos.getNombre());
        centroExistente.setDomicilio(datos.getDomicilio());
        centroExistente.setLocalidad(datos.getLocalidad());
        centroExistente.setCodigoPostal(datos.getCodigoPostal());
        centroExistente.setProvincia(datos.getProvincia());
        centroExistente.setTelefono(datos.getTelefono());
        centroExistente.setEmail(datos.getEmail());
        centroExistente.setActivo(datos.getActivo());
        return centroSaludRepository.save(centroExistente);
    }

    @Override
    public void desactivarCentro(Long id) {
        CentroSalud centroExistente = centroSaludRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Centro de salud no encontrado"));
        centroExistente.setActivo(false);
        centroSaludRepository.save(centroExistente);
    }
}
