package es.uvigo.dagss.recetas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicamentoDto {
    private String nombreComercial;
    private String principioActivo;
    private String fabricante;
    private String familia;
}
