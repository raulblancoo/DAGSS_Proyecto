package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicamentoService {
    List<Medicamento> listarMedicamentos();
    List<Medicamento> buscarMedicamentosPorNombreComercial(String nombreComercial);
    List<Medicamento> buscarMedicamentosPorPrincipioActivo(String principioActivo);
    List<Medicamento> buscarMedicamentosPorFabricante(String fabricante);
    List<Medicamento> buscarMedicamentosPorFamilia(String familia);

    List<Medicamento> buscarMedicamentoConFiltros(String nombreComercial, String principioActivo, String fabricante ,String familia);

    Medicamento crearMedicamento(Medicamento medicamento);
    Medicamento editarMedicamento(Long id, Medicamento medicamento);
    void eliminarMedicamento(Long id);

    boolean existsMedicamentoById(Long medicamentoId);
    Medicamento findMedicamentoById(Long medicamentoId);
}
