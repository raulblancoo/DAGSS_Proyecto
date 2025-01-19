package es.uvigo.dagss.recetas.dtos;

public class FieldErrorDto {
    private String field;
    private String message;

    // Constructor por defecto
    public FieldErrorDto() {
    }

    // Constructor con parámetros
    public FieldErrorDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // Getters y Setters
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
