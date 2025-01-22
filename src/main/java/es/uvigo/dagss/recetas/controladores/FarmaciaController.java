package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.dtos.*;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.mappers.FarmaciaMapper;
import es.uvigo.dagss.recetas.mappers.RecetaMapper;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import es.uvigo.dagss.recetas.servicios.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/farmacia/{numColegiado}")
public class FarmaciaController {

    @Autowired
    private FarmaciaService farmaciaService;
    @Autowired
    private FarmaciaMapper farmaciaMapper;
    @Autowired
    private RecetaService recetaService;
    @Autowired
    private RecetaMapper recetaMapper;


    /**
     * Endpoint: GET /api/farmacia/{numColegiado}/home
     * Descripción: Devuelve las opciones disponibles en el menú para la farmacia.
     */
    @GetMapping("/home")
    public ResponseEntity<List<String>> getFarmaciaHome() {
        return ResponseEntity.ok(farmaciaService.getHomeOptions());
    }

    /**
     * Endpoint: GET /api/farmacia/{numColegiado}/perfil
     * Descripción: Devuelve la información personal y profesional de la farmacia.
     */
    @GetMapping("/perfil")
    public ResponseEntity<FarmaciaProfile> getPerfilFarmacia(@PathVariable("numColegiado") String numColegiado) {
        return ResponseEntity.ok(farmaciaMapper.toProfileDto(farmaciaService.getPerfil(numColegiado)));
    }

    /**
     * Endpoint: PUT /api/farmacia/{numColegiado}/perfil
     * Descripción: Permite modificar datos personales de la farmacia.
     */
    @PutMapping("/perfil")
    public ResponseEntity<String> updatePerfilFarmacia(
            @PathVariable("numColegiado") String numColegiado,
            @Validated @RequestBody UpdateFarmaciaProfileRequest request) {
        farmaciaService.updatePerfil(request, numColegiado);
        return ResponseEntity.ok("Perfil actualizado exitosamente.");
    }

    /**
     * Endpoint: PUT /api/farmacia/{numColegiado}/perfil/password
     * Descripción: Permite a la farmacia actualizar su contraseña de acceso.
     */
    @PutMapping("/perfil/password")
    public ResponseEntity<?> changePasswordFarmacia(
            @PathVariable("numColegiado") String numColegiado,
            @Validated @RequestBody ChangePasswordRequest request) {
        farmaciaService.changePassword(request, numColegiado);
        return ResponseEntity.ok("Contraseña cambiada exitosamente.");
    }

    /**
     * Endpoint: GET /api/farmacia/{numColegiado}/recetas
     * Descripción: Busca recetas de un paciente por número de tarjeta sanitaria.
     */
    @GetMapping("/recetas")
    public ResponseEntity<List<RecetaDto>> getRecetasPorTarjetaSanitaria(@RequestParam("tarjetaSanitaria") String tarjetaSanitaria) {

        List<Receta> recetas = recetaService.buscarRecetasPorTarjetaSanitaria(tarjetaSanitaria);

        if(recetas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<RecetaDto> recetasDto = recetaMapper.toListDto(recetas);
        farmaciaService.puedeServirReceta(recetasDto);

        return ResponseEntity.ok(recetasDto);
    }

    /**
     * Endpoint: PUT /api/farmacia/{numColegiado}/recetas
     * Descripción: Marca una receta como SERVIDA y la vincula con la farmacia actual.
     */
    @PutMapping("/recetas")
    public ResponseEntity<String> servirReceta(@RequestBody ServirRecetaRequest request) {
        farmaciaService.servirReceta(request.getRecetaId(), request.getFarmaciaId());
        return ResponseEntity.ok("Receta servida exitosamente.");
    }
}
