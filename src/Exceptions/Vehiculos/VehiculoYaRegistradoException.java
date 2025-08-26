package Exceptions.Vehiculos;

public class VehiculoYaRegistradoException extends RuntimeException {
    public VehiculoYaRegistradoException() {
        super();
    }
    public VehiculoYaRegistradoException(String message) {
        super(message);
    }
}
