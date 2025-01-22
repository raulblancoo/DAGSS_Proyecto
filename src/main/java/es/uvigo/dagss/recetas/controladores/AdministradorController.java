package es.uvigo.dagss.recetas.controladores;

import com.fasterxml.jackson.annotation.JsonView;
import es.uvigo.dagss.recetas.dtos.*;
import es.uvigo.dagss.recetas.entidades.*;
import es.uvigo.dagss.recetas.mappers.*;
import es.uvigo.dagss.recetas.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdministradorController {
    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private AdministradorMapper administradorMapper;
    @Autowired
    private CentroSaludService centroSaludService;
    @Autowired
    private CentroSaludMapper centroSaludMapper;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private MedicoMapper medicoMapper;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteMapper pacienteMapper;
    @Autowired
    private FarmaciaService farmaciaService;
    @Autowired
    private FarmaciaMapper farmaciaMapper;
    @Autowired
    private CitaService citaService;
    @Autowired
    private CitaMapper citaMapper;
    @Autowired
    private MedicamentoService medicamentoService;
    @Autowired
    private MedicamentoMapper medicamentoMapper;

    /* GESTIÓN DE ADMINISTRADORES */

    /**
     * HU-A1: Home de administradores
     * Endpoint: GET /api/admin/home
     * Descripción: Devuelve las opciones disponibles en el menú para el administrador.
     */
    @GetMapping("/home")
    public ResponseEntity<List<String>> getAdministradorHome() {
        return ResponseEntity.ok(administradorService.getHomeOptions());
    }

    /**
     * HU-A2: Gestión de administradores
     * Endpoint: GET /api/admin
     * Descripción: Obtiene la lista de administradores registrados.
     */
    @GetMapping
    public ResponseEntity<List<AdministradorDto>> listarAdministradores() {
        List<Administrador> administradores = administradorService.buscarTodos();
        if (administradores.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(administradorMapper.toListDto(administradores));
        }
    }

    /**
     * Endpoint: POST /api/admin
     * Descripción: Crea un nuevo administrador.
     */
    @PostMapping
    public ResponseEntity<AdministradorDto> crearAdministrador(
            @Validated @RequestBody CrearAdminRequest request) {
        Administrador admin = administradorService.crearAdministrador(request);
        URI uri = crearURIAdministrador(admin);
        return ResponseEntity.created(uri).body(administradorMapper.toDto(admin));
    }

    /**
     * Endpoint: PUT /api/admin/{adminId}
     * Descripción: Actualiza los datos de un administrador existente.
     */
    @PutMapping("/{adminId}")
    public ResponseEntity<AdministradorDto> actualizarAdministrador(
            @PathVariable("adminId") Long adminId,
            @Validated @RequestBody UpdateAdminRequest request) {
        Administrador administrador = administradorService.actualizarAdministrador(request, adminId);
        return ResponseEntity.ok(administradorMapper.toDto(administrador));
    }

    /**
     * Endpoint: PUT /api/admin/{adminId}/password
     * Descripción: Cambia la contraseña de un administrador.
     */
    @PutMapping("/{adminId}/password")
    public ResponseEntity<String> cambiarContrasenaAdministrador(
            @PathVariable("adminId") Long adminId,
            @Validated @RequestBody ChangePasswordRequest request) {
        administradorService.cambiarPassword(request,adminId);
        return ResponseEntity.ok("Contraseña cambiada exitosamente.");
    }

    /**
     * Endpoint: DELETE /api/admin/{adminId}
     * Descripción: Elimina lógicamente un administrador (activa = false).
     */
    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> eliminarAdministrador(@PathVariable("adminId") Long adminId) {
        administradorService.eliminarAdministrador(adminId);
        return ResponseEntity.ok("Administrador eliminado exitosamente.");
    }


    /* GESTIÓN DE CENTROS DE SALUD */

    /**
     * HU-A3: Gestión de centros de salud
     * Endpoint: GET /api/admin/centroSalud
     * Descripción: Obtiene la lista de centros de salud registrados con filtros opcionales.
     */
    @GetMapping("/centroSalud")
    public ResponseEntity<List<CentroSaludDto>> listarCentrosSalud(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "localidad", required = false) String localidad) {

        List<CentroSalud> listaCentros = centroSaludService.buscarCentrosConFiltros(nombre, localidad);

        if (listaCentros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(centroSaludMapper.toListDto(listaCentros));
    }

    /**
     * Endpoint: POST /api/admin/centroSalud
     * Descripción: Crea un nuevo centro de salud.
     */
    @PostMapping("/centroSalud")
    public ResponseEntity<CentroSaludDto> crearCentro(@Validated @RequestBody CrearCentroSaludRequest request) {
        CentroSalud centroSalud = centroSaludService.crearCentro(request);
        URI uri = crearURICentroSalud(centroSalud);
        return ResponseEntity.created(uri).body(centroSaludMapper.toDto(centroSalud));
    }

    /**
     * Endpoint: PUT /api/admin/centroSalud/{centroId}
     * Descripción: Actualiza los datos de un centro de salud existente.
     */
    @PutMapping("/centroSalud/{centroId}")
    public ResponseEntity<CentroSaludDto> actualizarCentroSalud(
            @PathVariable("centroId") Long centroId,
            @Validated @RequestBody CrearCentroSaludRequest request) {
        CentroSalud centro = centroSaludService.actualizarCentro(centroId, request);
        return ResponseEntity.ok(centroSaludMapper.toDto(centro));
    }

    /**
     * Endpoint: DELETE /api/admin/centroSalud/{centroId}
     * Descripción: Elimina lógicamente un centro de salud (activo = false).
     */
    @DeleteMapping("/centroSalud/{centroId}")
    public ResponseEntity<String> eliminarCentroSalud(@PathVariable("centroId") Long centroId) {
        centroSaludService.eliminarCentro(centroId);
        return ResponseEntity.ok("Centro de salud eliminado exitosamente.");
    }

    /* GESTIÓN DE MÉDICOS */

    /**
     * HU-A4: Gestión de médicos
     * Endpoint: GET /api/admin/medicos
     * Descripción: Obtiene la lista de médicos registrados con filtros opcionales.
     */
    @GetMapping("/medicos")
    public ResponseEntity<List<MedicoDto>> listarMedicos(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "localidad", required = false) String localidad,
            @RequestParam(name = "centroSalud", required = false) Long centroSaludId) {

        List<Medico> listaMedicos = medicoService.buscarMedicosConFiltros(nombre, localidad, centroSaludId);

        if (listaMedicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(medicoMapper.toListDto(listaMedicos));
    }

    /**
     * Endpoint: POST /api/admin/medicos
     * Descripción: Crea un nuevo médico.
     */
    @PostMapping("/medicos")
    public ResponseEntity<MedicoDto> crearMedico(
            @Validated @RequestBody CrearMedicoRequest request) {
        Medico medico = medicoService.crearMedico(request);
        URI uri = crearURIMedico(medico);
        return ResponseEntity.created(uri).body(medicoMapper.toDto(medico));
    }

    /**
     * Endpoint: PUT /api/admin/medicos/{medicoId}
     * Descripción: Actualiza los datos de un médico existente.
     */
    @PutMapping("/medicos/{medicoId}")
    public ResponseEntity<MedicoDto> actualizarMedico(
            @PathVariable("medicoId") Long medicoId,
            @Validated @RequestBody CrearMedicoRequest request) {
        Medico medico = medicoService.actualizarMedico(medicoId, request);
        return ResponseEntity.ok(medicoMapper.toDto(medico));
    }

    /**
     * Endpoint: DELETE /api/admin/medicos/{medicoId}
     * Descripción: Elimina lógicamente un médico (activo = false).
     */
    @DeleteMapping("/medicos/{medicoId}")
    public ResponseEntity<String> eliminarMedico(@PathVariable("medicoId") Long medicoId) {
        medicoService.eliminarMedico(medicoId);
        return ResponseEntity.ok("Médico eliminado exitosamente.");
    }


    /* GESTIÓN DE PACIENTES */

    // TODO: no se mappea bien la localidad y provincia del centro de salud
    /**
     * HU-A5: Gestión de pacientes
     * Endpoint: GET /api/admin/pacientes
     * Descripción: Obtiene la lista de pacientes registrados con filtros opcionales.
     */
    @GetMapping("/pacientes")
    public ResponseEntity<List<PacienteDto>> listarPacientes(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "localidad", required = false) String localidad,
            @RequestParam(name = "centroSalud", required = false) Long centroSaludId,
            @RequestParam(name = "medico", required = false) Long medicoId) {

        List<Paciente> listaPacientes = pacienteService.buscarPacientesConFiltros(nombre, localidad, centroSaludId, medicoId);

        if (listaPacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pacienteMapper.toListDto(listaPacientes));
    }

    /**
     * Endpoint: POST /api/admin/pacientes
     * Descripción: Crea un nuevo paciente.
     */
    @PostMapping("/pacientes")
    public ResponseEntity<PacienteDto> crearPaciente(@Validated @RequestBody CrearPacienteRequest request) {
        Paciente paciente = pacienteService.crearPaciente(request);
        URI uri = crearURIPaciente(paciente);
        return ResponseEntity.created(uri).body(pacienteMapper.toDto(paciente));
    }

    /**
     * Endpoint: PUT /api/admin/pacientes/{pacienteId}
     * Descripción: Actualiza los datos de un paciente existente.
     */
    @PutMapping("/pacientes/{pacienteId}")
    public ResponseEntity<PacienteDto> actualizarPaciente(
            @PathVariable("pacienteId") Long pacienteId,
            @Validated @RequestBody CrearPacienteRequest request) {
        Paciente paciente = pacienteService.actualizarPaciente(pacienteId, request);
        return ResponseEntity.ok(pacienteMapper.toDto(paciente));
    }

    /**
     * Endpoint: DELETE /api/admin/pacientes/{pacienteId}
     * Descripción: Elimina lógicamente un paciente (activo = false).
     */
    @DeleteMapping("/pacientes/{pacienteId}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable("pacienteId") Long pacienteId) {
        pacienteService.eliminarPaciente(pacienteId);
        return ResponseEntity.ok("Paciente eliminado exitosamente.");
    }


    /* GESTIÓN DE FARMACIAS */

    /**
     * HU-A6: Gestión de farmacias
     * Endpoint: GET /api/admin/farmacias
     * Descripción: Obtiene la lista de farmacias registradas con filtros opcionales.
     */
    @GetMapping("/farmacias")
    public ResponseEntity<List<FarmaciaDto>> listarFarmacias(
            @RequestParam(name = "nombreEstablecimiento", required = false) String nombreEstablecimiento,
            @RequestParam(name = "localidad", required = false) String localidad) {

        List<Farmacia> listaFarmacias = farmaciaService.buscarFarmaciasConFiltros(nombreEstablecimiento, localidad);

        if (listaFarmacias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(farmaciaMapper.toListDto(listaFarmacias));
    }

    /**
     * Endpoint: POST /api/admin/farmacias
     * Descripción: Crea una nueva farmacia.
     */
    @PostMapping("/farmacias")
    public ResponseEntity<FarmaciaDto> crearFarmacia(
            @Validated @RequestBody CrearFarmaciaRequest request) {
        Farmacia farmacia = farmaciaService.crearFarmacia(request);
        URI uri = crearURIFarmacia(farmacia);
        return ResponseEntity.created(uri).body(farmaciaMapper.toDto(farmacia));
    }

    /**
     * Endpoint: PUT /api/admin/farmacias/{farmaciaId}
     * Descripción: Actualiza los datos de una farmacia existente.
     */
    @PutMapping("/farmacias/{farmaciaId}")
    public ResponseEntity<FarmaciaDto> actualizarFarmacia(@PathVariable("farmaciaId") Long farmaciaId,
            @Validated @RequestBody CrearFarmaciaRequest request) {
        Farmacia farmacia = farmaciaService.actualizarFarmacia(farmaciaId, request);
        return ResponseEntity.ok(farmaciaMapper.toDto(farmacia));
    }

    /**
     * Endpoint: DELETE /api/admin/farmacias/{farmaciaId}
     * Descripción: Elimina lógicamente una farmacia (activo = false).
     */
    @DeleteMapping("/farmacias/{farmaciaId}")
    public ResponseEntity<String> eliminarFarmacia(@PathVariable("farmaciaId") Long farmaciaId) {
        farmaciaService.eliminarFarmacia(farmaciaId);
        return ResponseEntity.ok("Farmacia eliminada exitosamente.");
    }


    /* GESTIÓN DE CITAS */

    /**
     * HU-A7: Gestión "manual" de citas
     * Endpoint: GET /api/admin/citas
     * Descripción: Obtiene la lista de citas registradas con filtros por fecha, médico y paciente.
     */
    @GetMapping("/citas")
    @JsonView(Vistas.VistaCitaAdmin.class)
    public ResponseEntity<List<CitaDto>> listarCitas(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fecha,
            @RequestParam(name = "medico",required = false) Long medicoId,
            @RequestParam(name = "paciente",required = false) Long pacienteId) {

        if (fecha == null) {
            fecha = LocalDate.now();
        }

        List<Cita> citas = citaService.buscarCitasConParametros(fecha, medicoId, pacienteId);

        if(citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(citaMapper.toListDto(citas));
    }

    /**
     * Endpoint: PUT /api/admin/citas/{citaId}
     * Descripción: Anula una cita existente (estado = ANULADA).
     */
    @PutMapping("/citas/{citaId}")
    public ResponseEntity<String> anularCita(@PathVariable("citaId") Long citaId) {
        citaService.anularCita(citaId);
        return ResponseEntity.ok("Cita anulada exitosamente.");
    }


    /* GESTIÓN DE MEDICAMENTOS */
    /**
     * HU-A8: Gestión de medicamentos
     * Endpoint: GET /api/admin/medicamentos
     * Descripción: Obtiene la lista de medicamentos registrados con filtros opcionales.
     */
    @GetMapping("/medicamentos")
    public ResponseEntity<List<MedicamentoDto>> listarMedicamentos(
            @RequestParam(name = "nombreComercial", required = false) String nombreComercial,
            @RequestParam(name = "principioActivo", required = false) String principioActivo,
            @RequestParam(name = "fabricante", required = false) String fabricante,
            @RequestParam(name = "familia", required = false) String familia){

        List<Medicamento> listaMedicamentos = medicamentoService.buscarMedicamentoConFiltros(nombreComercial, principioActivo, fabricante, familia);

        if (listaMedicamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(medicamentoMapper.toListDto(listaMedicamentos));
    }

    /**
     * Endpoint: POST /api/admin/medicamentos
     * Descripción: Crea un nuevo medicamento.
     */
    @PostMapping("/medicamentos")
    public ResponseEntity<MedicamentoDto> crearMedicamento(@Validated @RequestBody CrearMedicamentoRequest request) {
        Medicamento medicamento = medicamentoService.crearMedicamento(request);
        URI uri = crearURIMedicamento(medicamento);
        return ResponseEntity.created(uri).body(medicamentoMapper.toDto(medicamento));
    }

    /**
     * Endpoint: PUT /api/admin/medicamentos/{medicamentoId}
     * Descripción: Actualiza los datos de un medicamento existente.
     */
    @PutMapping("/medicamentos/{medicamentoId}")
    public ResponseEntity<MedicamentoDto> actualizarMedicamento(@PathVariable("medicamentoId") Long medicamentoId,
            @Validated @RequestBody CrearMedicamentoRequest request) {
        Medicamento medicamento = medicamentoService.actualizarMedicamento(medicamentoId, request);
        return ResponseEntity.ok(medicamentoMapper.toDto(medicamento));
    }

    /**
     * Endpoint: DELETE /api/admin/medicamentos/{medicamentoId}
     * Descripción: Elimina lógicamente un medicamento (activo = false).
     */
    @DeleteMapping("/medicamentos/{medicamentoId}")
    public ResponseEntity<String> eliminarMedicamento(@PathVariable("medicamentoId") Long medicamentoId) {
        medicamentoService.eliminarMedicamento(medicamentoId);
        return ResponseEntity.ok("Medicamento eliminado exitosamente.");
    }

    /* UTILS */

    private URI crearURIAdministrador(Administrador administrador) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(administrador.getId()).toUri();
    }

    private URI crearURICentroSalud(CentroSalud centroSalud) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(centroSalud.getId()).toUri();
    }

    private URI crearURIMedico(Medico medico) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(medico.getId()).toUri();
    }

    private URI crearURIPaciente(Paciente paciente) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(paciente.getId()).toUri();
    }

    private URI crearURIFarmacia(Farmacia farmacia) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(farmacia.getId()).toUri();
    }

    private URI crearURIMedicamento(Medicamento medicamento) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(medicamento.getId()).toUri();
    }


}
