package Exceptions.Compras;

public class MetodoPagoInvalidoException extends RuntimeException {
  public MetodoPagoInvalidoException() {
  }

  public MetodoPagoInvalidoException(String message) {
    super(message);
  }
}

