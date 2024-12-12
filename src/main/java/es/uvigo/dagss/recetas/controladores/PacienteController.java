package es.uvigo.dagss.recetas.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("paciente")
public class PacienteController {

    @GetMapping("/home")
    public String showHome(){
        return "Menú opciones: " +
                "\nMis citas" +
                "\nNueva cita" +
                "\nMis recetas" +
                "\nMi perfil" +
                "\nDesconectar";
    }

}
