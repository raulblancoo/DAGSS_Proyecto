package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.repositorios.PacienteRepository;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente findByTarjetaSanitaria(String tarjetaSanitaria) {
        return pacienteRepository.findByTarjetaSanitaria(tarjetaSanitaria);
    }
}
