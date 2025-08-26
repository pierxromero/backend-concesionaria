package Validaciones;


import Enums.Compras.TipoDePago;
import Exceptions.*;
import Exceptions.Compras.MetodoPagoInvalidoException;
import Exceptions.Compras.PagoRechazadoException;
import Exceptions.Personas.DniInvalidoException;
import Exceptions.Personas.EmailInvalidoException;
import Exceptions.Personas.NombreApellidoInvalidoException;
import Exceptions.Personas.TelefonoInvalidoException;
import Models.Compras.Compra;
import Models.Compras.PlanCompra;
import Models.Consecionaria;
import Models.Persona.Cliente;
import Models.Persona.Empleado;
import Models.Persona.Personas;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {
    public static int obtenerOpcionMenu(Scanner scanner) {
        int opcion = -1; //Valor por defecto, que indica que no se ha ingresado una opción válida.
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("|-Por favor seleccione una opción: ");
            String input = scanner.nextLine().trim(); // Leemos la entrada como String y eliminamos los espacios al inicio y fin

            // Verificamos si el input no es vacío
            if (input.isEmpty()) {
                System.out.println("Entrada vacía. Por favor ingrese una opción.");
                continue; // Vuelve a pedir la opción si el input está vacío
            }

            // Intentamos convertir el String a un número entero
            try {
                opcion = Integer.parseInt(input);  // Convertimos el String a un entero

                // Validación de que la opción esté dentro del rango esperado (0 a 8)
                if (opcion >= 0 && opcion <= 15) {
                    entradaValida = true;  // Salir del bucle si la entrada es válida
                } else {
                    System.out.println("Opción no válida. Por favor ingrese un numero que este entre las opciones.");
                }
            } catch (NumberFormatException e) {
                // Captura si el String no es un número válido
                System.out.println("Entrada no válida. Por favor ingrese un número.");
            }
        }
        return opcion;
    }
    public static String validarSiONo(String input) throws FormatoInvalidoException {
        // Verificar si la entrada es null o vacía
        if (input == null || input.trim().isEmpty()) {
            throw new FormatoInvalidoException("La entrada no puede estar vacía.");
        }

        String trimmedInput = input.trim().toLowerCase(); // Eliminar espacios y convertir a minúsculas

        // Si la entrada es "si", devolver "si"
        if (trimmedInput.equals("si")) {
            return "si"; // Devuelve "si"
        }
        // Si la entrada es "no", devolver null
        else if (trimmedInput.equals("no")) {
            return null; // Devuelve null si la entrada es "no"
        } else {
            throw new FormatoInvalidoException("Entrada inválida. Solo se permiten los valores 'si' o 'no'.");
        }
    }
    /* Validaciones para la contraseña */
    public static boolean validarContrasena(String contrasena) throws ContrasenaInvalidaException {
        //Verifica que la contraseña no sea nula ni vacía
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new ContrasenaInvalidaException("La contraseña no puede estar vacía ni contener solo espacios.");
        }
        //Verifica la longitud
        if (contrasena.length() < 4 || contrasena.length() > 20) {
            throw new ContrasenaInvalidaException("La contraseña debe tener entre 4 y 20 caracteres.");
        }
        //Verifica que no contenga caracteres especiales
        if (!contrasena.matches("[a-zA-Z0-9]+")) {
            throw new ContrasenaInvalidaException("La contraseña solo puede contener letras y números.");
        }
        return true;
    }
    public static boolean validarLogin(Consecionaria consecionaria, String dni, String password) throws DniInvalidoException {
        Personas persona = consecionaria.buscarPersonaPorDni(dni);
        if (persona instanceof Empleado) {
            Empleado empleado = (Empleado) persona;
            return empleado.getContrasena().equals(password);
        } else {
            throw new DniInvalidoException("El usuario con el DNI ingresado no es un empleado.");
        }
    }
    /* Validaciones para el DNI */
    public static Boolean validarDni(String dni)throws DniInvalidoException
    {
        if(dni.length()>9 || !(dni.matches("\\d+"))||dni.length()<7)
        {
            throw new DniInvalidoException("Formato de DNI invalido");
        }
        else{
            return true;
        }
    }
    public static Boolean validarBoolean(String aValidar)throws FormatoInvalidoException
    {
        if(aValidar.equalsIgnoreCase("true")||aValidar.equalsIgnoreCase("false"))
        {
            return true;
        }
        else{
            throw new FormatoInvalidoException("Formato invalido debe ingresar true o false");
        }
    }
    public static Boolean validarMarca(String marca)throws FormatoInvalidoException
    {

        if (marca.matches(".*\\d.*")){
            throw new FormatoInvalidoException("Marca inexistente");
        }else{
            return true;
        }
    }
    public static Boolean validarMatriculaMoto(String matricula)throws FormatoInvalidoException
    {
        String regex = "^[A-Za-z]{3}[0-9]{3}$|^[A-Za-z]{1}[0-9]{3}[A-Za-z]{3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(matricula);
        if(matcher.matches())
        {
            return true;
        }else {
            throw new FormatoInvalidoException("Formato de matricula invalido");
        }
    }
    public static Boolean ValidarPrecio(String precio)throws FormatoInvalidoException
    {
        try{
            Double.parseDouble(precio);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }


    public static boolean validarNombreApellido(String aValidar) {
        try {
            if (aValidar == null || aValidar.trim().isEmpty()) {
                throw new NombreApellidoInvalidoException("El valor no puede ser vacío.");
            }
            if (!aValidar.matches("[a-zA-ZáéíóúÁÉÍÓÚÑñ ]+")) {
                throw new NombreApellidoInvalidoException("El valor debe contener solo letras y espacios.");
            }
            return true;
        } catch (NombreApellidoInvalidoException e) {
            System.out.println("Error de validación en nombre o apellido: " + e.getMessage());
            return false;
        }
    }

    public static boolean validarEMAIL(String email) {
        try {
            if (email == null || !email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {
                throw new EmailInvalidoException("El email ingresado no es válido.");
            }
            return true;
        } catch (EmailInvalidoException e) {
            System.out.println("Error de validación en email: " + e.getMessage());
            return false;
        }
    }

    public static boolean validarTELEFONO(String telefono) {
        try {
            if (telefono == null || !telefono.matches("\\d{10}")) {
                throw new TelefonoInvalidoException("El teléfono ingresado debe tener 10 dígitos.");
            }
            return true;
        } catch (TelefonoInvalidoException e) {
            System.out.println("Error de validación en teléfono: " + e.getMessage());
            return false;
        }
    }

    public static boolean validarCliente(Cliente cliente, Consecionaria consecionaria)  {
        if (!Validaciones.validarNombreApellido(cliente.getNombre())) {
            return false;
        }
        if (!Validaciones.validarNombreApellido(cliente.getApellido())) {
            return false;
        }
        if (!Validaciones.validarDni(cliente.getDni())) {
            return false;
        }

        if (!Validaciones.validarEMAIL(cliente.getEmail())) {
            return false;
        }
        if (!Validaciones.validarTELEFONO(cliente.getTelefono())) {
            return false;
        }
        return true;
    }

//public static void ValidarPagocuota(double pago) throws PagoRechazadoException {
//    PlanCompra plan = new PlanCompra();
//    if (pago > plan.calcularCuotas(50000.0)||pago < 0) {
//throw new PagoRechazadoException("Pago invalido");
//    }else{
//        System.out.printf("Cuota pagada con exito");
//    }
//}
    public static void validarMetodoPago(String metodo)throws MetodoPagoInvalidoException {

        if(metodo.equals(TipoDePago.CREDITO)  || metodo.equals(TipoDePago.EFECTIVO)){
            System.out.printf("Metodo de pago correcto");
        }else {
            throw new MetodoPagoInvalidoException();
        }
    }

    public void validarMatricula(String opcionString) {

    }
}
