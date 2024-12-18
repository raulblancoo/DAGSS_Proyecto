package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicoController")
public class MedicoController {
    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public List<Medico> listarMedicos() {
        return medicoService.listarMedicos();
    }

    @RequestMapping(params="nombre", method = RequestMethod.GET)
    public ResponseEntity<List<Medico>> buscarMedicosPorNombre(@RequestParam(name = "nombre", required = true) String nombreMedico) {
        List<Medico> listaMedicos = medicoService.buscarMedicosPorNombre(nombreMedico);
        return ResponseEntity.ok(listaMedicos);
    }

    @PostMapping("/crear")
    public Medico crearMedico(@RequestBody Medico medico) {
        return medicoService.crearMedico(medico);
    }


}
