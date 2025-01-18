package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearPrescripcionRequest {
    private Long medicamentoId;
    private Long pacienteId;
    private Long medicoId;
    private Double dosisDiaria;
    private String indicaciones;
    private LocalDate fechaFin;
    private Prescripcion.Estado estado;

}