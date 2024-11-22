package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Usuario {

	// Anadir atributos propios
   

    public Paciente() {
        super(TipoUsuario.PACIENTE);        
    }

}
