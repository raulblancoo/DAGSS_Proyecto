package es.uvigo.dagss.recetas.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class RecetaDto {
    private String medicamento;
    private Date fechaValidezInicial;
    private Date fechaValidezFinal;
    private double numeroUnidades;
}
