package es.uvigo.dagss.recetas.controladores;

import com.fasterxml.jackson.annotation.JsonView;
import es.uvigo.dagss.recetas.dtos.*;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.mappers.CitaMapper;
import es.uvigo.dagss.recetas.mappers.MedicoMapper;
import es.uvigo.dagss.recetas.mappers.PrescripcionMapper;
import es.uvigo.dagss.recetas.mappers.RecetaMapper;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import es.uvigo.dagss.recetas.servicios.PrescripcionService;
import es.uvigo.dagss.recetas.servicios.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medico/{numColegiado}")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;
    @Autowired
    private MedicoMapper medicoMapper;
    @Autowired
    private CitaService citaService;
    @Autowired
    private CitaMapper citaMapper;
    @Autowired
    private PrescripcionService prescripcionService;
    @Autowired
    private PrescripcionMapper prescripcionMapper;
    @Autowired
    private RecetaService recetaService;
    @Autowired
    private RecetaMapper recetaMapper;

    /**
     * Endpoint: GET /api/medico/{numColegiado}/home
     * Descripción: Devuelve las opciones disponibles en el menú para el médico.
     */
    @GetMapping("/home")
    public ResponseEntity<List<String>> getMedicoHome() {
        return ResponseEntity.ok(medicoService.getHomeOptions());
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/agenda
     * Descripción: Retorna la lista de citas del médico para la fecha especificada (por defecto, el día actual).
     */
    @GetMapping("/agenda")
    @JsonView(Vistas.VistaCitaAgendaMedico.class)
    public ResponseEntity<List<CitaDto>> getAgenda(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fecha,
            @PathVariable("numColegiado") String numColegiado) {

        if (fecha == null) {
            fecha = LocalDate.now();
        }

        List<Cita> citas = citaService.getAgenda(fecha, numColegiado);
        if(citas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(citaMapper.toListDto(citas));
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/perfil
     * Descripción: Devuelve la información personal y profesional del médico.
     */
    @GetMapping("/perfil")
    public ResponseEntity<MedicoProfile> getPerfilMedico(@PathVariable("numColegiado") String numColegiado) {
        return ResponseEntity.ok(medicoMapper.toProfileDto(medicoService.getPerfil(numColegiado)));
    }

    /**
     * Endpoint: PUT /api/medico/{numColegiado}/perfil
     * Descripción: Permite modificar datos personales del médico.
     */
    @PutMapping("/perfil")
    public ResponseEntity<String> updatePerfilMedico(
            @Validated @RequestBody UpdateMedicoProfileRequest request,
            @PathVariable("numColegiado") String numColegiado) {
        medicoService.updatePerfil(request, numColegiado);
        return ResponseEntity.ok("Perfil actualizado exitosamente.");
    }

    /**
     * Endpoint: PUT /api/medico/{numColegiado}/perfil/password
     * Descripción: Permite al médico actualizar su contraseña de acceso.
     */
    @PutMapping("/perfil/password")
    public ResponseEntity<String> changePasswordMedico(
            @Validated @RequestBody ChangePasswordRequest request,
            @PathVariable("numColegiado") String numColegiado) {
        medicoService.changePassword(request, numColegiado);
        return ResponseEntity.ok("Contraseña cambiada exitosamente.");
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/citas/{citaId}
     * Descripción: Devuelve la información detallada de una cita específica.
     */
    @GetMapping("/citas/{citaId}")
    @JsonView(Vistas.VistaCitaDetalleMedico.class)
    public ResponseEntity<CitaDto> getDetallesCita(@PathVariable("citaId") Long citaId) {
        Cita cita = citaService.getDetallesCita(citaId);

        if(cita == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(citaMapper.toDto(cita));
    }

    /**
     * Endpoint: PUT /api/medico/{numColegiado}/citas/{citaId}
     * Descripción: Actualiza el estado de una cita (PLANIFICADA, AUSENTE, COMPLETADA).
     * Ejemplo: api/medico/CO12345/citas/1?estado=AUSENTE
     */
    @PutMapping("/citas/{citaId}")
    public ResponseEntity<String> actualizarEstadoCita(
            @PathVariable("citaId") Long citaId,
            @RequestParam Cita.EstadoCita estado) {
        citaService.actualizarEstadoCita(citaId, estado);
        return ResponseEntity.ok("Estado de la cita actualizado exitosamente.");
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/citas/{citaId}/prescripciones
     * Descripción: Retorna las prescripciones activas del paciente asociado a la cita.
     */
    @GetMapping("/citas/{citaId}/prescripciones")
    public ResponseEntity<List<PrescripcionDto>> getPrescripcionesEnCita(@PathVariable("citaId") Long citaId) {
        List<Prescripcion> listaPrescripcion = citaService.getPrescripcionesEnCita(citaId);

        if(listaPrescripcion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(prescripcionMapper.toListDto(listaPrescripcion));
    }

    /**
     * Endpoint: POST /api/medico/{numColegiado}/prescripcion
     * Descripción: Crea una nueva prescripción para un paciente.
     */
    @PostMapping("/prescripcion")
    public ResponseEntity<String> crearPrescripcion(@RequestBody CrearPrescripcionRequest request) {
        prescripcionService.crearPrescripcion(request);
        return new ResponseEntity<>("Prescripción creada exitosamente.", HttpStatus.CREATED);
    }

    /**
     * Endpoint: PUT /api/medico/{numColegiado}/prescripcion/{prescripcionId}
     * Descripción: Actualiza el estado de una prescripción (ACTIVO, INACTIVO).
     * Ejemplo: api/medico/CO12345/prescripcion/1?estado=INACTIVO
     */
    @PutMapping("/prescripcion/{prescripcionId}")
    public ResponseEntity<String> actualizarEstadoPrescripcion(
            @PathVariable("prescripcionId") Long prescripcionId,
            @RequestParam Prescripcion.Estado estado) {
        prescripcionService.actualizarEstadoPrescripcion(prescripcionId, estado);
        return ResponseEntity.ok("Estado de la cita actualizado exitosamente.");
    }

    /**
     * Endpoint: GET /api/medico/{numColegiado}/prescripcion/{prescripcionId}/recetas
     * Descripción: Retorna el plan de recetas generado para una prescripción específica.
     */
    @GetMapping("/prescripcion/{prescripcionId}/recetas")
    @JsonView(Vistas.VistaRecetaMedico.class)
    public ResponseEntity<List<RecetaDto>> getPlanRecetas(@PathVariable Long prescripcionId) {
        List<Receta> recetas = recetaService.buscarPlanesPorPrescripcionId(prescripcionId);

        if(recetas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(recetaMapper.toListDto(recetas));
    }

}
