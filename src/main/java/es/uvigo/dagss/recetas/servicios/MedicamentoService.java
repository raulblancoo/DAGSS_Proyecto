package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.CrearMedicamentoRequest;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicamentoService {
    List<Medicamento> buscarMedicamentoConFiltros(String nombreComercial, String principioActivo, String fabricante ,String familia);
    Medicamento crearMedicamento(CrearMedicamentoRequest request);
    Medicamento actualizarMedicamento(Long id, CrearMedicamentoRequest request);
    void eliminarMedicamento(Long id);

    boolean existsMedicamentoById(Long medicamentoId);
    Medicamento findMedicamentoById(Long medicamentoId);
}
