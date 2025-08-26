package Menu.MenuParaCompras;

import Enums.Compras.Plazo;
import Enums.Compras.TipoDePago;
import Enums.TipoDeObject;
import Exceptions.Compras.MetodoPagoInvalidoException;
import Exceptions.FormatoInvalidoException;
import Exceptions.ListadoNoDisponibleException;
import Exceptions.Personas.DniInvalidoException;
import Exceptions.Personas.PersonaNoEncontradaException;
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

import java.awt.*;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static Models.Gestores.JSONUtil.grabar;
import static Validaciones.CompraValidacion.*;
import static Validaciones.PersonaValidacion.validarDNI;
import static Validaciones.Validaciones.*;
import static Validaciones.VehiculoValidacion.validarMatricula;

public class ComprasMenu {
    public static void menuGestionCompras(Consecionaria consecionaria, Empleado admin) {
        Scanner scanner = new Scanner(System.in);
        Integer opcion;
        do {
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|                                                                     |");
            System.out.println("|                  Bienvenido a la Gestion                            |");
            System.out.println("|                        Especializada en Compras!                    |");
            System.out.println("|                                                                     |");
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|-1. Listar compras");
            System.out.println("|-2. Generar una compra");
            System.out.println("|-3. Pagar Compra de cuota");
            System.out.println("|-4. Pagar Compra de Efectivo");
            System.out.println("|-0. Volver al menú principal");
            opcion = obtenerOpcionMenu(scanner);
            switch (opcion) {
                case 1:
                    try{
                        consecionaria.listarCompras();
                    }catch (ListadoNoDisponibleException e){
                        System.out.println(e.getMessage());
                    }catch (Exception e){
                        System.out.println("Error Inesperado en Listar Compras: "+e.getMessage());
                    }
                    break;
                case 2:
                     try {
                         Compra compra = crearCompra(consecionaria, admin);
                         if (compra != null) {
                             consecionaria.AgregarCompra(compra);
                             grabar(consecionaria.ComprasToJSONArray(), "Compras.JSON");
                             System.out.println("Compra Generada Correctamente!");
                             System.out.println(compra);
                         }
                     }catch (Exception e){
                         System.out.println("Error Inesperado en Generar Compra: "+e.getMessage());
                     }
                    break;
                case 3:
                    try {
                        pagarCompraCuota(consecionaria, scanner);
                    }catch (Exception e){
                        System.out.println("Error Inesperado en Pago de Cuota: "+e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        pagarCompraEfectivo(consecionaria, scanner);
                    }catch (Exception e){
                        System.out.println("Error Inesperado en Pago de Efectivo: "+e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opción invalida. Por favor, intente de nuevo.");

            }
        } while (opcion !=0);
    }
    public static void pagarCompraCuota(Consecionaria consecionaria, Scanner scanner) {
        System.out.println("Ingrese el DNI del cliente para pagar las cuotas pendientes:");
        try{
            String dniCliente = validarDNI(scanner.nextLine());
            Personas persona = consecionaria.buscarPersonaPorDni(dniCliente);
            if (persona instanceof Empleado) {
                System.out.println("El empleado no puede pagar compras.");
                return;
            }
            Cliente cliente = (Cliente) persona;
            // Obtener compras pendientes de ese cliente (estadoCompra = false)
            HashSet<Compra> comprasPendientes = (HashSet<Compra>) consecionaria.buscarComprasCuotasPorCliente(cliente);
            if (comprasPendientes.isEmpty()) {
                System.out.println("No hay compras pendientes de pago para este cliente.");
            } else {
                System.out.println("Compras pendientes de pago para el cliente " + cliente.getNombre() + ":");
                comprasPendientes.forEach(compra -> {
                    System.out.println("---------------------------------------------------------------------------------------------------------------------");
                    System.out.println(compra);
                    System.out.println("---------------------------------------------------------------------------------------------------------------------");
                });
                System.out.println("Seleccione la compra que desea pagar:");
                Integer seleccionCompra =obtenerOpcionMenu(scanner);
                Compra compraSeleccionada = obtenerCompraPorIndice(comprasPendientes, seleccionCompra);
                if (compraSeleccionada != null) {
                    // Realizar el pago de la cuota
                    compraSeleccionada.setEstado(true); // Actualizar el estado de la compra a 'paga'
                    System.out.println("Realizando pago de la compra: " );
                    consecionaria.pagarCuota(compraSeleccionada);
                    consecionaria.darDeBajaVehiculo(compraSeleccionada.getVehiculo().getMatricula());
                    System.out.println("¡Pago realizado con éxito!");
                    grabar(consecionaria.vehiculosToJSONArray(), "Vehiculos.JSON");
                    grabar(consecionaria.ComprasToJSONArray(), "Compras.JSON");
                } else {
                    System.out.println("Selección de compra inválida.");
                }
            }
        }catch (FormatoInvalidoException e){
            System.out.println("Error: "+e.getMessage());
        }catch (PersonaNoEncontradaException e){
            System.out.println("Error No Se Encontro Persona: "+e.getMessage());
        }

    }

    public static void pagarCompraEfectivo(Consecionaria consecionaria, Scanner scanner) {
        try {
            // Solicitar el DNI del cliente
            System.out.println("Ingrese el DNI del cliente para pagar la compra en efectivo:");
            String dniCliente = validarDNI(scanner.nextLine());

            // Buscar la persona por el DNI
            Personas personas = consecionaria.buscarPersonaPorDni(dniCliente);

            // Verificar si la persona es un empleado
            if (personas.getTipo().equals(TipoDeObject.EMPLEADO)) {
                System.out.println("Un empleado no tiene compras. No se puede procesar el pago.");
                return; // Termina la función si es un empleado
            }

            // Verificar que la persona es un cliente antes de hacer el cast
                Cliente cliente = (Cliente) personas;  // Ahora podemos hacer el cast sin error

                // Obtener compras pendientes de ese cliente (estadoCompra = false)
                HashSet<Compra> comprasPendientes = (HashSet<Compra>) consecionaria.buscarComprasEfectivoPorCliente(cliente);

                // Verificar si hay compras pendientes de pago
                if (comprasPendientes.isEmpty()) {
                    System.out.println("No hay compras pendientes de pago para este cliente.");
                } else {
                    // Mostrar las compras pendientes de pago
                    System.out.println("Compras pendientes de pago para el cliente " + cliente.getNombre() + ":");
                    comprasPendientes.forEach(compra -> {
                        System.out.println(compra);
                    });

                    // Solicitar al usuario que seleccione la compra que desea pagar
                    System.out.println("Seleccione la compra que desea pagar:");
                    int seleccionCompra = obtenerOpcionMenu(scanner);
                    Compra compraSeleccionada = obtenerCompraPorIndice(comprasPendientes, seleccionCompra);

                    if (compraSeleccionada != null) {
                        // Realizar el pago en efectivo
                        compraSeleccionada.setEstado(true); // Actualizar el estado de la compra a 'paga'
                        System.out.println("Realizando pago en efectivo de la compra: ");
                        System.out.println(compraSeleccionada);
                        consecionaria.darDeBajaVehiculo(compraSeleccionada.getVehiculo().getMatricula());
                        System.out.println("¡Pago en efectivo realizado con éxito!");
                        grabar(consecionaria.vehiculosToJSONArray(), "Vehiculos.JSON");
                        grabar(consecionaria.ComprasToJSONArray(), "Compras.JSON");
                    } else {
                        System.out.println("Selección de compra inválida.");
                    }
                }
        } catch (FormatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (PersonaNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Compra obtenerCompraPorIndice(Set<Compra> compras, int indice) {
        ArrayList<Compra> listaCompras = new ArrayList<>(compras);
        if (indice >= 1 && indice <= listaCompras.size()) {
            return listaCompras.get(indice - 1);
        } else {
            return null;
        }
    }
    public static Compra crearCompra(Consecionaria consecionaria, Empleado admin) {
        Scanner scanner = new Scanner(System.in);
        Compra compra = null;
        System.out.println("Bienvenido a la Generacion de Compras");
        System.out.println("El Empleado a Cargo es: " + admin.getNombre() + " " + admin.getApellido());
        System.out.println("Ingrese el DNI del cliente");
        System.out.print("|-DNI: ");
        try {
            String DNI = validarDNI(scanner.nextLine());
            Personas persona = consecionaria.buscarPersonaPorDni(DNI);
            if (persona instanceof Empleado) {
                System.out.println("El empleado " + persona.getNombre() + " " + persona.getApellido() + " no puede realizar compras");
                return null;
            }
            Cliente cliente = (Cliente) persona;
            System.out.println("Estos Son Nuestros Vehiculos Disponibles");
            consecionaria.mostrarVehiculosFiltrados(consecionaria.buscarVehiculosFiltrado(true, null, null, null, null));

            System.out.println("Por favor Seleccione el Vehiculo a comprar");
            System.out.println("Ingresando su Matricula Correspondiente");
            System.out.print("|-Matricula: ");
            try {
                String matricula = scanner.nextLine();
                validarMatricula(matricula);
                Vehiculo vehiculoAcomprar = consecionaria.buscarVehiculoPorMatricula(matricula);
                System.out.println("Procedamos con el Tipo de Pago");
                System.out.print("|-Tipo de Pago: ");
                TipoDePago tipoDePago = validarTipoDePago(scanner.nextLine());
                Factura factura;
                PlanCompra planCompra = null;
                if (tipoDePago == TipoDePago.CREDITO) {
                    planCompra = generarPlanDeCompra(scanner, vehiculoAcomprar.getPrecio());
                    Double montoTotal = planCompra.getMontoTotal();
                    factura = new Factura(planCompra.getPlazo().ordinal() + 1, LocalDate.now(), montoTotal);
                } else {
                    factura = new Factura(0, LocalDate.now(), vehiculoAcomprar.getPrecio());
                }
                compra = new Compra(cliente, admin, false, factura, planCompra, tipoDePago, vehiculoAcomprar);
                return compra;

            } catch (FormatoInvalidoException e) {
                System.out.println("Error Ingreso de Dato: " + e.getMessage());
            } catch (VehiculoNoEncontradoException e) {
                System.out.println("Error En Busqueda: " + e.getMessage());
            }
        } catch (FormatoInvalidoException e) {
            System.out.println("Error de Cliente: " + e.getMessage());
        } catch (PersonaNoEncontradaException e) {
            System.out.println("Error de Busqueda Cliente: " + e.getMessage());
        } catch (ListadoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return compra;
    }
    public static PlanCompra generarPlanDeCompra(Scanner scanner, Double precioVehiculo) {
        PlanCompra planCompra = null;
        try{
            System.out.println("|-Usted ha decidido comprar mediante Credito-|");
            System.out.print("|-Seleccione el Plazo (DOCE MESES/VEINTICUATRO MESES/TREINTA Y SEIS MESES): ");
            Plazo plazo = validarPlazo(scanner.nextLine());
            System.out.print("|-Ingrese el monto inicial: ");
            Double montoInicial = validarMontoInicial(scanner.nextLine(),precioVehiculo);
            planCompra = new PlanCompra();
            planCompra.setPlazo(plazo);
            planCompra.setMontoInicial(montoInicial);
            // Calcular interés y monto total
            Double montoFinanciado = precioVehiculo - montoInicial; //1
            Double interes = planCompra.calcularInteres();
            Double interesTotal = montoFinanciado * interes; //2
            Double montoMensual = (montoFinanciado + interesTotal) / planCompra.calcularCuotas();
            Double montoTotal = montoMensual * planCompra.calcularCuotas();
            planCompra.setInteres(interes);
            planCompra.setMontoTotal(montoTotal);
        }catch (FormatoInvalidoException e){
            System.out.println("Error en el Plan de Compra: " + e.getMessage());
        }
        return planCompra;
    }
}
