package Menu;
import static Menu.MenuParaCompras.ComprasMenu.menuGestionCompras;
import static Menu.MenuParaPersonas.PersonasMenu.menuGestionClientes;
import  static Menu.MenuParaVehiculos.VehiculoMenu.menuGestionVehiculos;
import static Models.Gestores.JSONUtil.grabar;
import static Validaciones.Validaciones.*;

import Enums.Compras.Plazo;
import Enums.Compras.TipoDePago;
import Enums.Personas.Genero;
import Enums.Personas.Sector;
import Enums.TipoDeObject;
import Exceptions.*;
import Exceptions.Compras.MetodoPagoInvalidoException;
import Exceptions.Personas.DniInvalidoException;
import Exceptions.Personas.PersonaNoEncontradaException;
import Exceptions.Personas.PersonaYaRegistradaException;
import Exceptions.Vehiculos.VehiculoNoEncontradoException;
import Models.Compras.Compra;
import Models.Compras.Factura;
import Models.Compras.PlanCompra;
import Models.Consecionaria;
import Models.Persona.Cliente;
import Models.Persona.Empleado;
import Models.Persona.Personas;
import Models.Vehiculos.Vehiculo;
import Validaciones.Validaciones;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Set;

public class MenuSistema {
    public static void inicioLoggin() {
        Consecionaria consecionaria = new Consecionaria("Consecionaria UTN", "Av. Principal 123", "223456789");
        try{
            consecionaria.leerPersonas("Personas.JSON");
            consecionaria.leerVehiculos("Vehiculos.JSON");
            consecionaria.leerCompras("Compras.JSON");
        }catch (Exception e){
            System.out.println("No se pudo leer el JSON");
        }
        if(consecionaria.contarPersonas()==0){
                cargarEmpleadosAdmin(consecionaria);
        }
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 1) {
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|                                                                     |");
            System.out.println("|                      Bienvenido al Sistema                          |");
            System.out.println("|                        Porfavor logueese!                           |");
            System.out.println("|                                                                     |");
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.print("|-Ingrese su DNI: ");
            String dni = scanner.nextLine();

            try {
                validarDni(dni);
                System.out.print("|-Ingrese su contraseña: ");
                String password = scanner.nextLine();
                validarContrasena(password);

                if (validarLogin(consecionaria, dni, password)) {
                    Empleado admin = (Empleado) consecionaria.buscarPersonaPorDni(dni);
                    System.out.println("|-Login exitoso. Bienvenido al sistema.");
                    inicioMenu(consecionaria, admin);
                } else {
                    System.out.println("|-Contraseña incorrecta. Intente de nuevo.");
                }
            } catch (DniInvalidoException | ContrasenaInvalidaException e) {
                System.out.println("|-Error: " + e.getMessage());
            } catch (PersonaNoEncontradaException e) {
                System.out.println("|-Se produjo un Error: " + e.getMessage());
            }

            System.out.println("|-¿Desea Salir? 1-Si | Otro caracter-No");
            String input = scanner.nextLine();

            if (input.trim().equals("1")) {
                opcion = 1;
            } else {
                opcion = 0;
            }
        }
    }
    public static void cargarEmpleadosAdmin(Consecionaria consecionaria) {
            Empleado empleado1 = new Empleado(TipoDeObject.EMPLEADO,"Ramos","Libertad 981","44658233",
                    true, Genero.MASCULINO,"Lautaro",2, Sector.ADMINISTRACION,10000.40,"lauta233");
            Empleado empleado2 = new Empleado(TipoDeObject.EMPLEADO,"Castro","Guemes 255","45612666",
                    true, Genero.MASCULINO,"Lautaro",4,Sector.ADMINISTRACION,15000.00,"castro100");
            Empleado empleado3 = new Empleado(TipoDeObject.EMPLEADO,"Odera","20 de Septiembre","40405056",
                    true,Genero.FEMENINO,"Maica",8,Sector.VENTAS,20000.50,"mai97");
            Empleado empleado4 = new Empleado(TipoDeObject.EMPLEADO,"Romero","Constitucion 900","95296095",
                    true,Genero.MASCULINO,"Piero",5,Sector.VENTAS,18000.00,"piero987");
            consecionaria.agregarPersona(empleado1);
            consecionaria.agregarPersona(empleado2);
            consecionaria.agregarPersona(empleado3);
            consecionaria.agregarPersona(empleado4);
        System.out.println("|-----------------------------------------------------------------|");
        System.out.println("|- Se ha realizado un BACKUP de los administradores.");
        System.out.println("|- Causa: El archivo Personas.JSON estaba vacío.");
        System.out.println("|-----------------------------------------------------------------|");
            grabar(consecionaria.personasToJSONArray(),"Personas.JSON");
    }
    public static void inicioMenu(Consecionaria consecionaria, Empleado admin) {
        Scanner scanner = new Scanner(System.in);
            Integer opcion;
        do {
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|                                                                     |");
            System.out.println("|                Concesionaria Consecionaria UTN                      |");
            System.out.println("|                      Sistema de Gestión                             |");
            System.out.println("|                                                                     |");
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|-¡Bienvenido! "+admin.getNombre()+" "+admin.getApellido()+" Del Sector "+admin.getSector());
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|-1. Gestión de Vehículos");
            System.out.println("|-2. Gestión de Clientes/Empleados");
            System.out.println("|-3. Gestion de Compras");
            System.out.println("|-4. Información de la Consecionaria");
            System.out.println("|-0. Salir");
            opcion = obtenerOpcionMenu(scanner);
            switch (opcion) {
                case 1:
                    try {
                        menuGestionVehiculos(consecionaria);
                    }catch (Exception e){
                        System.out.println("Error en Menu Gestion de Vehículos: "+e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        menuGestionClientes(consecionaria);
                    }catch (Exception e){
                        System.out.println("Error en Menu Gestion de Vehículos: "+e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        menuGestionCompras(consecionaria,admin);
                    }catch (Exception e){
                        System.out.println("Error en Menu Gestion de Vehículos: "+e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.println("--- Información de la Consecionaria ---");
                        System.out.println(consecionaria);
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }
}
