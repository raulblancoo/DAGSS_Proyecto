package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.dtos.CentroSaludDto;
import es.uvigo.dagss.recetas.dtos.MedicoDto;
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.mappers.CentroSaludMapper;
import es.uvigo.dagss.recetas.mappers.MedicoMapper;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import es.uvigo.dagss.recetas.servicios.CentroSaludService;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministradorController {
    private final AdministradorService administradorService;
    private final CentroSaludService centroSaludService;
    private final CentroSaludMapper centroSaludMapper;
    private final MedicoService medicoService;
    private final MedicoMapper medicoMapper;

    public AdministradorController(AdministradorService administradorService, CentroSaludService centroSaludService, CentroSaludMapper centroSaludMapper, MedicoService medicoService, MedicoMapper medicoMapper) {
        this.administradorService = administradorService;
        this.centroSaludService = centroSaludService;
        this.centroSaludMapper = centroSaludMapper;
        this.medicoService = medicoService;
        this.medicoMapper = medicoMapper;
    }

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
    public ResponseEntity<Administrador> crearAdministrador(@RequestBody Administrador administrador) {
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

    // TODO: verificar si necesitamos el like en el repositorio y no es mejor un containing...
    @GetMapping("/centroSalud")
    public ResponseEntity<List<CentroSaludDto>> listarCentrosSalud(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "localidad", required = false) String localidad) {

        List<CentroSalud> listaCentros;

        if (nombre == null && localidad == null) {
            listaCentros = centroSaludService.listarCentrosSalud();
        } else if (nombre != null && localidad == null) {
            listaCentros = centroSaludService.buscarCentrosPorNombre(nombre);
        } else if (nombre == null && localidad != null) {
            listaCentros = centroSaludService.buscarCentrosPorLocalidad(localidad);
        } else {
            return ResponseEntity.badRequest().build();
        }

        if (listaCentros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(centroSaludMapper.toListDto(listaCentros));
    }

    // TODO: debe devolver un 201
    @PostMapping("/centroSalud")
    public ResponseEntity<CentroSalud> crearCentro(@RequestBody CentroSalud centroSalud) {
        return ResponseEntity.ok(centroSaludService.crearCentro(centroSalud));
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

    @GetMapping("/medico")
    public ResponseEntity<List<MedicoDto>> listarMedicos(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "localidad", required = false) String localidad,
            @RequestParam(name = "centroSalud", required = false) Long centroSaludId) {

        List<Medico> listaMedicos;

        if (nombre == null && localidad == null && centroSaludId == null) {
            listaMedicos = medicoService.listarMedicos();
        } else if (nombre != null && localidad == null && centroSaludId == null) {
            listaMedicos = medicoService.buscarMedicosPorNombre(nombre);
        } else if (nombre == null && localidad != null && centroSaludId == null) {
            listaMedicos = medicoService.buscarMedicosPorLocalidad(localidad);
        } else if (nombre == null && localidad == null && centroSaludId != null) {
            listaMedicos = medicoService.buscarMedicosPorCentroSalud(centroSaludId);
        } else {
            return ResponseEntity.badRequest().build();
        }

        if(listaMedicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(medicoMapper.toListDto(listaMedicos));
    }

    @PostMapping("/medico")
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        try {
            medicoService.crearMedico(medico);
            return ResponseEntity.ok(medico);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
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

}
