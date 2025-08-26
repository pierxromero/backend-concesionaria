package Exceptions.Personas;

import java.util.InputMismatchException;

public class DniInvalidoException extends InputMismatchException {
    public DniInvalidoException(String message) {
        super(message);
    }
}
