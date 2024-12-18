package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.repositorios.MedicoRepository;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    @Override
    public List<Medico> buscarMedicosPorLocalidad(String localidad) {
        // TODO
        return List.of();
    }

    @Override
    public List<Medico> buscarMedicosPorNombre(String nombre) {
        return medicoRepository.findByNombreLike(nombre);
    }

    @Override
    public List<Medico> buscarMedicosPorCentroSalud(String nombreCentroSalud) {
//        return medicoRepository.findByCentroSalud(nombreCentroSalud);
        return List.of();
    }

    @Override
    public Medico crearMedico(Medico medico) {
        if(medicoRepository.existsByEmail(medico.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }

        Medico newMedico = new Medico(
                medico.getLogin(), medico.getNumeroColegiado(),
                medico.getNombre(), medico.getApellidos(), medico.getDni(),
                medico.getNumeroColegiado(), medico.getTelefono(), medico.getEmail(), medico.getCentroSalud()
        );

        return medicoRepository.save(newMedico);
    }


    @Override
    public Medico editarMedico(Long id, Medico medicamento) {
        // TODO
        return null;
    }

    @Override
    public void eliminarMedico(Long id) {
        Medico medicoExistente = medicoRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("No existe el medico con id: " + id));
        medicoExistente.desactivar();
        medicoRepository.save(medicoExistente);
    }
}
