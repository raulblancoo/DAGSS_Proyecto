package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.servicios.RecetaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FarmaciaController {
    private final RecetaService recetaService;

    public FarmaciaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    // GESTIÓN DE RECETAS
    @GetMapping("/farmacia")
    public String homeFarmacia(){
        return "home";
    }

    @GetMapping("/farmacia/gestionRecetas")
    public String gestionRecetas(){
        return "formulario para introducir post y la lista";
    }

    @GetMapping("/farmacia/listadoRecetas")
    public List<RecetaDto> listarRecetasPorTarjeta(@RequestParam String tarjeta){
        return recetaService.buscarRecetasActivasPorTarjetaSanitaria(tarjeta);
    }


}
