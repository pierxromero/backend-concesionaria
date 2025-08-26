package Validaciones;

import Enums.Personas.Genero;
import Enums.Personas.Sector;
import Enums.TipoDeObject;
import Exceptions.FormatoInvalidoException;

import static Validaciones.VehiculoValidacion.validarSiONoBoolean;

public class PersonaValidacion {
    public static String validarDNI(String input) throws FormatoInvalidoException {
        // Eliminar espacios al inicio y al final
        String dni = input.trim();

        // Validar que el DNI no esté vacío
        if (dni.isEmpty()) {
            throw new FormatoInvalidoException("El DNI no puede estar vacío.");
        }

        // Validar que tenga exactamente 8 dígitos
        if (!dni.matches("\\d{8}")) {
            throw new FormatoInvalidoException("El DNI debe contener exactamente 8 dígitos.");
        }

        return dni;
    }
        public static Boolean validarEstado(String input) throws FormatoInvalidoException {
        // Eliminar espacios al inicio y al final y convertir a minúsculas
        String estadoStr = input.trim().toLowerCase();

        // Verificar si la entrada es "si" o "no"
        if (estadoStr.equals("si")) {
            return true; // Devuelve true si la entrada es "si"
        } else if (estadoStr.equals("no")) {
            return false; // Devuelve false si la entrada es "no"
        } else {
            // Si no es "si" ni "no", lanzar excepción
            throw new FormatoInvalidoException("El estado debe ser 'si' o 'no'.");
        }
    }
    public static String validarNombre(String input) throws FormatoInvalidoException {
        // Eliminar espacios al inicio y al final
        String nombre = input.trim();

        // Verificar si el nombre está vacío o contiene caracteres no permitidos
        if (nombre.isEmpty() || !nombre.matches("[a-zA-Z]+")) {
            throw new FormatoInvalidoException("El nombre debe contener solo letras y no puede estar vacío.");
        }

        // Convertir la primera letra a mayúscula y el resto a minúsculas
        nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();

        return nombre;
    }
    public static String validarApellido(String input) throws FormatoInvalidoException {
        // Eliminar espacios al inicio y al final
        String apellido = input.trim();

        // Verificar si el apellido está vacío o contiene caracteres no permitidos
        if (apellido.isEmpty() || !apellido.matches("[a-zA-Z]+")) {
            throw new FormatoInvalidoException("El apellido debe contener solo letras y no puede estar vacío.");
        }

        // Convertir la primera letra a mayúscula y el resto a minúsculas
        apellido = apellido.substring(0, 1).toUpperCase() + apellido.substring(1).toLowerCase();

        return apellido;
    }
    public static String validarDireccion(String input) throws FormatoInvalidoException {
        // Eliminar espacios al inicio y al final
        String direccion = input.trim();

        // Verificar si la dirección está vacía
        if (direccion.isEmpty()) {
            throw new FormatoInvalidoException("La dirección no puede estar vacía.");
        }

        // Validar que la dirección contenga una palabra seguida de 1 a 4 números
        if (!direccion.matches("^[a-zA-Z]+\\s\\d{1,4}$")) {
            throw new FormatoInvalidoException("La dirección debe consistir en una palabra seguida de 1 a 4 números.");
        }

        return direccion;
    }

    public static Genero validarGenero(String input) throws FormatoInvalidoException {
        String generoStr = input.trim().toLowerCase();
        switch (generoStr) {
            case "masculino":
                return Genero.MASCULINO;
            case "femenino":
                return Genero.FEMENINO;
            case "otro":
                return Genero.OTRO;
            default:
                throw new FormatoInvalidoException("Género inválido. Solo se permiten: 'Masculino', 'Femenino', 'Otro'.");
        }
    }
    public static Sector validarSector(String input) throws FormatoInvalidoException {
        String sectorStr = input.trim().toLowerCase();
        switch (sectorStr) {
              case "ventas":
                return Sector.VENTAS;
              case "administracion":
                return Sector.ADMINISTRACION;
                case "taller":
                return Sector.TALLER;
            case "contabilidad":
                return Sector.CONTABILIDAD;
            default:
                throw new FormatoInvalidoException("Sector inválido. Solo se permiten: 'Publico', 'Privado'.");
        }
    }

