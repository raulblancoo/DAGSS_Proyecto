package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Medicamento;
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
        return medicamentoRepository.findByNombreComercialContaining(nombreComercial);
    }

    @Override
    public List<Medicamento> buscarMedicamentosPorPrincipioActivo(String principioActivo) {
        return medicamentoRepository.findByPrincipioActivoContaining(principioActivo);
    }

    @Override
    public List<Medicamento> buscarMedicamentosPorFabricante(String fabricante) {
        return medicamentoRepository.findByFabricanteContaining(fabricante);
    }

    @Override
    public List<Medicamento> buscarMedicamentosPorFamilia(String familia) {
        return medicamentoRepository.findByFamiliaContaining(familia);
    }

    @Override
    public Medicamento crearMedicamento(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @Override
    public Medicamento editarMedicamento(Long id, Medicamento medicamento) {
        return null;
    }

    @Override
    public void eliminarMedicamento(Long id) {
        Medicamento medicamentoExistente = medicamentoRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("No existe un medicamento con el id: " + id));
        medicamentoExistente.setActivo(false);
        medicamentoRepository.save(medicamentoExistente);
    }
}
