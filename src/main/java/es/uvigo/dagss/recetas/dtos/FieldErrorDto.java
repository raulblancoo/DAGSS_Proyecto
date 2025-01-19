package es.uvigo.dagss.recetas.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorDto {
    private String field;
    private String message;
}
