package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.CitaDto;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.repositorios.CitaRepository;
import es.uvigo.dagss.recetas.servicios.CitaService;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final PacienteService pacienteService;
    private final CitaRepository citaRepository;

    public CitaServiceImpl(PacienteService pacienteService, CitaRepository citaRepository) {
        this.pacienteService = pacienteService;
        this.citaRepository = citaRepository;
    }


    // TODO: cambiarlo
    @Override
    public List<Cita> buscarCitasActivasPorCliente(Long pacienteId) {
        return null;
    }

}
