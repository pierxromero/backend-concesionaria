package Exceptions;

public class ListadoNoDisponibleException extends RuntimeException {
    public ListadoNoDisponibleException() {
        super();
    }
    public ListadoNoDisponibleException(String message) {
        super(message);
    }
}
