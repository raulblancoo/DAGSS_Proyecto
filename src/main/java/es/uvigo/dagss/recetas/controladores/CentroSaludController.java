package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.servicios.CentroSaludService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/centros-salud")
public class CentroSaludController {
    private final CentroSaludService centroSaludService;

    public CentroSaludController(CentroSaludService centroSaludService) {
        this.centroSaludService = centroSaludService;
    }

    @GetMapping
    public List<CentroSalud> listarCentros() {
        return centroSaludService.listarCentrosSalud();
    }

    // TODO: separar en métodos separados (nombre y localidad) preguntar
    @GetMapping("/buscar")
    public List<CentroSalud> buscarCentros(@RequestParam String criterio) {
        return centroSaludService.buscarCentrosPorCriterio(criterio);
    }

    @PostMapping("/crear")
    public CentroSalud crearCentro(@RequestBody CentroSalud centroSalud) {
        return centroSaludService.crearCentro(centroSalud);
    }

    @PutMapping("/editar/{id}")
    public CentroSalud editarCentro(@PathVariable Long id, @RequestBody CentroSalud datos) {
        return centroSaludService.editarCentro(id, datos);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarCentro(@PathVariable Long id) {
        centroSaludService.desactivarCentro(id);
    }
}