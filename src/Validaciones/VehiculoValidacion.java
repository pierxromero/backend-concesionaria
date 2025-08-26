package Validaciones;

import Enums.TipoDeObject;
import Enums.Vehiculos.TipoDeCarga;
import Enums.Vehiculos.TipoDeCombustible;
import Enums.Vehiculos.TipoDeMoto;
import Exceptions.FormatoInvalidoException;

import java.time.Year;

public class VehiculoValidacion {
    /* Validaciones para vehiculo */
    public static Boolean validarSiONoBoolean(String input) throws FormatoInvalidoException {
        // Verificar si la entrada es null o vacía
        if (input == null || input.trim().isEmpty()) {
            throw new FormatoInvalidoException("La entrada no puede estar vacía.");
        }

        String trimmedInput = input.trim().toLowerCase(); // Eliminar espacios y convertir a minúsculas

        // Si la entrada es "si", devolver true
        if (trimmedInput.equals("si")) {
            return true; // Devuelve true
        }
        // Si la entrada es "no", devolver false
        else if (trimmedInput.equals("no")) {
            return false; // Devuelve false
        } else {
            throw new FormatoInvalidoException("Entrada inválida. Solo se permiten los valores 'si' o 'no'.");
        }
    }
    public static TipoDeCarga validarTipoDeCarga(String input) throws FormatoInvalidoException {
        // Eliminar espacios en blanco al inicio y al final
        String tipo = input.trim().toLowerCase();

        // Verificar y devolver el enum correspondiente
        if (tipo.equals("liviana")) {
            return TipoDeCarga.LIVIANA;
        } else if (tipo.equals("pesada")) {
            return TipoDeCarga.PESADA;
        } else if (tipo.equals("construccion")) {
            return TipoDeCarga.CONSTRUCCION;
        } else if (tipo.equals("alimentaria")) {
            return TipoDeCarga.ALIMENTARIA;
        } else if (tipo.equals("peligrosa")) {
            return TipoDeCarga.PELIGROSA;
        } else if (tipo.equals("materiales fragiles")) {
            return TipoDeCarga.MATERIALES_FRAGILES;
        } else {
            throw new FormatoInvalidoException(
                    "Tipo de carga inválido. Solo se permiten: 'Liviana', 'Pesada', 'Construcción', 'Alimentaria', 'Peligrosa', o 'Materiales Frágiles'."
            );
        }
    }
    public static Double validarLongitud(String input) throws FormatoInvalidoException {
        try {
            Double longitud = Double.parseDouble(input.trim());

            // Verificar que la longitud esté en el rango permitido
            if (longitud < 12.0 || longitud > 20.0) {
                throw new FormatoInvalidoException("La longitud del camión debe estar entre 12.0 m y 20.0 m.");
            }

            return longitud;
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("La longitud debe ser un número decimal válido.");
        }
    }
    public static Integer validarCapacidadCargaCamion(String input) throws FormatoInvalidoException {
        try {
            Integer capacidad = Integer.parseInt(input.trim());

            // Verificar que la capacidad esté en el rango permitido
            if (capacidad < 1000 || capacidad > 30000) {
                throw new FormatoInvalidoException("La capacidad de carga del camión debe estar entre 1000 kg y 30000 kg.");
            }

            return capacidad;
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("La capacidad de carga debe ser un número entero válido.");
        }
    }
    public static Integer validarCapacidadCargaMoto(String input) throws FormatoInvalidoException {
        try {
            Integer capacidad = Integer.parseInt(input.trim());

            // Verificar que la capacidad de carga esté entre 1 y 150 kg
            if (capacidad <= 0 || capacidad > 150) {
                throw new FormatoInvalidoException("La capacidad de carga debe ser un número positivo mayor a cero y no puede exceder los 150 kg.");
            }

            return capacidad;
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("La capacidad de carga debe ser un número entero válido.");
        }
    }
    public static TipoDeMoto validarTipoDeMoto(String input) throws FormatoInvalidoException {
        // Eliminar espacios en blanco al inicio y al final
        String tipo = input.trim().toLowerCase();

        // Verificar si el tipo es uno de los tres permitidos
        if (tipo.equals("deportiva")) {
            return TipoDeMoto.DEPORTIVA;
        } else if (tipo.equals("clasica")) {
            return TipoDeMoto.CLASICA;
        } else if (tipo.equals("electrica")) {
            return TipoDeMoto.ELECTRICA;
        } else {
            throw new FormatoInvalidoException("Tipo de moto inválido. Solo se permiten: 'Deportiva', 'Clasica' o 'Electrica'.");
        }
    }
    public static String validarModelo(String input) throws FormatoInvalidoException {
        String modelo = input.trim();

        // Verificar si el modelo no está vacío
        if (modelo.isEmpty()) {
            throw new FormatoInvalidoException("El modelo no puede estar vacío.");
        }

        // Verificar si el modelo contiene solo caracteres alfabéticos y números
        if (!modelo.matches("[A-Za-z0-9 ]+")) {
            throw new FormatoInvalidoException("El modelo solo puede contener letras, números y espacios.");
        }

        // Si pasa todas las validaciones, devolver el modelo
        return modelo;
    }
    public static Integer validarCantidadDePuertas(String input) throws FormatoInvalidoException {
        try {
            // Verificar si el valor es un número
            Integer cantidad = Integer.parseInt(input.trim());

            // Validar si está en el rango permitido (2 a 5 puertas)
            if (cantidad >= 2 && cantidad <= 5) {
                return cantidad;
            } else {
                throw new FormatoInvalidoException("Cantidad de puertas no válida. Debe ser entre 2 y 5.");
            }
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Cantidad de puertas debe ser un número válido.");
        }
    }
    public static Boolean validarBooleanStr(String input) throws FormatoInvalidoException {
        String respuesta = input.trim().toLowerCase();

        if (respuesta.equals("si")) {
            return true;
        } else if (respuesta.equals("no")) {
            return false;
        } else {
            throw new FormatoInvalidoException("Respuesta inválida. Ingrese 'si' o 'no'.");
        }
    }
    public static String validarColor(String input) throws FormatoInvalidoException {
        // Lista de colores permitidos
        String[] coloresPermitidos = {"rojo", "azul", "verde", "negro", "blanco", "gris", "amarillo", "naranja"};
        String color = input.trim().toLowerCase();

        // Verificar si el color ingresado está en la lista de colores permitidos
        for (String c : coloresPermitidos) {
            if (color.equals(c)) {
                return color; // Color válido
            }
        }
        throw new FormatoInvalidoException("Color no válido. Debe ser uno de los siguientes: rojo, azul, verde, negro, blanco, gris, amarillo, naranja.");
    }
    public static Integer validarCapacidadCombustible(String input) throws FormatoInvalidoException {
        // Eliminar espacios en blanco al inicio y al final
        String capacidadStr = input.trim();

        // Verificar si la entrada es numérica y dentro del rango
        try {
            Integer capacidad = Integer.parseInt(capacidadStr);

            // Verificar si el valor es un número positivo dentro del rango
            if (capacidad < 10 || capacidad > 200) {
                throw new FormatoInvalidoException("La capacidad de combustible debe estar entre 10 y 200 litros.");
            }

            return capacidad;
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("Entrada inválida. Se debe ingresar un número entero positivo.");
        }
    }
    public static Integer validarKilometraje(String input) throws FormatoInvalidoException {
        //Eliminar espacios al principio y al final
        String kilometrajeStr = input.trim();
        //Verificar si es un número entero
        try {
            Integer kilometraje = Integer.parseInt(kilometrajeStr);

            // Validar que el kilometraje esté en el rango aceptable (entre 0 y 5,000,000 km)
            if (kilometraje < 0 || kilometraje > 300000) {
                throw new FormatoInvalidoException("El kilometraje debe estar entre 0 y 300.000 km.");
            }

            // Retornar el kilometraje
            return kilometraje;
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("El kilometraje debe ser un número entero válido.");
        }
    }
    public static Year validarAnio(String input) throws FormatoInvalidoException {
        // Eliminar espacios en blanco al principio y al final
        String anioStr = input.trim();

        // Verificar si es un número entero
        try {
            int anio = Integer.parseInt(anioStr);

            // Verificar que el año esté dentro del rango válido (por ejemplo, entre 1900 y el año actual)
            int currentYear = Year.now().getValue();
            if (anio < 1900 || anio > currentYear) {
                throw new FormatoInvalidoException("El año debe ser entre 1900 y el año actual.");
            }

            // Retornar el año como un objeto Year
            return Year.of(anio);
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("El año debe ser un número válido.");
        }
    }
    public static Double validarPrecio(String input) throws FormatoInvalidoException {
        // Eliminar espacios al principio y al final
        String precioStr = input.trim();

        // Expresión regular para validar el formato correcto (número con hasta 2 decimales)
        String regex = "^[0-9]+(\\.[0-9]{1,2})?$";

        if (!precioStr.matches(regex)) {
            throw new FormatoInvalidoException("El precio debe ser un número con hasta 2 decimales.");
        }

        // Intentar convertir a Double
        try {
            return Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            throw new FormatoInvalidoException("El precio ingresado no es válido.");
        }
    }
    public static TipoDeCombustible validarTipoCombustible(String input) throws FormatoInvalidoException {
        // Eliminar espacios en blanco al inicio y al final
        String tipo = input.trim().toLowerCase();

        // Verificar si el tipo es uno de los cinco permitidos
        switch (tipo) {
            case "gasolina":
                return TipoDeCombustible.GASOLINA;
            case "diesel":
                return TipoDeCombustible.DIESEL;
            case "electrico":
                return TipoDeCombustible.ELECTRICO;
            case "hibrido":
                return TipoDeCombustible.HIBRIDO;
            case "biocombustible":
                return TipoDeCombustible.BIOCOMBUSTIBLE;
            default:
                throw new FormatoInvalidoException("Tipo de combustible inválido. Solo se permiten: 'Gasolina', 'Diesel', 'Electrico', 'Hibrido', o 'Biocombustible'.");
        }
    }
    public static TipoDeObject validarTipoVehiculo(String input) throws FormatoInvalidoException {
        // Eliminar espacios en blanco al inicio y al final
        String tipo = input.trim().toLowerCase();

        // Verificar si el tipo es uno de los tres permitidos
        switch (tipo) {
            case "auto":
                return TipoDeObject.AUTO;
            case "moto":
                return TipoDeObject.MOTO;
            case "camion":
                return TipoDeObject.CAMION;
            default:
                throw new FormatoInvalidoException("Tipo de vehículo inválido. Solo se permiten: 'Auto', 'Moto' o 'Camion'.");
        }
    }
    public static void validarMatricula(String matricula) throws FormatoInvalidoException {
        //Quitar espacios en blanco al inicio y al final
        String trimmedMatricula = matricula.trim();
        // Verificar que la cadena no esté vacía
        if (trimmedMatricula.isEmpty()) {
            throw new FormatoInvalidoException("La matrícula no puede estar vacía.");
        }
        // Verificar que cumpla con el formato exacto: 3 letras seguidas de 3 números
        if (!trimmedMatricula.matches("[a-zA-Z]{3}\\d{3}")) {
            throw new FormatoInvalidoException("Formato inválido de matrícula. Debe ser del tipo 'ABC123'.");
        }
    }
    public static void validarMarcaVoid(String marca) throws FormatoInvalidoException {
        //Quitar espacios al inicio y al final
        String trimmedMarca = marca.trim();
        //Verificar que no esté vacío después de limpiar espacios
        if (trimmedMarca.isEmpty()) {
            throw new FormatoInvalidoException("La marca no puede estar vacía.");
        }
        //Verificar que solo contenga letras y espacios entre palabras
        if (!trimmedMarca.matches("[a-zA-Z]+( [a-zA-Z]+)*")) {
            throw new FormatoInvalidoException("La marca solo puede contener letras y espacios entre palabras.");
        }
    }
}
