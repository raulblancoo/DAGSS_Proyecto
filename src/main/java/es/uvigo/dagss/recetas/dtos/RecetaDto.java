package es.uvigo.dagss.recetas.dtos;

import es.uvigo.dagss.recetas.entidades.Receta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecetaDto {
    private String nombre_medicamento;
    private String nombre_medico;
    private LocalDate  fecha_inicio;
    private LocalDate fecha_fin;
    private Receta.EstadoReceta estado;
}
