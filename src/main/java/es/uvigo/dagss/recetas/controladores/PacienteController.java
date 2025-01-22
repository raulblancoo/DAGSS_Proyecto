package es.uvigo.dagss.recetas.controladores;

import com.fasterxml.jackson.annotation.JsonView;
import es.uvigo.dagss.recetas.dtos.*;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.mappers.CitaMapper;
import es.uvigo.dagss.recetas.mappers.PacienteMapper;
import es.uvigo.dagss.recetas.mappers.RecetaMapper;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.PacienteService;
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
@RequestMapping("/api/paciente/{numSegSocial}")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteMapper pacienteMapper;
    @Autowired
    private CitaService citaService;
    @Autowired
    private CitaMapper citaMapper;
    @Autowired
    private RecetaService recetaService;
    @Autowired
    private RecetaMapper recetaMapper;

    /**
     * Endpoint: GET /api/paciente/{numSegSocial}/home
     * Descripción: Devuelve las opciones disponibles en el menú para el paciente.
     */
    @GetMapping("/home")
    public ResponseEntity<List<String>> getPacienteHome(@PathVariable("numSegSocial") String numSegSocial) {
        return ResponseEntity.ok(pacienteService.getHomeOptions(numSegSocial));
    }

    /**
     * Endpoint: GET /api/paciente/{numSegSocial}/perfil
     * Descripción: Devuelve la información personal del paciente.
     */
    @GetMapping("/perfil")
    public ResponseEntity<PacienteProfile> getPerfilPaciente(@PathVariable("numSegSocial") String numSegSocial) {
        return ResponseEntity.ok(pacienteMapper.toProfileDto(pacienteService.getPerfil(numSegSocial)));
    }

    /**
     * Endpoint: PUT /api/paciente/{numSegSocial}/perfil
     * Descripción: Permite modificar datos personales del paciente.
     */
    @PutMapping("/perfil")
    public ResponseEntity<String> updatePerfilPaciente(
            @Validated @RequestBody UpdatePacienteProfileRequest request,
            @PathVariable("numSegSocial") String numSegSocial) {
        pacienteService.updatePerfil(request, numSegSocial);
        return ResponseEntity.ok("Perfil actualizado exitosamente.");
    }

    /**
     * Endpoint: PUT /api/paciente/perfil/password
     * Descripción: Permite al paciente actualizar su contraseña de acceso.
     */
    @PutMapping("/perfil/password")
    public ResponseEntity<String> changePasswordPaciente(
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
    @JsonView(Vistas.VistaCitaDetallePaciente.class)
    public ResponseEntity<List<CitaDto>> getCitasFuturas(@PathVariable("numSegSocial") String numSegSocial) {
        List<Cita> citas = citaService.getCitasFuturas(numSegSocial);

        if(citas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(citaMapper.toListDto(citas));
    }

    /**
     * Endpoint: GET /api/paciente/{numSegSocial}/citas/disponibilidad
     * Descripción: Retorna los huecos de 15 minutos disponibles entre las 8:30 y las 15:30 para la fecha indicada.
     */
    @GetMapping("/citas/disponibilidad")
    public ResponseEntity<List<String>> getDisponibilidadCitas(
            @PathVariable("numSegSocial") String numSegSocial,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fecha) {

        if (fecha == null) {
            fecha = LocalDate.now();
        }

        List<String> disponibilidad = citaService.getDisponibilidadCitas(fecha, numSegSocial);
        if(disponibilidad.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(disponibilidad);
    }

    /**
     * Endpoint: POST /api/paciente/{numSegSocial}/citas
     * Descripción: Crea una nueva cita para el paciente con el médico asignado en la fecha y hora especificadas.
     */
    @PostMapping("/citas")
    public ResponseEntity<String> crearCita(
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
    public ResponseEntity<String> anularCita(@PathVariable("citaId") Long citaId) {
        citaService.anularCita(citaId);
        return ResponseEntity.ok("Cita anulada exitosamente.");
    }

    /**
     * Endpoint: GET /api/paciente/{numSegSocial}/recetas
     * Descripción: Retorna la lista de recetas pendientes de ser servidas ordenadas por fecha, de más próxima a más lejana.
     */
    @GetMapping("/recetas")
    @JsonView(Vistas.VistaRecetaPaciente.class)
    public ResponseEntity<List<RecetaDto>> getRecetasPendientes(@PathVariable("numSegSocial") String numSegSocial) {
        List<Receta> recetas = recetaService.buscarRecetasPendientes(numSegSocial);

        if(recetas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(recetaMapper.toListDto(recetas));
    }


}
