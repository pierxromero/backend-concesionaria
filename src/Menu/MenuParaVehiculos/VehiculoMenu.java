package Menu.MenuParaVehiculos;

import Enums.TipoDeObject;
import Enums.Vehiculos.TipoDeCarga;
import Enums.Vehiculos.TipoDeCombustible;
import Enums.Vehiculos.TipoDeMoto;
import Exceptions.FormatoInvalidoException;
import Exceptions.ListadoNoDisponibleException;
import Exceptions.Vehiculos.VehiculoNoEncontradoException;
import Exceptions.Vehiculos.VehiculoNullException;
import Exceptions.Vehiculos.VehiculoYaRegistradoException;
import Models.Consecionaria;
import Models.Vehiculos.Auto;
import Models.Vehiculos.Camion;
import Models.Vehiculos.Moto;
import Models.Vehiculos.Vehiculo;

import java.time.Year;
import java.util.Scanner;

import static Models.Gestores.JSONUtil.grabar;
import static Validaciones.Validaciones.*;
import static Validaciones.VehiculoValidacion.*;
import static Validaciones.VehiculoValidacion.validarTipoDeCarga;

public class VehiculoMenu {
    public static void menuGestionVehiculos(Consecionaria consecionaria) {
        int opcion;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|                                                                     |");
            System.out.println("|                Bienvenido a la Gestion                              |");
            System.out.println("|                    Especializada en Vehiculos                       |");
            System.out.println("|                                                                     |");
            System.out.println("|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|");
            System.out.println("|-1. Agregar Vehículo");
            System.out.println("|-2. Eliminar Vehiculo");
            System.out.println("|-3. Buscar Vehiculo");
            System.out.println("|-4. Filtrar Vehiculos");
            System.out.println("|-5. Mostrar Todos Los Vehiculos");
            System.out.println("|-6. Total de Vehiculos en el Sistema");
            System.out.println("|-7. Actualizar/Modificar Vehiculo");
            System.out.println("|-8. Comprobar Vehiculo por Matricula/Existe o no");
            System.out.println("|-0. Volver al menú principal");
            opcion = obtenerOpcionMenu(scanner);
            switch (opcion) {
                case 1:
                    try {
                        Vehiculo vehiculo = crearVehiculo();
                        consecionaria.agregarVehiculo(vehiculo);
                        System.out.println("Vehículo agregado exitosamente.");
                        grabar(consecionaria.vehiculosToJSONArray(),"Vehiculos.JSON");
                    } catch (VehiculoYaRegistradoException e) {
                        System.out.println("Error: El vehículo ya está registrado.");
                    } catch (VehiculoNullException e){
                        System.out.println("Error: El vehículo no puede ser nulo.");
                    }
                    catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Ingrese La Matricula Para Eliminar el Vehiculo");
                        String matricula = scanner.nextLine();
                        validarMatricula(matricula);
                        consecionaria.eliminarVehiculoPorMatricula(matricula);
                        System.out.println("Vehículo eliminado exitosamente.");
                        grabar(consecionaria.vehiculosToJSONArray(),"Vehiculos.JSON");
                    }catch (VehiculoNoEncontradoException | FormatoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error de Vehiculo contains: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Ingrese La Matricula Para Buscar el Vehiculo");
                        String matricula = scanner.nextLine();
                        validarMatricula(matricula);
                        Vehiculo vehiculo = consecionaria.buscarVehiculoPorMatricula(matricula);
                        if (vehiculo != null) {
                            System.out.println("Vehículo encontrado:");
                            System.out.println(vehiculo);
                        } else {
                            System.out.println("Vehículo no encontrado.");
                        }
                    }catch (VehiculoNoEncontradoException | FormatoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    filtradosVehiculos(consecionaria);
                    break;
                case 5:
                    try {
                        System.out.println("|---- Todos Los Vehículos ----|");
                        consecionaria.listarVehiculos();
                        System.out.println("|-----------------------------|");
                    } catch (ListadoNoDisponibleException e) {
                        System.out.println("Error De Listado: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        System.out.println("|---- Total de Vehiculos -----|");
                        System.out.println(consecionaria.contarVehiculos());
                        System.out.println("|---- Total Disponibles ------|");
                        System.out.println(consecionaria.contarVehiculosDisponibles());
                        System.out.println("|-----------------------------|");
                    }catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        modificarVehiculo(consecionaria);
                    }catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        System.out.println("|-Ingrese La Matricula Para Comprobar si existe el Vehiculo-|");
                        System.out.print("|-Matricula: ");
                        String matricula = scanner.nextLine();
                        validarMatricula(matricula);
                        Boolean comprobar = consecionaria.existeVehiculo(matricula);
                        if (comprobar) {
                            System.out.println("|-El Vehiculo Existe-|");
                            System.out.println(consecionaria.buscarVehiculoPorMatricula(matricula));
                        } else {
                            System.out.println("|-El Vehiculo No Existe-|");
                        }
                    } catch (FormatoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }
    public static void modificarVehiculo(Consecionaria consecionaria) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("|-Ingrese La Matricula Para Modificar el Vehiculo-|");
        try {
            System.out.print("|-Matricula: ");
            String matriculaPrincipal = scanner.nextLine();
            validarMatricula(matriculaPrincipal);
            Vehiculo nuevoVehiculo = consecionaria.buscarVehiculoPorMatricula(matriculaPrincipal);
            boolean continuar = true;
            while (continuar) {
                System.out.println("|-- Modificar Vehículo --|");
                System.out.println("1. Estado");
                System.out.println("2. Marca");
                System.out.println("3. Modelo");
                System.out.println("4. Precio");
                System.out.println("5. Año");
                System.out.println("6. Kilometraje");
                System.out.println("7. Combustible");
                System.out.println("8. Capacidad de Combustible");
                if (nuevoVehiculo instanceof Auto) {
                    System.out.println("9. Cantidad de Puertas");
                    System.out.println("10. Sistema de Navegación");
                    System.out.println("11. Color");
                } else if (nuevoVehiculo instanceof Moto) {
                    System.out.println("9. Tipo de Moto");
                    System.out.println("10. Sistema de Audio");
                    System.out.println("11. Capacidad de Carga");
                } else if (nuevoVehiculo instanceof Camion) {
                    System.out.println("9. Capacidad de Carga");
                    System.out.println("10. Longitud");
                    System.out.println("11. Tipo de Carga");
                }
                System.out.println("0. Salir");
                int opcion = obtenerOpcionMenu(scanner);
                switch (opcion) {
                    case 1:
                        try{
                            System.out.println("Ingrese el nuevo estado (Si/No): ");
                            nuevoVehiculo.setEstado(validarSiONoBoolean(scanner.nextLine()));
                        }catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificacion: " + e.getMessage());
                        }
                        break;
                    case 2:
                        try{
                            System.out.println("Ingrese la nueva marca: ");
                            System.out.print("|-Marca: ");
                            String marca = scanner.nextLine();
                            validarMarca(marca);
                            nuevoVehiculo.setMarca(marca);
                        }catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificacion: " + e.getMessage());
                        }
                        break;
                    case 3:
                        try{
                            System.out.println("Ingrese el nuevo modelo: ");
                            System.out.print("|-Modelo: ");
                            String modelo = scanner.nextLine();
                            validarModelo(modelo);
                            nuevoVehiculo.setModelo(modelo);
                        }catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificacion: " + e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            System.out.println("Ingrese el nuevo precio: ");
                            System.out.print("|-Precio: ");
                            Double precio = validarPrecio(scanner.nextLine());
                            nuevoVehiculo.setPrecio(precio);
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificacion: " + e.getMessage());
                        }
                        break;
                    case 5:
                        try {
                            System.out.println("Ingrese el nuevo anio: ");
                            System.out.print("|-Anio: ");
                            Year anio = validarAnio(scanner.nextLine());
                            nuevoVehiculo.setAnio(anio);
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificacion: " + e.getMessage());
                        }
                        break;
                    case 6:
                        try {
                            System.out.println("Ingrese el nuevo kilometraje: ");
                            System.out.print("|-Kilometraje: ");
                            Integer kilometraje = validarKilometraje(scanner.nextLine());
                            nuevoVehiculo.setKilometraje(kilometraje);
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificacion: " + e.getMessage());
                        }
                        break;
                    case 7:
                        try {
                            System.out.println("Ingrese el nuevo combustible: ");
                            System.out.print("|-Combustible: ");
                            TipoDeCombustible combustible = validarTipoCombustible(scanner.nextLine());
                            nuevoVehiculo.setCombustible(combustible);
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificacion: " + e.getMessage());
                        }
                        break;
                    case 8:
                        try {
                            System.out.println("Ingrese la nueva capacidad de combustible: ");
                            System.out.print("|-Capacidad de Combustible: ");
                            Integer capacidadCombustible = validarCapacidadCombustible(scanner.nextLine());
                            nuevoVehiculo.setCapacidadCombustible(capacidadCombustible);
                        } catch (FormatoInvalidoException e) {
                            System.out.println("Error Modificacion: " + e.getMessage());
                        }
                        break;

                    case 9:
                        if (nuevoVehiculo instanceof Auto) {
                            try {
                                System.out.println("Ingrese la nueva cantidad de puertas: ");
                                System.out.print("|-Cantidad de Puertas: ");
                                Integer cantidadPuertas = validarCantidadDePuertas(scanner.nextLine());
                                ((Auto)nuevoVehiculo).setNumeroPuertas(cantidadPuertas);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        } else if (nuevoVehiculo instanceof Moto) {
                            try {
                                System.out.println("Ingrese el nuevo tipo de moto: ");
                                System.out.print("|-Tipo de Moto: ");
                                TipoDeMoto tipoDeMoto = validarTipoDeMoto(scanner.nextLine());
                                ((Moto)nuevoVehiculo).setTipoDeMoto(tipoDeMoto);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        } else if (nuevoVehiculo instanceof Camion) {
                            try {
                                System.out.println("Ingrese la nueva capacidad de carga: ");
                                System.out.print("|-Capacidad de Carga: ");
                                Integer capacidadCarga = validarCapacidadCargaMoto(scanner.nextLine());
                                ((Camion)nuevoVehiculo).setCapacidadCarga(capacidadCarga);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        }
                        break;

                    case 10:
                        if (nuevoVehiculo instanceof Auto) {
                            try {
                                System.out.println("Ingrese el Nuevo Sistema de Navegación: ¿Tiene sistema de navegación (Si/No)? ");
                                Boolean sistemaNavegacion = validarBooleanStr(scanner.nextLine());
                                ((Auto) nuevoVehiculo).setSistemaNavegacion(sistemaNavegacion);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        } else if (nuevoVehiculo instanceof Moto) {
                            try {
                                System.out.println("Ingrese el Nuevo Sistema de Audio: ¿Tiene sistema de audio (Si/No)? ");
                                Boolean sistemaAudio = validarBooleanStr(scanner.nextLine());
                                ((Moto) nuevoVehiculo).setSistemaAudio(sistemaAudio);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        } else if (nuevoVehiculo instanceof Camion) {
                            try {
                                System.out.println("Ingrese la nueva longitud: ");
                                Double longitud = validarLongitud(scanner.nextLine());
                                ((Camion) nuevoVehiculo).setLongitud(longitud);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        }
                        break;

                    case 11:
                        if (nuevoVehiculo instanceof Auto) {
                            try {
                                System.out.println("Ingrese el nuevo color: ");
                                System.out.print("|-Color: ");
                                String color = validarColor(scanner.nextLine());
                                ((Auto) nuevoVehiculo).setColor(color);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        } else if (nuevoVehiculo instanceof Moto) {
                            try {
                                System.out.println("Ingrese la nueva capacidad de Carga: ");
                                Integer capacidadCarga = validarCapacidadCargaMoto(scanner.nextLine());
                                ((Moto) nuevoVehiculo).setCapacidadCarga(capacidadCarga);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        } else if (nuevoVehiculo instanceof Camion) {
                            try {
                                System.out.println("Ingrese la nueva capacidad de Carga: ");
                                Integer capacidadCarga = validarCapacidadCargaMoto(scanner.nextLine());
                                ((Camion) nuevoVehiculo).setCapacidadCarga(capacidadCarga);
                            } catch (FormatoInvalidoException e) {
                                System.out.println("Error Modificacion: " + e.getMessage());
                            }
                        }
                        break;
                    case 0:
                        continuar = false;
                        Boolean exito = consecionaria.actualizarVehiculo(matriculaPrincipal, nuevoVehiculo);
                        if (exito) {
                            grabar(consecionaria.vehiculosToJSONArray(),"Vehiculos.JSON");
                        }else {
                            System.out.println("Vehículo no actualizado.");
                            System.out.println("Ocurrio un Error");
                        }
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor intente de nuevo.");
                }
            }
        } catch (VehiculoNoEncontradoException | FormatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void filtradosVehiculos(Consecionaria consecionaria) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("|-Filtrar Vehiculos-|");
            System.out.println("|-Si desea aplicar un filtro, ingrese Si/No-|");
            System.out.print("|-¿Desea filtrar por Estado? [Si/No]: ");
            String booleanTemp = validarSiONo(scanner.nextLine());
            System.out.print("|-¿Desea filtrar por Tipo de Vehiculo? [Si/No]: ");
            String tipoVehiculoTemp = validarSiONo(scanner.nextLine());
            System.out.print("|-¿Desea filtrar por Marca? [Si/No]: ");
            String marcaTemp = validarSiONo(scanner.nextLine());
            System.out.print("|-¿Desea filtrar por Precio? [Si/No]: ");
            String precioTemp = validarSiONo(scanner.nextLine());
            System.out.print("|-¿Desea filtrar por Anio? [Si/No]: ");
            String anioTemp = validarSiONo(scanner.nextLine());
            Boolean estado = null;
            TipoDeObject tipoVehiculo = null;
            String marca = null;
            Double precio = null;
            Year anio = null;
            try {
                if (booleanTemp != null && booleanTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese el Estado del Vehiculo-|");
                    System.out.print("|-Estado (true/si - false/no): ");
                    estado = validarSiONoBoolean(scanner.nextLine());
                }

                if (tipoVehiculoTemp != null && tipoVehiculoTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese el Tipo de Vehiculo-|");
                    System.out.print("|-Tipo de Vehiculo: ");
                    tipoVehiculo = validarTipoVehiculo(scanner.nextLine());
                }

                if (marcaTemp != null && marcaTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese la Marca del Vehiculo-|");
                    System.out.print("|-Marca: ");
                    marca = scanner.nextLine();
                    validarMarcaVoid(marca);
                }

                if (precioTemp != null && precioTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese el Precio del Vehiculo-|");
                    System.out.print("|-Precio (Decimal): ");
                    precio = validarPrecio(scanner.nextLine());
                }

                if (anioTemp != null && anioTemp.equalsIgnoreCase("si")) {
                    System.out.println("|-Ingrese el Anio del Vehiculo-|");
                    System.out.print("|-Anio: ");
                    anio = validarAnio(scanner.nextLine());
                }
                consecionaria.mostrarVehiculosFiltrados(consecionaria.buscarVehiculosFiltrado(estado,
                        marca, precio, anio, tipoVehiculo));
            } catch (FormatoInvalidoException e) {
                System.out.println("Dato del Filtro Incorrecto: " + e.getMessage());
            } catch (ListadoNoDisponibleException e) {
                System.out.println("Error de Listado: " + e.getMessage());
            }

        } catch (FormatoInvalidoException e) {
            System.out.println("Error De Filtro: " + e.getMessage());
        }
    }
    public static Vehiculo crearVehiculo() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Ingrese la Marca del Vehiculo: ");
            System.out.print("|-Marca: ");
            String marca = scanner.nextLine();
            validarMarcaVoid(marca);
            System.out.println("Ingrese el Modelo del Vehiculo: ");
            System.out.print("|-Modelo: ");
            String modelo = scanner.nextLine();
            validarModelo(modelo);

            System.out.println("Ingrese la Matricula del Vehiculo: ");
            System.out.print("|-Matricula: ");
            String matricula = scanner.nextLine();
            validarMatricula(matricula);
            matricula = matricula.toUpperCase();

            System.out.println("Ingrese el Precio del Vehiculo: ");
            System.out.print("|-Precio (Decimal): ");
            Double precio = validarPrecio(scanner.nextLine());

            System.out.println("Ingrese Anio del Vehiculo: ");
            System.out.print("|-Anio: ");
            Year anio = validarAnio(scanner.nextLine());

            System.out.println("Ingrese Kilometraje del Vehiculo: ");
            System.out.print("|-Kilometraje (Enteros): ");
            Integer kilometraje = validarKilometraje(scanner.nextLine());

            System.out.println("Ingrese Tipo de Combustible del Vehiculo");
            System.out.println("Tipos: Gasolina, Diesel, Electrico, Hibrido, Biocombustible");
            System.out.print("|-Tipo: ");
            TipoDeCombustible combustible = validarTipoCombustible(scanner.nextLine());

            System.out.println("Ingrese Capacidad De Combustible del Vehiculo: ");
            System.out.print("|-Capacidad de Combustible (Enteros): ");
            Integer capacidadCombustible = validarCapacidadCombustible(scanner.nextLine());

            System.out.println("|-Ingrese el Tipo de Vehiculo que desea Agregar-/Auto/Moto/Camion/");
            System.out.print("|-Tipo: ");
            TipoDeObject tipoVehiculo = validarTipoVehiculo(scanner.nextLine());
            Vehiculo vehiculo = null;

            switch (tipoVehiculo) {
                case AUTO:
                    System.out.println("|-Para la Creacion del Auto-|");
                    System.out.println("|-Porfavor Ingrese los siguientes datos...");
                    System.out.println("|-Ingrese la Cantidad de Puertas: ");
                    System.out.println("|-Cantidad de Puertas: ");
                    Integer cantidadPuertas = validarCantidadDePuertas(scanner.nextLine());
                    System.out.println("|-Ingrese si cuenta con Sistema de Navegacion");
                    System.out.print("|-Sistema de Navegacion Si/No: ");
                    Boolean sistemaNavegacion = validarBooleanStr(scanner.nextLine());
                    System.out.println("|-Ingrese el Color del Auto");
                    System.out.print("|-Color: ");
                    String colorStr = scanner.nextLine();
                    colorStr = validarColor(colorStr);
                    vehiculo = new Auto(true,marca,modelo,matricula,precio,anio,kilometraje,combustible,capacidadCombustible,colorStr,cantidadPuertas,sistemaNavegacion,tipoVehiculo);
                    break;

                case MOTO:
                    System.out.println("|-Para la Creación de la Moto-|");
                    System.out.println("|-Por favor ingrese los siguientes datos...");
                    System.out.println("|-Ingrese el Tipo de Moto: ");
                    System.out.println("|-Tipos: Deportiva, Clasica, Electrica");
                    System.out.print("|-Tipo de Moto: ");
                    TipoDeMoto tipoDeMoto = validarTipoDeMoto(scanner.nextLine());
                    System.out.println("|-Ingrese si cuenta con Sistema de Audio");
                    System.out.print("|-Sistema de Audio Si/No: ");
                    Boolean sistemaAudio = validarBooleanStr(scanner.nextLine());
                    System.out.println("|-Ingrese la Capacidad de Carga de la Moto: ");
                    System.out.print("|-Capacidad de Carga (Enteros): ");
                    Integer capacidadCarga = validarCapacidadCargaMoto(scanner.nextLine());
                    vehiculo = new Moto(true,marca,modelo,matricula,precio,anio,kilometraje,combustible,capacidadCombustible,tipoVehiculo,capacidadCarga,sistemaAudio,tipoDeMoto);
                    break;
                case CAMION:
                    System.out.println("|-Para la Creacion del Camion-|");
                    System.out.println("|-Porfavor Ingrese los siguientes datos...");
                    System.out.println("|-Ingrese la Capacidad de Carga del Camion: ");
                    System.out.print("|-Capacidad de Carga (Enteros): ");
                    Integer capacidadCargaCamion = validarCapacidadCargaCamion(scanner.nextLine());
                    System.out.println("|-Ingrese la longitud del Camion: ");
                    System.out.print("|-Longitud (Decimal): ");
                    Double longitud = validarLongitud(scanner.nextLine());
                    System.out.println("|-Ingrese el Tipo de Carga del Camion: ");
                    System.out.println("|-Tipos: Liviana, Pesada, Construccion, Alimentaria, Peligrosa, Materiales Fragiles");
                    System.out.print("|-Tipo de Carga: ");
                    TipoDeCarga tipoDeCarga = validarTipoDeCarga(scanner.nextLine());
                    vehiculo = new Camion(true,marca,modelo,matricula,precio,anio,kilometraje,combustible,capacidadCombustible,tipoVehiculo,capacidadCargaCamion,tipoDeCarga,longitud);
                    break;
                default:
                    System.out.println("Tipo de vehículo no reconocido.");
            }
            if (vehiculo != null) {
                System.out.println("Vehículo creado exitosamente");
            }
            return vehiculo;
        } catch (FormatoInvalidoException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
