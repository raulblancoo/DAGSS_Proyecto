package es.uvigo.dagss.recetas.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PrescripcionDto {
    private String medicamento;
    private String dosisDiaria;
    private Date fechaInicio;
    private Date fechaFin;
    private String indicaciones;
}
