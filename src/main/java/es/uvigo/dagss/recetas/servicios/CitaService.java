package es.uvigo.dagss.recetas.servicios;
import es.uvigo.dagss.recetas.entidades.Cita;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CitaService {
    List<Cita> listarCitas(LocalDate date, Long medicoId, Long pacienteId);
    void anularCita(Long citaId);
}
