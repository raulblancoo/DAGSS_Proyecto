package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.servicios.RecetaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecetaController {
    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    // TODO:Comprobar si es un get o un post
    @GetMapping("/recetas")
    public List<RecetaDto> mostrarRecetasPlanificadasPorPaciente(@RequestParam("tarjetaSanitaria") String tarjetaSanitaria) {
        return recetaService.buscarRecetasActivasPorTarjetaSanitaria(tarjetaSanitaria);
    }

}
