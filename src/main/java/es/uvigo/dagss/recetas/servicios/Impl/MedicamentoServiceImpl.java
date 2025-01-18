package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.MedicamentoRepository;
import es.uvigo.dagss.recetas.servicios.MedicamentoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
    public List<Medicamento> listarMedicamentos() {
        // TODO: comprobar si tienen que ser todos o solo los activos
        return medicamentoRepository.findAll();
    }

    @Override
    public List<Medicamento> buscarMedicamentosPorNombreComercial(String nombreComercial) {
        return medicamentoRepository.findByNombreComercialLike(nombreComercial);
    }

    @Override
    public List<Medicamento> buscarMedicamentosPorPrincipioActivo(String principioActivo) {
        return medicamentoRepository.findByPrincipioActivoLike(principioActivo);
    }

    @Override
    public List<Medicamento> buscarMedicamentosPorFabricante(String fabricante) {
        return medicamentoRepository.findByFabricanteLike(fabricante);
    }

    @Override
    public List<Medicamento> buscarMedicamentosPorFamilia(String familia) {
        return medicamentoRepository.findByFamiliaLike(familia);
    }

    @Override
    public List<Medicamento> buscarMedicamentoConFiltros(String nombreComercial, String principioActivo, String fabricante, String familia) {

        if(nombreComercial == null && principioActivo == null && fabricante == null && familia == null) {
            return medicamentoRepository.findAll();
        } else if(nombreComercial != null && principioActivo == null && fabricante == null && familia == null) {
            return medicamentoRepository.findByNombreComercialLike(nombreComercial);
        } else if(nombreComercial == null && principioActivo != null && fabricante == null && familia == null) {
            return medicamentoRepository.findByPrincipioActivoLike(principioActivo);
        } else if(nombreComercial == null && principioActivo == null && fabricante != null && familia == null) {
            return medicamentoRepository.findByFabricanteLike(fabricante);
        } else if(nombreComercial == null && principioActivo == null && fabricante == null && familia != null) {
            return medicamentoRepository.findByFamiliaLike(familia);
        } else {
            throw new WrongParameterException("Solo se puede proporcionar un parámetro de filtro a la vez.");
        }

    }

    @Override
    public Medicamento crearMedicamento(Medicamento medicamento) {

        // TODO: no sé si hacer la validación de que ya existe por id

        return medicamentoRepository.save(medicamento);
    }

    @Override
    public Medicamento editarMedicamento(Long id, Medicamento medicamento) {
        //TODO
        return null;
    }

    @Override
    public void eliminarMedicamento(Long id) {
        Medicamento medicamentoExistente = medicamentoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe medicamento con el id: " + id));
        medicamentoExistente.setActivo(false);
        medicamentoRepository.save(medicamentoExistente);
    }

    @Override
    public boolean existsMedicamentoById(Long medicamentoId) {
        return medicamentoRepository.existsById(medicamentoId);
    }

    @Override
    public Medicamento findMedicamentoById(Long medicamentoId) {
        return medicamentoRepository.findById(medicamentoId).
                orElseThrow(() -> new ResourceNotFoundException("No existe el medicamento con id:" + medicamentoId));
    }

}
