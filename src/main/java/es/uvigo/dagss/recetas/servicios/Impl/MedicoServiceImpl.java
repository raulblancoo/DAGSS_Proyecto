package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.repositorios.MedicoRepository;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return medicoRepository.findByCentroSalud_LocalidadLike(localidad);
    }

    @Override
    public List<Medico> buscarMedicosPorNombre(String nombre) {
        return medicoRepository.findByNombreLike(nombre);
    }

    @Override
    public List<Medico> buscarMedicosPorCentroSalud(Long centroSaludId) {
        return medicoRepository.findByCentroSalud_Id(centroSaludId);
    }

    @Transactional
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

    // TODO: corregir, no funciona
    @Transactional
    @Override
    public Medico editarMedico(Long id, Medico datosMedico) {
        Optional<Medico> medico = medicoRepository.findById(id);

        if(medico.isPresent()) {
            Medico medicoEdit = medico.get();
            medicoEdit.setNombre(datosMedico.getNombre());
            medicoEdit.setApellidos(datosMedico.getApellidos());
            medicoEdit.setDni(datosMedico.getDni());
            medicoEdit.setNumeroColegiado(datosMedico.getNumeroColegiado());
            medicoEdit.setTelefono(datosMedico.getTelefono());
            medicoEdit.setEmail(datosMedico.getEmail());
            medicoEdit.setCentroSalud(datosMedico.getCentroSalud());
            medicoEdit.setPacientes(datosMedico.getPacientes());
            medicoEdit.setRecetas(datosMedico.getRecetas());
            medicoEdit.setCitas(datosMedico.getCitas());
            medicoEdit.setActivo(datosMedico.getActivo());
            medicoEdit.setLogin(datosMedico.getLogin());
            medicoEdit.setPassword(datosMedico.getPassword());

            return medicoRepository.save(medicoEdit);
        } else {
            throw new IllegalArgumentException("El médico no existe");
        }
    }

    @Transactional
    @Override
    public void eliminarMedico(Long id) {
        Medico medicoExistente = medicoRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("No existe el medico con id: " + id));
        medicoExistente.desactivar();
        medicoRepository.save(medicoExistente);
    }
}
