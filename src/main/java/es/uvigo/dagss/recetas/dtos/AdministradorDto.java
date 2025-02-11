package es.uvigo.dagss.recetas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorDto {
    private String login;
    private String nombre;
    private String email;
    private String fechaCreacion;
    private boolean activo;
}
