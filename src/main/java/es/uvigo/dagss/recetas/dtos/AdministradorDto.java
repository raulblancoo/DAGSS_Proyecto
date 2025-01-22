package es.uvigo.dagss.recetas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorDto {
    private String login;
    private String nombre;
    private String email;
    private Date fechaCreacion;
    private boolean activo;
}
