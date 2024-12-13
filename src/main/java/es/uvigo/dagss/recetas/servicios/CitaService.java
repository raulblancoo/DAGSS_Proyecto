package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.CitaDto;
import es.uvigo.dagss.recetas.entidades.Cita;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CitaService {
    List<Cita> buscarCitasActivasPorCliente(Long pacienteId);
}
