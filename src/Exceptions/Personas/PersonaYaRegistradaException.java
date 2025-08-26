package Exceptions.Personas;

public class PersonaYaRegistradaException extends RuntimeException {
    public PersonaYaRegistradaException() {
        super();
    }
    public PersonaYaRegistradaException(String message) {
        super(message);
    }
}
