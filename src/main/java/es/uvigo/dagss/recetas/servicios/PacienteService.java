package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.stereotype.Service;

@Service
public interface PacienteService {
    Paciente findByTarjetaSanitaria(String tarjetaSanitaria);
}