    public static TipoDeObject validarTipoPersona(String input) throws FormatoInvalidoException {
        String tipoStr = input.trim().toLowerCase();
        switch (tipoStr) {
            case "empleado":
                return TipoDeObject.EMPLEADO;
            case "cliente":
                return TipoDeObject.CLIENTE;
            default:
                throw new FormatoInvalidoException("Tipo de persona inválido. Solo se permiten: 'Empleado' o 'Cliente'.");
        }
    }
    ////////////////////// Validacion Empleado
    public static Double validarSueldoBase(String input) throws FormatoInvalidoException {
        // Eliminar espacios al inicio y al final
        String sueldoBaseStr = input.trim();

        // Verificar si la entrada está vacía
        if (sueldoBaseStr.isEmpty()) {
            throw new FormatoInvalidoException("El sueldo base no puede estar vacío.");
        }

        try {
            // Intentar convertir el valor a Double
            Double sueldoBase = Double.parseDouble(sueldoBaseStr);

            // Verificar que el sueldo base sea positivo
            if (sueldoBase <= 0) {
                throw new FormatoInvalidoException("El sueldo base debe ser un número positivo.");
            }

            return sueldoBase;
        } catch (NumberFormatException e) {
            // Si no es un número válido
            throw new FormatoInvalidoException("El sueldo base debe ser un número válido.");
        }
    }

    public static Integer validarAntiguedad(String input) throws FormatoInvalidoException {
        // Eliminar espacios al principio y al final
        String antiguedadStr = input.trim();

        // Verificar si la entrada está vacía
        if (antiguedadStr.isEmpty()) {
            throw new FormatoInvalidoException("La antigüedad no puede estar vacía.");
        }

        try {
            // Intentar convertir el valor a Integer
            Integer antiguedad = Integer.parseInt(antiguedadStr);

            // Verificar que la antigüedad no sea negativa
            if (antiguedad < 0) {
                throw new FormatoInvalidoException("La antigüedad no puede ser negativa.");
            }

            return antiguedad;
        } catch (NumberFormatException e) {
            // Si no es un número válido
            throw new FormatoInvalidoException("La antigüedad debe ser un número válido.");
        }
    }

    public static String validarContrasenaPersona(String input) throws FormatoInvalidoException {
        // Eliminar espacios al principio y al final
        String contrasena = input.trim();

        // Verificar si la contraseña está vacía o solo contiene espacios
        if (contrasena.isEmpty()) {
            throw new FormatoInvalidoException("La contraseña no puede estar vacía ni contener solo espacios.");
        }

        // Verificar que la longitud esté entre 4 y 20 caracteres
        if (contrasena.length() < 4 || contrasena.length() > 20) {
            throw new FormatoInvalidoException("La contraseña debe tener entre 4 y 20 caracteres.");
        }

        // Verificar que la contraseña solo contenga letras y números (sin caracteres especiales)
        if (!contrasena.matches("[a-zA-Z0-9]+")) {
            throw new FormatoInvalidoException("La contraseña solo puede contener letras y números.");
        }

        // Si pasa todas las validaciones, devolver la contraseña
        return contrasena;
    }
    //////Clientes
    public static String validarEmail(String input) throws FormatoInvalidoException {
        // Eliminar espacios al inicio y al final
        String email = input.trim();

        // Verificar si la entrada está vacía después de eliminar espacios
        if (email.isEmpty()) {
            throw new FormatoInvalidoException("El correo electrónico no puede estar vacío.");
        }

        // Verificar que el email termine con '.com'
        if (!email.toLowerCase().endsWith(".com")) {
            throw new FormatoInvalidoException("El correo electrónico debe terminar en '.com'.");
        }

        // Expresión regular para validar el formato del email y asegurar que termine en '.com'
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$";
        //String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|net)$";
//En caso que se permita algo mas de .com

        if (!email.matches(regex)) {
            throw new FormatoInvalidoException("El correo electrónico no tiene un formato válido.");
        }

        return email;
    }

    public static String validarTelefono(String input) throws FormatoInvalidoException {
        // Eliminar espacios al inicio y al final
        String telefono = input.trim();

        // Verificar si está vacío después de eliminar espacios
        if (telefono.isEmpty()) {
            throw new FormatoInvalidoException("El teléfono no puede estar vacío.");
        }

        // Expresión regular para el formato "223-132-2690"
        String regex = "^\\d{3}-\\d{3}-\\d{4}$";

        // Validar contra la expresión regular
        if (!telefono.matches(regex)) {
            throw new FormatoInvalidoException("El teléfono debe estar en el formato '223-301-2707'.");
        }

        return telefono;
    }

        public static Boolean validarLicenciaConducir(String input) throws FormatoInvalidoException {
            return validarSiONoBoolean(input); // Usamos el método que ya validamos para sí/no
        }
}
