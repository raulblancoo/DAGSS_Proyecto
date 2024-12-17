package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.servicios.MedicamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamento")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public List<Medicamento> listarMedicamentos() {
        return medicamentoService.listarMedicamentos();
    }

    @RequestMapping(params = "nombreComercial", method = RequestMethod.GET)
    public ResponseEntity<List<Medicamento>> buscarCentrosPorNombreComercial(@RequestParam(name = "nombreComercial", required = true) String nombreComercial) {
        List<Medicamento> listaMedicamentos = medicamentoService.buscarMedicamentosPorNombreComercial(nombreComercial);
        return ResponseEntity.ok(listaMedicamentos);
    }

    // TODO: comprobar si hace falta pasarle un responseEntity incorrecto.
    @RequestMapping(params = "principioActivo", method = RequestMethod.GET)
    public ResponseEntity<List<Medicamento>> buscarCentrosPorPrincipioActivo(@RequestParam(name = "principioActivo", required = true) String principioActivo) {
        List<Medicamento> listaMedicamentos = medicamentoService.buscarMedicamentosPorPrincipioActivo(principioActivo);
        return ResponseEntity.ok(listaMedicamentos);
    }

    @RequestMapping(params = "fabricante", method = RequestMethod.GET)
    public ResponseEntity<List<Medicamento>> buscarCentrosPorFabricante(@RequestParam(name = "fabricante", required = true) String fabricante) {
        List<Medicamento> listaMedicamentos = medicamentoService.buscarMedicamentosPorFabricante(fabricante);
        return ResponseEntity.ok(listaMedicamentos);
    }

    @RequestMapping(params = "familia", method = RequestMethod.GET)
    public ResponseEntity<List<Medicamento>> buscarCentrosPorFamilia(@RequestParam(name = "familia", required = true) String familia) {
        List<Medicamento> listaMedicamentos = medicamentoService.buscarMedicamentosPorFamilia(familia);
        return ResponseEntity.ok(listaMedicamentos);
    }

    @PostMapping("/crear")
    public Medicamento crearMedicamento(@RequestBody Medicamento centroSalud) {
        return medicamentoService.crearMedicamento(centroSalud);
    }

    @PutMapping("/editar/{id}")
    public Medicamento editarMedicamento(@PathVariable Long id, @RequestBody Medicamento datos) {
        return medicamentoService.editarMedicamento(id, datos);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarMedicamento(@PathVariable Long id) {
        medicamentoService.eliminarMedicamento(id);
    }
}
