package es.uvigo.dagss.recetas.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Date;

@Data
public class RecetaDto {
    @JsonView({Vistas.VistaRecetaMedico.class,Vistas.VistaRecetaPaciente.class})
    private String medicamento;
    @JsonView(Vistas.VistaRecetaPaciente.class)
    private String medico;
    @JsonView({Vistas.VistaRecetaMedico.class,Vistas.VistaRecetaPaciente.class})
    private Date fechaValidezInicial;
    @JsonView({Vistas.VistaRecetaMedico.class,Vistas.VistaRecetaPaciente.class})
    private Date fechaValidezFinal;
    @JsonView({Vistas.VistaRecetaMedico.class,Vistas.VistaRecetaPaciente.class})
    private double numeroUnidades;
}
