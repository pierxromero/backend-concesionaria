package Exceptions.Personas;

public class PersonaNoEncontradaException extends RuntimeException {
    public PersonaNoEncontradaException() {
        super();
    }
    public PersonaNoEncontradaException(String message) {
        super(message);
    }
}
