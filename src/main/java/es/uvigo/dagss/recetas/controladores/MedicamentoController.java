package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.mappers.MedicamentoMapper;
import es.uvigo.dagss.recetas.servicios.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;
    @Autowired
    private MedicamentoMapper medicamentoMapper;

    /**
     * Endpoint: GET /api/medicamentos
     * Descripción: Permite buscar medicamentos por nombre comercial, principio activo, fabricante o familia.
     */
    @GetMapping
    public ResponseEntity<?> buscarMedicamentos(
            @RequestParam(name = "nombreComercial", required = false) String nombreComercial,
            @RequestParam(name = "principioActivo", required = false) String principioActivo,
            @RequestParam(name = "fabricante", required = false) String fabricante,
            @RequestParam(name = "familia", required = false) String familia){

        List<Medicamento> listaMedicamentos = medicamentoService.buscarMedicamentoConFiltros(nombreComercial, principioActivo, fabricante, familia);

        if (listaMedicamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(medicamentoMapper.toListDto(listaMedicamentos));
    }
}
