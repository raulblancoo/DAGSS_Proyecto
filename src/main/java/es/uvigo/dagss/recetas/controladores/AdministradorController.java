package es.uvigo.dagss.recetas.controladores;


import es.uvigo.dagss.recetas.dtos.AdministradorDto;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }


    @GetMapping("/admin")
    public List<AdministradorDto> getAdministradores() {
        return administradorService.listarAdministradoresActivos();
    }

}
