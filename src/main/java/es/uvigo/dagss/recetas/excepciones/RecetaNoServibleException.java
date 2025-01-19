package es.uvigo.dagss.recetas.excepciones;

public class RecetaNoServibleException extends RuntimeException {
    public RecetaNoServibleException(String mensaje) {
        super(mensaje);
    }
}
