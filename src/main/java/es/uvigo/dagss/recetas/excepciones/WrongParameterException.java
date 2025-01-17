package es.uvigo.dagss.recetas.excepciones;

public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String message) {
        super(message);
    }
}
