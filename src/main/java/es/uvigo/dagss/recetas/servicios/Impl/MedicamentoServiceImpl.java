package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.CrearMedicamentoRequest;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.MedicamentoRepository;
import es.uvigo.dagss.recetas.servicios.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

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
    public Medicamento crearMedicamento(CrearMedicamentoRequest request) {
        Medicamento medicamento = getMedicamentoFromRequest(new Medicamento(), request);
        return medicamentoRepository.save(medicamento);
    }

    @Override
    public Medicamento actualizarMedicamento(Long id, CrearMedicamentoRequest request) {
        Medicamento medicamento = medicamentoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Medicamento con id " + id + " no encontrado"));

        Medicamento modifiedMedicamento = getMedicamentoFromRequest(medicamento, request);
        return medicamentoRepository.save(modifiedMedicamento);
    }

    @Override
    public void eliminarMedicamento(Long id) {
        Medicamento medicamentoExistente = medicamentoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe medicamento con el id: " + id));
        medicamentoExistente.setActivo(false);
        medicamentoRepository.save(medicamentoExistente);
    }

    private Medicamento getMedicamentoFromRequest(Medicamento medicamento, CrearMedicamentoRequest request) {
        medicamento.setNombreComercial(medicamento.getNombreComercial());
        medicamento.setPrincipioActivo(medicamento.getPrincipioActivo());
        medicamento.setFabricante(medicamento.getFabricante());
        medicamento.setFamilia(medicamento.getFamilia());
        medicamento.setNumeroDosis(medicamento.getNumeroDosis());
        return medicamento;
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
