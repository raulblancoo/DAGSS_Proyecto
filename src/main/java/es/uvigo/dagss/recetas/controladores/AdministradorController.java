package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.dtos.*;
import es.uvigo.dagss.recetas.entidades.*;
import es.uvigo.dagss.recetas.mappers.*;
import es.uvigo.dagss.recetas.servicios.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministradorController {
    @Autowired
    AdministradorService administradorService;
    @Autowired
    CentroSaludService centroSaludService;
    @Autowired
    CentroSaludMapper centroSaludMapper;
    @Autowired
    MedicoService medicoService;
    @Autowired
    MedicoMapper medicoMapper;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    PacienteMapper pacienteMapper;
    @Autowired
    private FarmaciaService farmaciaService;
    @Autowired
    private FarmaciaMapper farmaciaMapper;
    @Autowired
    private CitaService citaService;
    @Autowired
    private CitaMapper citaMapper;

//    public AdministradorController(AdministradorService administradorService, CentroSaludService centroSaludService, CentroSaludMapper centroSaludMapper, MedicoService medicoService, MedicoMapper medicoMapper, PacienteService pacienteService, PacienteMapper pacienteMapper) {
//        this.administradorService = administradorService;
//        this.centroSaludService = centroSaludService;
//        this.centroSaludMapper = centroSaludMapper;
//        this.medicoService = medicoService;
//        this.medicoMapper = medicoMapper;
//        this.pacienteService = pacienteService;
//        this.pacienteMapper = pacienteMapper;
//    }

    /* GESTIÓN DE ADMINISTRADORES */

    @GetMapping
    public ResponseEntity<List<Administrador>> listarAdministradores() {
        List<Administrador> administradores = administradorService.buscarTodos();
        if (administradores.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(administradores);
        }
    }

    @PostMapping
    public ResponseEntity<Administrador> crearAdministrador(@Valid @RequestBody Administrador administrador) {
        try {
            administradorService.crearAdministrador(
                    administrador.getLogin(), administrador.getPassword(),
                    administrador.getNombre(), administrador.getEmail());
            return ResponseEntity.ok(administrador);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> editarAdministrador(@PathVariable Long id, @RequestBody Administrador datos) {
        try {
            administradorService.editarAdministrador(id, datos.getNombre(), datos.getEmail(), datos.getActivo());
            return ResponseEntity.ok(datos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public void eliminarAdministrador(@PathVariable Long id) {
        administradorService.desactivarAdministrador(id);
    }

    /* GESTIÓN DE CENTROS DE SALUD */

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

    @PostMapping("/centroSalud")
    public ResponseEntity<CentroSalud> crearCentro(@Valid @RequestBody CentroSalud datosCentroSalud) {
        CentroSalud centroSalud = centroSaludService.crearCentro(datosCentroSalud);
        URI uri = crearURICentroSalud(centroSalud);
        return ResponseEntity.created(uri).body(centroSalud);
    }

    // TODO: debe devolver un 200
    @PutMapping("/centroSalud/{centroSaludId}")
    public ResponseEntity<CentroSalud> editarCentro(@PathVariable("centroSaludId") Long centroSaludId, @RequestBody CentroSalud datos) {
        return ResponseEntity.ok(centroSaludService.editarCentro(centroSaludId, datos));
    }

    // TODO: ResponseEntity
    @DeleteMapping("/centroSalud/{centroSaludId}")
    public void eliminarCentro(@PathVariable("centroSaludId") Long centroSaludId) {
        centroSaludService.desactivarCentro(centroSaludId);
    }

    /* GESTIÓN DE MÉDICOS */

    // TODO: no se mappea bien la localidad y provincia del centro de salud
    @GetMapping("/medico")
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

    @PostMapping("/medico")
    public ResponseEntity<Medico> crearMedico(@Valid @RequestBody Medico datosMedico) {
        Medico medico = medicoService.crearMedico(datosMedico);
        URI uri = crearURIMedico(medico);
        return ResponseEntity.created(uri).body(medico);
    }

    // TODO: no funciona el método de editarMedico, corregir
    @PutMapping("/medico/{medicoId}")
    public ResponseEntity<Medico> editarMedico(@PathVariable("medicoId") Long medicoId, @RequestBody Medico datos) {
        try {
            Medico medico = medicoService.editarMedico(medicoId, datos);
            return ResponseEntity.ok(medico);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/medico/{medicoId}")
    public void eliminarMedico(@PathVariable("medicoId") Long medicoId) {
        medicoService.eliminarMedico(medicoId);
    }

    /* GESTIÓN DE PACIENTES */

    // TODO: no se mappea bien la localidad y provincia del centro de salud
    @GetMapping("/paciente")
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


    @PostMapping("/paciente")
    public ResponseEntity<Paciente> crearPaciente(@Valid @RequestBody Paciente datosPaciente) {
        // TODO: si se ponen datos mal rollo médico o centrosalud inexistente peta
        Paciente paciente = pacienteService.crearPaciente(datosPaciente);
        URI uri = crearURIPaciente(paciente);
        return ResponseEntity.created(uri).body(paciente);
    }

    // TODO: HACER
    @PutMapping("/paciente/{pacienteId}")
    public ResponseEntity<Paciente> editarPaciente(@PathVariable("pacienteId") Long pacienteId, @RequestBody PacienteCreateDto datosPaciente) {
        //pacienteService.editarPaciente(pacienteId,datosPaciente);
        return null;
    }

    @DeleteMapping("/paciente/{pacienteId}")
    public void eliminarPaciente(@PathVariable("pacienteId") Long pacienteId) {
        pacienteService.eliminarPaciente(pacienteId);
    }


    /* GESTIÓN DE FARMACIAS */

    @GetMapping("/farmacia")
    public ResponseEntity<List<FarmaciaDto>> listarFarmacias(
            @RequestParam(name = "nombreEstablecimiento", required = false) String nombreEstablecimiento,
            @RequestParam(name = "localidad", required = false) String localidad) {

        List<Farmacia> listaFarmacias = farmaciaService.buscarFarmaciasConFiltros(nombreEstablecimiento, localidad);

        if (listaFarmacias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(farmaciaMapper.toListDto(listaFarmacias));
    }

    @PostMapping("/farmacia")
    public ResponseEntity<Farmacia> crearFarmacia(@Valid @RequestBody Farmacia datosFarmacia) {
        Farmacia farmacia = farmaciaService.crearFarmacia(datosFarmacia);
        URI uri = crearURIFarmacia(farmacia);
        return ResponseEntity.created(uri).body(farmacia);

    }

    @PutMapping("/farmacia/{farmaciaId}")
    public ResponseEntity<FarmaciaDto> editarFarmacia(@PathVariable("farmaciaId") Long farmaciaId, @Valid @RequestBody Farmacia datosFarmacia) {
        return null;
    }

    @PostMapping("/farmacia/{farmaciaId}")
    public void eliminarFarmacia(@PathVariable("farmaciaId") Long farmaciaId) {
        farmaciaService.eliminarFarmacia(farmaciaId);
    }

    /* GESTIÓN DE CITAS */

    // TODO: Revisar y poner el método como en el resto de sitios
    @GetMapping("/cita")
    public ResponseEntity<List<CitaDto>> listarCitas(
            @RequestParam("fecha") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
            @RequestParam(value = "medico", required = false) Long medicoId,
            @RequestParam(value = "paciente", required = false) Long pacienteId) {

        List<Cita> citas = citaService.listarCitas(date, medicoId, pacienteId);

        return ResponseEntity.ok(citaMapper.toListDto(citas));
    }

    // TODO: Patch según CHATGPT es lo más correcto, es la verdad???
    @PatchMapping("cita/{citaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void anularCita(@PathVariable("citaId") Long citaId) {
        citaService.anularCita(citaId);
    }

    /* GESTIÓN DE CITAS */


    /* UTILS */

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


}
