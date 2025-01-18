package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.CrearPrescripcionRequest;
import es.uvigo.dagss.recetas.dtos.UpdateMedicoProfileRequest;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import es.uvigo.dagss.recetas.servicios.PrescripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/medico/{numColegiado}")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;
    @Autowired
    private CitaService citaService;
    @Autowired
    private PrescripcionService prescripcionService;

    /**
     * Endpoint: GET /api/medico/{numColegiado}/home
     * Descripción: Devuelve las opciones disponibles en el menú para el médico.
     */
    @GetMapping("/home")
    public ResponseEntity<?> getMedicoHome() {
        return ResponseEntity.ok(medicoService.getHomeOptions());
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/agenda
     * Descripción: Retorna la lista de citas del médico para la fecha especificada (por defecto, el día actual).
     */
    @GetMapping("/agenda")
    public ResponseEntity<?> getAgenda(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @PathVariable("numColegiado") String numColegiado) {

        if (fecha == null) {
            fecha = LocalDate.now();
        }

        return ResponseEntity.ok(citaService.getAgenda(fecha, numColegiado));
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/perfil
     * Descripción: Devuelve la información personal y profesional del médico.
     */
    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfilMedico(@PathVariable("numColegiado") String numColegiado) {
        return ResponseEntity.ok(medicoService.getPerfil(numColegiado));
    }

    /**
     * Endpoint: PUT /api/medico/{numColegiado}/perfil
     * Descripción: Permite modificar datos personales del médico.
     */
    @PutMapping("/perfil")
    public ResponseEntity<?> updatePerfilMedico(
            @Validated @RequestBody UpdateMedicoProfileRequest request,
            @PathVariable("numColegiado") String numColegiado) {
        medicoService.updatePerfil(request, numColegiado);
        return ResponseEntity.ok("Perfil actualizado exitosamente.");
    }

    /**
     * Endpoint: PUT /api/medico/{numColegiado}/perfil/cambiar-contrasena
     * Descripción: Permite al médico actualizar su contraseña de acceso.
     */
    @PutMapping("/perfil/cambiar-contrasena")
    public ResponseEntity<?> changePasswordMedico(
            @Validated @RequestBody ChangePasswordRequest request,
            @PathVariable("numColegiado") String numColegiado) {
        medicoService.changePassword(request, numColegiado);
        return ResponseEntity.ok("Contraseña cambiada exitosamente.");
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/citas/{citaId}
     * Descripción: Devuelve la información detallada de una cita específica.
     */
    @GetMapping("/{citaId}")
    public ResponseEntity<?> getDetallesCita(@PathVariable("citaId") Long citaId) {
        return ResponseEntity.ok(citaService.getDetallesCita(citaId));
    }

    /**
     * Endpoint: PUT /api/medico/{numColegiado}/citas/{citaId}/estado
     * Descripción: Actualiza el estado de una cita (PLANIFICADA, AUSENTE, COMPLETADA).
     */
    @PutMapping("/{citaId}/estado")
    public ResponseEntity<?> actualizarEstadoCita(
            @PathVariable("citaId") Long citaId,
            @RequestParam String estado) {
        citaService.actualizarEstadoCita(citaId, estado);
        return ResponseEntity.ok("Estado de la cita actualizado exitosamente.");
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/citas/{citaId}/prescripciones
     * Descripción: Retorna las prescripciones activas del paciente asociado a la cita.
     */
    @GetMapping("/{citaId}/prescripciones")
    public ResponseEntity<?> getPrescripcionesEnCita(@PathVariable("citaId") Long citaId) {
        return ResponseEntity.ok(citaService.getPrescripcionesEnCita(citaId));
    }

    /**
     * Endpoint: POST /api/medico/{numColegiado}/prescripcion
     * Descripción: Crea una nueva prescripción para un paciente.
     */
    @PostMapping
    public ResponseEntity<?> crearPrescripcion(@RequestBody CrearPrescripcionRequest request) {
        prescripcionService.crearPrescripcion(request);
        return new ResponseEntity<>("Prescripción creada exitosamente.", HttpStatus.CREATED);
    }

    // TODO: falta eliminar Prescripción (Hu-m3) (marcar como inactiva)

    /**
     * Endpoint: GET /api/medico/{numColegiado}/prescripcion/{prescripcionId}/recetas
     * Descripción: Retorna el plan de recetas generado para una prescripción específica.
     */
    @GetMapping("/prescripciones/{prescripcionId}/recetas")
    public ResponseEntity<?> getPlanRecetas(@PathVariable Long prescripcionId) {
        return ResponseEntity.ok(prescripcionService.getPlanRecetas(prescripcionId));
    }

    // TODO: falta eliminar plan de recetas asociado a prescripción (marcar como anulada)

}
