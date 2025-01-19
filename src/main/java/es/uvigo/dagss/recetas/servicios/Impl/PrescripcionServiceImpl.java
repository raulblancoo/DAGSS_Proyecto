package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.CrearPrescripcionRequest;
import es.uvigo.dagss.recetas.entidades.*;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.repositorios.RecetaRepository;
import es.uvigo.dagss.recetas.repositorios.PrescripcionRepository;
import es.uvigo.dagss.recetas.servicios.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrescripcionServiceImpl implements PrescripcionService {

    @Autowired
    private PrescripcionRepository prescripcionRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private MedicamentoService medicamentoService;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private RecetaService recetaService;
    @Autowired
    private RecetaRepository recetaRepository;


    @Override
    @Transactional
    public void crearPrescripcion(CrearPrescripcionRequest request) {
        if (medicoService.existsMedicoById(request.getMedicoId()) && pacienteService.existsPacienteById(request.getPacienteId()) &&
                medicamentoService.existsMedicamentoById(request.getMedicamentoId())) {
            Medico medico = medicoService.findMedicoById(request.getMedicoId());
            Paciente paciente = pacienteService.findPacienteById(request.getPacienteId());
            Medicamento medicamento = medicamentoService.findMedicamentoById(request.getMedicamentoId());

            Prescripcion prescripcion = new Prescripcion();
            prescripcion.setMedicamento(medicamento);
            prescripcion.setPaciente(paciente);
            prescripcion.setMedico(medico);
            prescripcion.setDosisDiaria(request.getDosisDiaria());
            prescripcion.setIndicaciones(request.getIndicaciones());
            prescripcion.setFechaInicio(LocalDate.now());
            prescripcion.setFechaFin(request.getFechaFin());
            prescripcion.setEstado(Prescripcion.Estado.ACTIVO);

            prescripcionRepository.save(prescripcion);

            // Generar plan de recetas automáticamente
            generarPlanRecetas(prescripcion);

        } else {
            throw new ResourceNotFoundException("medico= " +  medicoService.existsMedicoById(request.getMedicoId()) + "paciente=" + pacienteService.existsPacienteById(request.getPacienteId()) + "medicamento=" + medicamentoService.existsMedicamentoById(request.getMedicamentoId()) );
        }
    }

    @Override
    public List<Prescripcion> findByPacienteAndEstado(Long pacienteId, Prescripcion.Estado estado) {
        return prescripcionRepository.findByPaciente_IdAndEstado(pacienteId, estado);
    }

    @Transactional
    @Override
    public void actualizarEstadoPrescripcion(Long prescripcionId, Prescripcion.Estado estado) {
        Optional<Prescripcion> prescripcion = prescripcionRepository.findById(prescripcionId);

        if(prescripcion.isPresent()) {
            prescripcion.get().setEstado(estado);
            recetaService.anularRecetaAsociada(prescripcionId);
            prescripcionRepository.save(prescripcion.get());
        } else {
            throw new ResourceNotFoundException("Prescripcion con id " + prescripcionId + " no encontrada");
        }

    }

    // TODO: Mirar como generamos los planes de receta
    @Transactional
    public void generarPlanRecetas(Prescripcion prescripcion) {
        // Lógica para generar el plan de recetas
        // Este método debe calcular las fechas de validez de las recetas y crearlas

        // Ejemplo simplificado:
        LocalDate fechaInicio = prescripcion.getFechaInicio();
        LocalDate fechaFin = prescripcion.getFechaFin();
        Double dosisDiaria = prescripcion.getDosisDiaria();
        Integer numeroDosisEnvase = prescripcion.getMedicamento().getNumeroDosis();

        // Cálculo simplificado: una receta por semana
        LocalDate fechaActual = fechaInicio;
        while (!fechaActual.isAfter(fechaFin)) {
            Receta receta = new Receta();
            receta.setPrescripcion(prescripcion);
            receta.setFechaValidezInicial(fechaActual);
            receta.setFechaValidezFinal(fechaActual.plusWeeks(1).minusDays(1));
            receta.setNumeroUnidades(1); // Asumiendo una caja por receta
            receta.setEstado(Receta.Estado.PLANIFICADA);
            // Farmacia inicialmente nula
            recetaRepository.save(receta);
            fechaActual = fechaActual.plusWeeks(1);
        }
    }
}
