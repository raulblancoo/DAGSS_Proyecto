package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.CrearCitaRequest;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.repositorios.CitaRepository;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.MedicoService;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import es.uvigo.dagss.recetas.servicios.PrescripcionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private PrescripcionService prescripcionService;


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
    public List<Cita> listarCitasMedico(String numColegiado) {
        // TODO: hacer que sólo devuelva las citas que tenga el día de hoy
        return citaRepository.findByMedico_NumeroColegiado(numColegiado);
    }

    /* Relacionadas con médico */
    @Override
    public Cita getDetallesCita(Long citaId) {
        return citaRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita con id " + citaId + " no encontrada o no pertenece al médico."));
    }

    @Override
    @Transactional
    public void actualizarEstadoCita(Long citaId, Cita.EstadoCita estado) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita con id " + citaId + " no encontrada o no permitida."));

        cita.setEstado(estado);
        citaRepository.save(cita);
    }

    @Override
    public List<Prescripcion> getPrescripcionesEnCita(Long citaId) {
        Cita cita = getDetallesCita(citaId);
        return prescripcionService.findByPacienteAndEstado(cita.getPaciente().getId(), Prescripcion.Estado.ACTIVO);
    }

    @Override
    public List<Cita> getAgenda(LocalDate fecha, String numColegiado) {
        Medico medico = medicoService.findMedicoByNumColegiado(numColegiado);
        List<Cita> citas = citaRepository.findByMedicoAndFechaHoraInicioBetween(
                medico,
                fecha.atStartOfDay(),
                fecha.plusDays(1).atStartOfDay()
        );

        List<Cita> toret = new ArrayList<>();

        for(Cita cita : citas) {
            if(cita.getEstado().equals(Cita.EstadoCita.PLANIFICADA)) {
                toret.add(cita);
            }
        }

        return toret;
    }


    /* Relacionadas con paciente */
    @Override
    public List<Cita> getCitasFuturas(String numSegSocial) {
        Paciente paciente = pacienteService.findPacienteByNumSeguridadSocial(numSegSocial);
        return citaRepository.findByPacienteAndEstado(paciente, Cita.EstadoCita.PLANIFICADA);
    }

    @Transactional
    @Override
    public void anularCita(Long citaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe ninguna cita con id:" + citaId));

        cita.setEstado(Cita.EstadoCita.ANULADA);
        citaRepository.save(cita);
    }

    @Override
    public List<String> getDisponibilidadCitas(LocalDate fecha, String numSeguridadSocial) {
        // Recuperar el médico del paciente
        Paciente paciente = pacienteService.findPacienteByNumSeguridadSocial(numSeguridadSocial);
        Medico medico = paciente.getMedico();

        // Define el rango de tiempo: 8:30 a 15:30
        LocalDateTime inicio = fecha.atTime(8, 30);
        LocalDateTime fin = fecha.atTime(15, 30);

        // Obtiene todas las citas del médico en ese rango
        List<Cita> citas = citaRepository.findByMedicoAndFechaHoraInicioBetween(medico, inicio, fin);

        // Genera todos los posibles huecos de 15 minutos
        List<String> huecos = new ArrayList<>();
        LocalDateTime current = inicio;
        while (!current.isAfter(fin.minusMinutes(15))) {
            huecos.add(current.toLocalTime().toString());
            current = current.plusMinutes(15);
        }

        // Elimina los huecos que ya están ocupados
        for (Cita cita : citas) {
            if(cita.getEstado().equals(Cita.EstadoCita.PLANIFICADA) || cita.getEstado().equals(Cita.EstadoCita.COMPLETADA)) {
                String hora = cita.getFechaHoraInicio().toLocalTime().toString();
                huecos.remove(hora);
            }
        }

        return huecos;
    }

    @Transactional
    @Override
    public void crearCita(CrearCitaRequest request, String numSegSocial) {
        Paciente paciente = pacienteService.findPacienteByNumSeguridadSocial(numSegSocial);
        Medico medico = paciente.getMedico();

        // Verifica que la cita esté dentro del horario permitido
        LocalTime horaCita = request.getFechaHora().toLocalTime();
        LocalTime inicioHorario = LocalTime.of(8, 30);
        LocalTime finHorario = LocalTime.of(15, 30);

        if (horaCita.isBefore(inicioHorario) || horaCita.plusMinutes(15).isAfter(finHorario)) {
            throw new IllegalArgumentException("La cita debe estar entre las 8:30 y las 15:30.");
        }

        // Verifica que el hueco esté disponible
        List<Cita> citas = citaRepository.findByMedicoAndFechaHoraInicioBetween(medico,
                request.getFechaHora(),
                request.getFechaHora().plusMinutes(15));

        if (!citas.isEmpty()) {
            throw new IllegalArgumentException("El hueco seleccionado no está disponible.");
        }

        // Crea y guarda la nueva cita
        Cita cita = new Cita();
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        cita.setCentroSalud(paciente.getCentroSalud());
        cita.setFechaHoraInicio(request.getFechaHora());
        cita.setDuracionMinutos(15); // Duración fija de 15 minutos
        cita.setEstado(Cita.EstadoCita.PLANIFICADA);

        citaRepository.save(cita);
    }

//    @Transactional
//    @Override
//    public void confirmarCita(Long citaId) {
//
//        Paciente paciente = getCurrentPaciente();
//        Cita cita = citaRepository.findByIdAndPaciente(cita_id, paciente)
//                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada o no pertenece al paciente."));
//
//        if (cita.getEstado() != Cita.Estado.PLANIFICADA) {
//            throw new IllegalArgumentException("Solo se pueden confirmar citas en estado PLANIFICADA.");
//        }
//
//        cita.setEstado(Cita.Estado.COMPLETADA);
//        citaRepository.save(cita);
//    }

    public List<Cita> findByPacienteAndEstado(Paciente paciente, Cita.EstadoCita estado) {
        return citaRepository.findByPacienteAndEstado(paciente, estado);
    }
}
