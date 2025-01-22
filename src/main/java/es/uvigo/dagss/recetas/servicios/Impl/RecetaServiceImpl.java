package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.repositorios.RecetaRepository;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import es.uvigo.dagss.recetas.servicios.RecetaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RecetaServiceImpl implements RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;
    @Autowired
    private PacienteService pacienteService;

    @Override
    public List<Receta> buscarPlanesPorPrescripcionId(Long prescripcionId) {
        return recetaRepository.findByPrescripcion_IdAndEstadoIn(prescripcionId, Arrays.asList(Receta.Estado.SERVIDA, Receta.Estado.PLANIFICADA));
    }

    @Override
    public List<Receta> buscarRecetasPorTarjetaSanitaria(String tarjetaSanitaria) {
        return recetaRepository.findByPrescripcion_Paciente_TarjetaSanitariaAndEstado(tarjetaSanitaria, Receta.Estado.PLANIFICADA);
    }

    @Override
    public List<Receta> buscarRecetasPendientes(String numSegSocial) {
        Paciente paciente = pacienteService.findPacienteByNumSeguridadSocial(numSegSocial);
        return recetaRepository.findByPrescripcion_PacienteAndEstado(paciente, Receta.Estado.PLANIFICADA);
    }

    @Transactional
    @Override
    public void anularRecetaAsociada(Long prescripcionId) {
        List<Receta> recetas = recetaRepository.findByPrescripcion_Id(prescripcionId);

        if(recetas.isEmpty()){
            throw new ResourceNotFoundException("No hay recetas asociadas a la prescripcion " + prescripcionId);
        }

        for (Receta receta : recetas) {
            receta.setEstado(Receta.Estado.ANULADA);
            recetaRepository.save(receta);
        }
    }
}
