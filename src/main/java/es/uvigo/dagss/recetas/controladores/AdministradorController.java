package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {
    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public List<Administrador> listarAdministradores() {
        return administradorService.buscarTodos();
    }

    @GetMapping("/buscar")
    public List<Administrador> buscarAdministradores(@RequestParam String criterio) {
        return administradorService.buscarPorCriterio(criterio);
    }

    @PostMapping("/crear")
    public Administrador crearAdministrador(@RequestBody Administrador administrador) {
        return administradorService.crearAdministrador(
                administrador.getLogin(), administrador.getPassword(),
                administrador.getNombre(), administrador.getEmail());
    }

    @PutMapping("/editar/{id}")
    public Administrador editarAdministrador(@PathVariable Long id, @RequestBody Administrador datos) {
        return administradorService.editarAdministrador(
                id, datos.getNombre(), datos.getEmail(), datos.getActivo());
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarAdministrador(@PathVariable Long id) {
        administradorService.desactivarAdministrador(id);
    }
}
