package Menu.MenuParaPersonas;

import Enums.Personas.Genero;
import Enums.Personas.Sector;
import Enums.TipoDeObject;
import Exceptions.FormatoInvalidoException;
import Exceptions.ListadoNoDisponibleException;
import Exceptions.Personas.PersonaNoEncontradaException;
import Exceptions.Personas.PersonaNullException;
import Exceptions.Personas.PersonaYaRegistradaException;
import Exceptions.Vehiculos.VehiculoNoEncontradoException;
import Exceptions.Vehiculos.VehiculoYaRegistradoException;
import Models.Consecionaria;
import Models.Persona.Cliente;
import Models.Persona.Empleado;
import Models.Persona.Personas;
import Models.Vehiculos.Vehiculo;

import java.util.Scanner;

import static Models.Gestores.JSONUtil.grabar;
import static Validaciones.PersonaValidacion.*;
import static Validaciones.Validaciones.*;
import static Validaciones.VehiculoValidacion.validarMatricula;
import static Validaciones.VehiculoValidacion.validarSiONoBoolean;

public class PersonasMenu {

    public static void menuGestionClientes(Consecionaria consecionaria) {
        int opcion;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|                                                                     |");
            System.out.println("|                Bienvenido a la Gestion                              |");
            System.out.println("|                    Especializada en Personas                        |");
            System.out.println("|                                                                     |");
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|-1. Agregar Cliente/Empleado");
            System.out.println("|-2. Eliminar Cliente/Empleado");
            System.out.println("|-3. Buscar Cliente/Empleado");
            System.out.println("|-4. Filtrado de Busqueda");
            System.out.println("|-5. Mostrar Todos los Empleados");
            System.out.println("|-6. Mostrar Todos los Clientes");
            System.out.println("|-7. Total de Cliente/Empleado");
            System.out.println("|-8. Modificar Cliente/Empleado");
            System.out.println("|-9. Comprobar Cliente/Empleado por DNI");
            System.out.println("|-0. Volver al menú principal");
            opcion = obtenerOpcionMenu(scanner);
            switch (opcion) {
                case 1:
                    try {
                        Personas persona = crearPersona();
                        consecionaria.agregarPersona(persona);
                        System.out.println("Persona agregado exitosamente.");
                        grabar(consecionaria.personasToJSONArray(),"Personas.JSON");
                    } catch (PersonaYaRegistradaException e) {
                        System.out.println("Error: La persona ya está registrado.");
                    } catch (PersonaNullException e) {
                        System.out.println("Error: La persona no puede estar vacia.");
                    } catch (Exception e) {
                        System.out.println("Error Inesperado en La Carga de la Persona: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Ingrese El DNI Para Eliminar la Persona");
                        String DNI = validarDNI(scanner.nextLine());
                        consecionaria.eliminarPersonaPorDni(DNI);
                        System.out.println("Persona eliminado exitosamente.");
                        grabar(consecionaria.personasToJSONArray(),"Personas.JSON");
                    }catch (PersonaNoEncontradaException | FormatoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Ingrese El DNI Para Buscar la Persona");
                        String DNI  = validarDNI(scanner.nextLine());
                        Personas personas = consecionaria.buscarPersonaPorDni(DNI);
                        if (personas != null) {
                            System.out.println("Vehículo encontrado:");
                            System.out.println(personas);
                        } else {
                            System.out.println("Vehículo no encontrado.");
                        }
                    }catch (PersonaNoEncontradaException | FormatoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    try{
                        filtradosPersonas(consecionaria);
                    }catch (Exception E){
                        System.out.println("Error :"+ E.getMessage());
                    }
                    break;
                case 5:
                    try {
                        consecionaria.mostrarPersonasFiltradas(consecionaria.buscarPersonasFiltro(null,
                                null,null,TipoDeObject.EMPLEADO));
                    }catch (ListadoNoDisponibleException e) {
                        System.out.println("Error De Listado: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error Inesperado en el Listado: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        consecionaria.mostrarPersonasFiltradas(consecionaria.buscarPersonasFiltro(null,
                                null,null,TipoDeObject.CLIENTE));
                    }catch (ListadoNoDisponibleException e) {
                        System.out.println("Error De Listado: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error Inesperado en el Listado: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        System.out.println("|---- Total de Personas -----|");
                        System.out.println(consecionaria.contarPersonas());
                        System.out.println("|---- Total Personas De Alta ------|");
                        System.out.println(consecionaria.contarPersonasDisponibles());
                        System.out.println("|-----------------------------|");
                    }catch (Exception e) {
                        System.out.println("Error Conteo: " + e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        modificarPersona(consecionaria);
                    }catch (Exception e) {
                        System.out.println("Error Modificacion: " + e.getMessage());
                    }
                    break;
                case 9:
                    try {
                        System.out.println("|-Ingrese El DNI Para Comprobar si existe la Persona-|");
                        System.out.print("|-DNI: ");
                        String DNI = validarDNI(scanner.nextLine());
                        Boolean comprobar = consecionaria.existePersona(DNI);
                        if (comprobar) {
                            System.out.println("|-La Persona Existe-|");
                            System.out.println(consecionaria.buscarPersonaPorDni(DNI));
                        } else {
                            System.out.println("|-La Persona No Existe-|");
                        }
                    } catch (FormatoInvalidoException e) {
                        System.out.println("Error De Formato: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error Inesperado De Comprobacion: " + e.getMessage());
                    }
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }
    public static void modificarPersona(Consecionaria consecionaria) {
        System.out.println("|-Ingrese el DNI para modificar la Persona-|");
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.print("|-DNI: ");
            String dniPrincipal = validarDNI(scanner.nextLine());
            Personas persona = consecionaria.buscarPersonaPorDni(dniPrincipal);
            Boolean continuar = true;
            while (continuar) {
                System.out.println("|-- Modificar Persona --|");
                System.out.println("1. Nombre");
                System.out.println("2. Apellido");
                System.out.println("3. Dirección");
                System.out.println("4. Estado (Activo/Inactivo)");
                System.out.println("5. Género");
                if (persona instanceof Cliente) {
                    System.out.println("6. Email");
                    System.out.println("7. Teléfono");
                    System.out.println("8. Licencia de Conducir");
                } else if (persona instanceof Empleado) {
                    System.out.println("6. Sector");
                    System.out.println("7. Sueldo Base");
                    System.out.println("8. Antigüedad");
                    System.out.println("9. Contraseña");
                }
                System.out.println("0. Salir");
                int opcion = obtenerOpcionMenu(scanner);
                switch (opcion) {
                    case 1:
                        try {
                            System.out.println("Ingrese el nuevo nombre: ");
                            persona.setNombre(validarNombre(scanner.nextLine()));
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificación: " + e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            System.out.println("Ingrese el nuevo apellido: ");
                            persona.setApellido(validarApellido(scanner.nextLine()));
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificación: " + e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            System.out.println("Ingrese la nueva dirección (Calle y Número): ");
                            persona.setDireccion(validarDireccion(scanner.nextLine()));
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificación: " + e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            System.out.println("Ingrese el nuevo estado [Si(Activo)/No(Inactivo)]]: ");
                            persona.setEstado(validarSiONoBoolean(scanner.nextLine()));
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificación: " + e.getMessage());
                        }
                        break;
                    case 5:
                        try {
                            System.out.println("Ingrese el nuevo género (Masculino/Femenino/otro): ");
                            persona.setGenero(validarGenero(scanner.nextLine()));
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificación: " + e.getMessage());
                        }
                        break;
                    case 6:
                        if (persona instanceof Cliente) {
                            try {
                                System.out.println("Ingrese el nuevo email (Correo Electronico @-.com): ");
                                ((Cliente) persona).setEmail(validarEmail(scanner.nextLine()));
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificación: " + e.getMessage());
                            }
                        } else if (persona instanceof Empleado) {
                            try {
                                System.out.println("Ingrese el nuevo sector (Administracion,taller,ventas,contabilidad) : ");
                                ((Empleado) persona).setSector(validarSector(scanner.nextLine()));
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificación: " + e.getMessage());
                            }
                        }
                        break;
                    case 7:
                        if (persona instanceof Cliente) {
                            try {
                                System.out.println("Ingrese el nuevo teléfono (ejemplo 222-222-3232): ");
                                ((Cliente) persona).setTelefono(validarTelefono(scanner.nextLine()));
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificación: " + e.getMessage());
                            }
                        } else if (persona instanceof Empleado) {
                            try {
                                System.out.println("Ingrese el nuevo sueldo base: ");
                                ((Empleado) persona).setSueldoBase(validarSueldoBase(scanner.nextLine()));
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificación: " + e.getMessage());
                            }
                        }
                        break;
                    case 8:
                        if (persona instanceof Cliente) {
                            try {
                                System.out.println("¿La persona tiene licencia de conducir? (Si/No): ");
                                ((Cliente) persona).setLicenciaConducir(validarSiONoBoolean(scanner.nextLine()));
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificación: " + e.getMessage());
                            }
                        } else if (persona instanceof Empleado) {
                            try {
                                System.out.println("Ingrese la nueva antigüedad (en años): ");
                                ((Empleado) persona).setAntiguedad(validarAntiguedad(scanner.nextLine()));
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificación: " + e.getMessage());
                            }
                        }
                        break;
                    case 9:
                        if (persona instanceof Empleado) {
                            try {
                                System.out.println("Ingrese la nueva contraseña: ");
                                ((Empleado) persona).setContrasena(validarContrasenaPersona(scanner.nextLine()));
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificación: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Opción inválida para el tipo de persona seleccionado.");
                        }
                        break;
                    case 0:
                        continuar = false;
                        boolean exito = consecionaria.actualizarPersona(dniPrincipal, persona);
                        if (exito) {
                            grabar(consecionaria.personasToJSONArray(), "Personas.JSON");
                            System.out.println("Persona actualizada correctamente.");
                        } else {
                            System.out.println("No se pudo actualizar la persona.");
                        }
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            }
        } catch (PersonaNoEncontradaException | FormatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public static Personas crearPersona(){
        Scanner scanner = new Scanner(System.in);
       try{
           System.out.println("|-Ingrese el Nombre de la persona: ");
           System.out.print("|-Nombre: ");
           String nombre = validarNombre(scanner.nextLine());
           System.out.println("|-Ingrese el Apellido de la persona: ");
           System.out.print("|-Apellido: ");
           String apellido = validarApellido(scanner.nextLine());
           System.out.println("|-Ingrese el DNI de la persona: ");
           System.out.print("|-DNI: ");
           String dni = validarDNI(scanner.nextLine());
           System.out.println("|-Ingrese Direccion de la persona (Calle y Numero)");
           System.out.print("|-Direccion: ");
           String direccion = validarDireccion(scanner.nextLine());
           System.out.println("|-Ingrese Genero de la persona (Masculino,Femenino,Otro)");
           System.out.print("|-Genero: ");
           Genero genero = validarGenero(scanner.nextLine());
           System.out.println("|-Ingrese el tipo de Persona / Empleado o Cliente: ");
           System.out.print("|-Tipo de Persona: ");
           TipoDeObject tipoDeObject = validarTipoPersona(scanner.nextLine());
           Personas personas = null;
           switch (tipoDeObject){
               case CLIENTE:
                   System.out.println("|-Para la creacion del cliente-|");
                   System.out.println("|-Porfavor ingrese los siguientes datos-|");
                   System.out.println("|-Ingrese el email de la Persona (@gmail.com ejemplo)");
                   System.out.print("|-Email: ");
                   String email = validarEmail(scanner.nextLine());
                   System.out.println("|-Ingrese el telefono de la Persona (ejemplo 222-222-3232)");
                   System.out.print("|-Telefono: ");
                   String telefono = validarTelefono(scanner.nextLine());
                   System.out.println("|-Ingrese si cuenta con Licencia de Conducir si/no");
                   System.out.print("|-Licencia: ");
                   Boolean licenciaConducir = validarLicenciaConducir(scanner.nextLine());
                   personas = new Cliente(tipoDeObject,apellido,direccion,dni,
                           true,genero,nombre,email,licenciaConducir,telefono);
                   break;
               case EMPLEADO:
                   System.out.println("|-Para la creación del empleado-|");
                   System.out.println("|-Por favor, ingrese los siguientes datos-|");
                   System.out.println("|-Ingrese el sector del empleado-|");
                   System.out.println("|-Opciones: ADMINISTRACION, VENTAS, TALLER, CONTABILDIAD-|");
                   System.out.print("|-Sector: ");
                   Sector sector = validarSector(scanner.nextLine());
                   System.out.println("|-Ingrese el sueldo base del empleado-|");
                   System.out.print("|-Sueldo Base (Decimal): ");
                   Double sueldoBase = validarSueldoBase(scanner.nextLine());
                   System.out.println("|-Ingrese la antigüedad del empleado-|");
                   System.out.print("|-Antigüedad (en años): ");
                   Integer antiguedad = validarAntiguedad(scanner.nextLine());
                   System.out.println("|-Ingrese la contraseña del empleado-|");
                   System.out.print("|-Contraseña: ");
                   String contrasena = validarContrasenaPersona(scanner.nextLine());
                   personas = new Empleado(tipoDeObject,apellido,direccion,dni,true,
                           genero,nombre,antiguedad,sector,sueldoBase,contrasena);
                   break;
               default:
                   System.out.println("Tipo de Persona Desconocida");
           }
           if (personas != null) {
               System.out.println("Persona creada exitosamente.");
           }
           return personas;
       }catch (FormatoInvalidoException e) {
           System.out.println("Datos de la Persona Incorrectos: " + e.getMessage());
       }
       return null;
   }
    public static void filtradosPersonas(Consecionaria consecionaria) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("|-Filtrar Personas-|");
            System.out.println("|-Si desea aplicar un filtro, ingrese Si/No-|");
            System.out.print("|-¿Desea filtrar por Tipo de Persona? [Si/No]: ");
            String tipoPersonaTemp = validarSiONo(scanner.nextLine());
            System.out.print("|-¿Desea filtrar por Género? [Si/No]: ");
            String generoTemp = validarSiONo(scanner.nextLine());
            System.out.print("|-¿Desea filtrar por Estado? [Si/No]: ");
            String estadoTemp = validarSiONo(scanner.nextLine());
            System.out.print("|-¿Desea filtrar por Apellido? [Si/No]: ");
            String apellidoTemp = validarSiONo(scanner.nextLine());

            TipoDeObject tipoDeObject = null;
            Genero genero = null;
            Boolean estado = null;
            String apellido = null;

            try {
                if (tipoPersonaTemp != null && tipoPersonaTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese el Tipo de Persona-|");
                    System.out.print("|-Tipo de Persona (Empleado/Cliente): ");
                    tipoDeObject = validarTipoPersona(scanner.nextLine());
                }
                if (generoTemp != null && generoTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese el Género-|");
                    System.out.print("|-Género: ");
                    genero = validarGenero(scanner.nextLine());
                }
                if (estadoTemp != null && estadoTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese el Estado de la Persona-|");
                    System.out.print("|-Estado (true/si - false/no): ");
                    estado = validarSiONoBoolean(scanner.nextLine());
                }
                if (apellidoTemp != null && apellidoTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese el Apellido de la Persona-|");
                    System.out.print("|-Apellido: ");
                    apellido = validarApellido(scanner.nextLine());
                }
                consecionaria.mostrarPersonasFiltradas(consecionaria.buscarPersonasFiltro(
                       estado, apellido, genero, tipoDeObject
                ));
            } catch (FormatoInvalidoException e) {
                System.out.println("Dato del Filtro Incorrecto: " + e.getMessage());
            } catch (ListadoNoDisponibleException e) {
                System.out.println("Error de Listado: " + e.getMessage());
            }

        } catch (FormatoInvalidoException e) {
            System.out.println("Error De Filtro: " + e.getMessage());
        }
    }
}
