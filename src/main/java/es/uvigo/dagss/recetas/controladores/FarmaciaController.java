package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.ServirRecetaRequest;
import es.uvigo.dagss.recetas.dtos.UpdateFarmaciaProfileRequest;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/farmacia/{numColegiado}")
public class FarmaciaController {

    @Autowired
    private FarmaciaService farmaciaService;

    /**
     * Endpoint: GET /api/farmacia/{numColegiado}/home
     * Descripción: Devuelve las opciones disponibles en el menú para la farmacia.
     */
    @GetMapping("/home")
    public ResponseEntity<?> getFarmaciaHome() {
        return ResponseEntity.ok(farmaciaService.getHomeOptions());
    }

    /**
     * Endpoint: GET /api/farmacia/{numColegiado}/perfil
     * Descripción: Devuelve la información personal y profesional de la farmacia.
     */
    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfilFarmacia(@PathVariable("numColegiado") String numColegiado) {
        return ResponseEntity.ok(farmaciaService.getPerfil(numColegiado));
    }

    /**
     * Endpoint: PUT /api/farmacia/{numColegiado}/perfil
     * Descripción: Permite modificar datos personales de la farmacia.
     */
    @PutMapping("/perfil")
    public ResponseEntity<?> updatePerfilFarmacia(
            @PathVariable("numColegiado") String numColegiado,
            @Validated @RequestBody UpdateFarmaciaProfileRequest request) {
        farmaciaService.updatePerfil(request, numColegiado);
        return ResponseEntity.ok("Perfil actualizado exitosamente.");
    }

    /**
     * Endpoint: PUT /api/farmacia/{numColegiado}/perfil/cambiar-contrasena
     * Descripción: Permite a la farmacia actualizar su contraseña de acceso.
     */
    @PutMapping("/perfil/cambiar-contrasena")
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
    // TODO: mirar bien que hacer en este método
//    @GetMapping("/recetas")
//    public ResponseEntity<?> getRecetasPorTarjetaSanitaria(@RequestParam String tarjetaSanitaria) {
//        List<Receta> recetas = farmaciaService.getRecetasPorTarjetaSanitaria(tarjetaSanitaria);
//        return ResponseEntity.ok(recetas);
//    }

    /**
     * Endpoint: PUT /api/farmacia/{numColegiado}/recetas/{recetaId}/servir
     * Descripción: Marca una receta como SERVIDA y la vincula con la farmacia actual.
     */
    @PutMapping("/recetas/{recetaId}/servir")
    public ResponseEntity<?> servirReceta(
            @PathVariable("recetaId") Long recetaId,
            @RequestBody ServirRecetaRequest request) {
        farmaciaService.servirReceta(recetaId, request.getFarmaciaId());
        return ResponseEntity.ok("Receta servida exitosamente.");
    }
}
