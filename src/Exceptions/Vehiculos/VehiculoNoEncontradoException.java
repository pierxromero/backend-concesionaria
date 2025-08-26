package Exceptions.Vehiculos;

public class VehiculoNoEncontradoException extends RuntimeException {
    public VehiculoNoEncontradoException() {
        super();
    }
    public VehiculoNoEncontradoException(String message) {
        super(message);
    }
}
