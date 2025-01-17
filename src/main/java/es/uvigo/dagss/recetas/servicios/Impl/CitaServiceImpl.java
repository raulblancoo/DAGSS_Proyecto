package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.repositorios.CitaRepository;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final PacienteService pacienteService;
    private final CitaRepository citaRepository;

    public CitaServiceImpl(PacienteService pacienteService, CitaRepository citaRepository) {
        this.pacienteService = pacienteService;
        this.citaRepository = citaRepository;
    }

    @Override
    public List<Cita> listarCitas(LocalDate date, Long medicoId, Long pacienteId) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        Sort sort = Sort.by(Sort.Direction.ASC, "fechaHoraInicio");

        List<Cita> citas;

        if (medicoId != null && pacienteId != null) {
            citas = citaRepository.findByFechaHoraInicioBetweenAndMedicoIdAndPacienteId(startOfDay, endOfDay, medicoId, pacienteId, sort);
        } else if (medicoId != null) {
            citas = citaRepository.findByFechaHoraInicioBetweenAndMedicoId(startOfDay, endOfDay, medicoId, sort);
        } else if (pacienteId != null) {
            citas = citaRepository.findByFechaHoraInicioBetweenAndPacienteId(startOfDay, endOfDay, pacienteId, sort);
        } else {
            citas = citaRepository.findByFechaHoraInicioBetween(startOfDay, endOfDay, sort);
        }

        return citas;
    }

    @Override
    public void anularCita(Long citaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe ninguna cita con id:" + citaId));

        cita.setEstado(Cita.EstadoCita.ANULADA);
        citaRepository.save(cita);
    }
}
