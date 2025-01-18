package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.CrearCitaRequest;
import es.uvigo.dagss.recetas.dtos.UpdatePacienteProfileRequest;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/paciente/{numSegSocial}")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private CitaService citaService;

    /**
     * Endpoint: GET /api/paciente/{numSegSocial}/home
     * Descripción: Devuelve las opciones disponibles en el menú para el paciente.
     */
    @GetMapping("/home")
    public ResponseEntity<?> getPacienteHome(@PathVariable("numSegSocial") String numSegSocial) {
        return ResponseEntity.ok(pacienteService.getHomeOptions(numSegSocial));
    }

    /**
     * Endpoint: GET /api/paciente/perfil
     * Descripción: Devuelve la información personal del paciente.
     */
    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfilPaciente(@PathVariable("numSegSocial") String numSegSocial) {
        return ResponseEntity.ok(pacienteService.getPerfil(numSegSocial));
    }

    /**
     * Endpoint: PUT /api/paciente/perfil
     * Descripción: Permite modificar datos personales del paciente.
     */
    @PutMapping("/perfil")
    public ResponseEntity<?> updatePerfilPaciente(
            @Validated @RequestBody UpdatePacienteProfileRequest request,
            @PathVariable("numSegSocial") String numSegSocial) {
        pacienteService.updatePerfil(request, numSegSocial);
        return ResponseEntity.ok("Perfil actualizado exitosamente.");
    }

    /**
     * Endpoint: PUT /api/paciente/perfil/cambiar-contrasena
     * Descripción: Permite al paciente actualizar su contraseña de acceso.
     */
    @PutMapping("/perfil/cambiar-contrasena")
    public ResponseEntity<?> changePasswordPaciente(
            @Validated @RequestBody ChangePasswordRequest request,
            @PathVariable("numSegSocial") String numSegSocial) {
        pacienteService.changePassword(request, numSegSocial);
        return ResponseEntity.ok("Contraseña cambiada exitosamente.");
    }

    /**
     * Endpoint: GET /api/paciente/{numSegSocial}/citas
     * Descripción: Retorna la lista de citas registradas para el paciente logueado con estado PLANIFICADA.
     */
    @GetMapping("/citas")
    public ResponseEntity<?> getCitasFuturas(@PathVariable("numSegSocial") String numSegSocial) {
        List<Cita> citas = citaService.getCitasFuturas(numSegSocial);
        return ResponseEntity.ok(citas);
    }

    /**
     * Endpoint: GET /api/paciente/{numSegSocial}/citas/disponibilidad
     * Descripción: Retorna los huecos de 15 minutos disponibles entre las 8:30 y las 15:30 para la fecha indicada.
     */
    @GetMapping("/citas/disponibilidad")
    public ResponseEntity<?> getDisponibilidadCitas(
            @PathVariable("numSegSocial") String numSegSocial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<String> disponibilidad = citaService.getDisponibilidadCitas(fecha, numSegSocial);
        return ResponseEntity.ok(disponibilidad);
    }

    /**
     * Endpoint: POST /api/paciente/{numSegSocial}/citas
     * Descripción: Crea una nueva cita para el paciente con el médico asignado en la fecha y hora especificadas.
     */
    @PostMapping("/citas")
    public ResponseEntity<?> crearCita(
            @PathVariable("numSegSocial") String numSegSocial,
            @Validated @RequestBody CrearCitaRequest request) {
        citaService.crearCita(request,numSegSocial);
        return new ResponseEntity<>("Cita creada exitosamente.", HttpStatus.CREATED);
    }

    /**
     * Endpoint: PUT /api/paciente/{numSegSocial}/citas/{citaId}/anular
     * Descripción: Marca el estado de la cita seleccionada como ANULADA y actualiza la lista de citas mostrada.
     */
    @PutMapping("/citas/{citaId}/anular")
    public ResponseEntity<?> anularCita(@PathVariable Long citaId) {
        citaService.anularCita(citaId);
        return ResponseEntity.ok("Cita anulada exitosamente.");
    }

//    /**
//     * Endpoint: POST /api/paciente/citas/{cita_id}/confirmar
//     * Descripción: Confirma la asignación de la cita en la fecha y hora indicada.
//     */
//    @PostMapping("/citas/{cita_id}/confirmar")
//    public ResponseEntity<?> confirmarCita(@PathVariable Long cita_id) {
//        pacienteService.confirmarCita(cita_id);
//        return ResponseEntity.ok("Cita confirmada exitosamente.");
//    }


    /**
     * Endpoint: GET /api/paciente/recetas
     * Descripción: Retorna la lista de recetas pendientes de ser servidas ordenadas por fecha, de más próxima a más lejana.
     */
//    @GetMapping("/recetas")
//    public ResponseEntity<?> getRecetasPendientes() {
//        List<Receta> recetas = pacienteService.getRecetasPendientes();
//        return ResponseEntity.ok(recetas);
//    }


}
