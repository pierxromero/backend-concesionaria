package Exceptions.Compras;

public class CompraNoEncontradaException extends RuntimeException {
    public CompraNoEncontradaException(String message) {
        super(message);
    }
}
