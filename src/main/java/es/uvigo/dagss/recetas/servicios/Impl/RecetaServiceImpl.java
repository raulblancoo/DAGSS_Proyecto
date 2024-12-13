package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.mappers.RecetaMapper;
import es.uvigo.dagss.recetas.repositorios.RecetaRepository;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import es.uvigo.dagss.recetas.servicios.PacienteService;
import es.uvigo.dagss.recetas.servicios.RecetaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecetaServiceImpl implements RecetaService {
    private final RecetaMapper recetaMapper;
    private final RecetaRepository recetaRepository;
    private final PacienteService pacienteService;
    private final FarmaciaService farmaciaService;

    public RecetaServiceImpl(RecetaMapper recetaMapper, RecetaRepository recetaRepository, PacienteService pacienteService, FarmaciaService farmaciaService) {
        this.recetaMapper = recetaMapper;
        this.recetaRepository = recetaRepository;
        this.pacienteService = pacienteService;
        this.farmaciaService = farmaciaService;
    }


    /**
     * HU-F2. [Farmacia] Consulta de recetas de un paciente
     *
     * @param tarjetaSanitaria
     * @return Lista de recetas que este estén en vigor para ese paciente
     */
    @Override
    public List<RecetaDto> buscarRecetasActivasPorTarjetaSanitaria(String tarjetaSanitaria) {
        // TODO: Gestionar excepciones cuando la tarjetaSanitaria incluída no es correcta (paciente vacío)
        Paciente paciente = pacienteService.findByTarjetaSanitaria(tarjetaSanitaria);
        return recetaRepository.findRecetasActivasPorPaciente(paciente, LocalDate.now());
    }

    /**
     * HU-F3. [Farmacia] Anotación de recetas DISPENSADA
     * @param recetaId
     * @param farmaciaId
     * @return Receta guardada en BD
     */
    @Override
    // TODO: Comprobar si tiene que devolver una Receta (puede devolver void)
    public Receta anotarRecetaServida(Long recetaId, Long farmaciaId) {
        Receta receta = recetaRepository.findRecetaPlanificadaValida(recetaId, LocalDate.now())
                .orElseThrow(() -> new IllegalArgumentException("Receta no válida o fuera de periodo de validez"));

        // TODO: La farmacia que viene puede ser vacía (hay que comprobar de alguna manera)
        Farmacia farmacia = farmaciaService.findFarmaciaById(farmaciaId);

        receta.setEstado(Receta.EstadoReceta.SERVIDA);
        receta.setFarmacia(farmacia);

        return recetaRepository.save(receta);
    }




}
