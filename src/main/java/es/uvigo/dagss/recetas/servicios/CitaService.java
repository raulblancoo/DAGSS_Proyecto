package es.uvigo.dagss.recetas.servicios;
import es.uvigo.dagss.recetas.dtos.CrearCitaRequest;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CitaService {
    List<Cita> buscarCitasConParametros(LocalDate date, Long medicoId, Long pacienteId);

    List<Cita> listarCitasMedico(String numColegiado);

    /* Relacionadas con médico */
    Cita getDetallesCita(Long citaId);
    void actualizarEstadoCita(Long citaId, Cita.EstadoCita estado);
    List<Prescripcion> getPrescripcionesEnCita(Long citaId);
    List<Cita> getAgenda(LocalDate fecha, String numColegiado);

    /* Relacionadas con paciente */
    List<Cita> getCitasFuturas(String numSegSocial);
    void anularCita(Long citaId);
    List<String> getDisponibilidadCitas(LocalDate fecha, String numSegSocial);
    void crearCita(CrearCitaRequest request, String numSegSocial);
}

