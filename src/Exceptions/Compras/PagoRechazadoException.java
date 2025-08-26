package Exceptions.Compras;

public class PagoRechazadoException extends RuntimeException {
    public PagoRechazadoException(String message) {
        super(message);
    }
}
