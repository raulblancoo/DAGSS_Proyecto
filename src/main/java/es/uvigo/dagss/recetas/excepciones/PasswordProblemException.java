package es.uvigo.dagss.recetas.excepciones;

public class PasswordProblemException extends RuntimeException {
    public PasswordProblemException(String message) {
        super(message);
    }
}
