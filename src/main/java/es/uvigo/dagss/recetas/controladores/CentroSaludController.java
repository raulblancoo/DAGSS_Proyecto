package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.servicios.CentroSaludService;
import org.springframework.http.ResponseEntity;
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

    // TODO: con los métodos de abajo sólo me deja filtrar con uno de los campos a la vez (y no por los dos)
    @GetMapping("/buscar")
    public List<CentroSalud> buscarCentros(@RequestParam String criterio) {
        return centroSaludService.buscarCentrosPorCriterio(criterio);
    }

    @RequestMapping(params = "nombre", method = RequestMethod.GET)
    public ResponseEntity<List<CentroSalud>> buscarCentrosPorNombre(@RequestParam(name = "nombre", required = true) String nombre) {
        List<CentroSalud> listaCentros = centroSaludService.buscarCentrosPorNombre(nombre);
        return ResponseEntity.ok(listaCentros);
    }

    // TODO: comprobar si hace falta pasarle un responseEntity incorrecto.
    @RequestMapping(params = "localidad", method = RequestMethod.GET)
    public ResponseEntity<List<CentroSalud>> buscarCentrosPorLocalidad(@RequestParam(name = "localidad", required = true) String localidad) {
        List<CentroSalud> listaCentros = centroSaludService.buscarCentrosPorLocalidad(localidad);
        return ResponseEntity.ok(listaCentros);
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