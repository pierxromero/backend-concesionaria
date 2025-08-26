package Models;

import Enums.*;
import Enums.Compras.TipoDePago;
import Enums.Personas.Genero;
import Exceptions.*;
import Exceptions.Compras.CompraNoEncontradaException;
import Exceptions.Compras.CompraYaRegistradaException;
import Exceptions.Compras.CuotaYaPagadaException;
import Exceptions.Compras.PagoRechazadoException;
import Exceptions.Personas.PersonaNoEncontradaException;
import Exceptions.Personas.PersonaNullException;
import Exceptions.Personas.PersonaYaRegistradaException;
import Exceptions.Vehiculos.VehiculoNoEncontradoException;
import Exceptions.Vehiculos.VehiculoNullException;
import Exceptions.Vehiculos.VehiculoYaRegistradoException;
import Models.Compras.Compra;
import Models.Gestores.Gestor;
import Models.Persona.Cliente;
import Models.Persona.Personas;
import Models.Vehiculos.Vehiculo;
import org.json.JSONArray;

import java.time.Year;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static Models.Gestores.JSONUtil.LeerArchivoCompras;

//- Concesionaria *
public class Consecionaria {
    /*=====================ATRIBUTOS=================*/
    /**/private String nombre; ///Nombre del Local
    /**/private String direccion; ///Direccion del Local
    /**/private String telefono;  ///Numero del Local
    /**/private Gestor<Vehiculo, Personas> inventario; ///Almacenamiento de Todos los Vehiculos y Personas
    /**/private HashSet<Compra> ListaCompras; ///Almacenamiento de Todas las Compras

    /*=====================CONSTRUCTORES=================*/
    public Consecionaria() {
    } //Constructor por Defecto

    public Consecionaria(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        inventario = new Gestor<Vehiculo, Personas>();
        ListaCompras = new HashSet<>();
    } //Constructor con Parámetros

    /*=====================Getters y Setters=================*/
    /**/
    public String getNombre() {
        return nombre;
    }

    /**/
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**/
    public String getDireccion() {
        return direccion;
    }

    /**/
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**/
    public String getTelefono() {
        return telefono;
    }

    /**/
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**/
    public HashSet<Compra> getListaCompras() {
        return ListaCompras;
    }

    /**/
    public void setListaCompras(HashSet<Compra> listaCompras) {
        ListaCompras = listaCompras;
    }

    /**/
    public Gestor<Vehiculo, Personas> getInventario() {
        return inventario;
    }

    /**/
    public void setInventario(Gestor<Vehiculo, Personas> inventario) {
        this.inventario = inventario;
    }
    /*=====================MÉTODOS DELEGADOS=================*/

    /* Métodos de gestión de vehículos */
    public void darDeBajaVehiculo(String matricula) throws VehiculoNoEncontradoException {
        // Verificar si el vehículo existe en el inventario
        Vehiculo vehiculoEncontrado = buscarVehiculoPorMatricula(matricula);
        vehiculoEncontrado.setEstado(false);
    }
    public void agregarVehiculo(Vehiculo vehiculo) throws VehiculoYaRegistradoException, VehiculoNullException {
        inventario.agregarVehiculo(vehiculo);
    }

    public void eliminarVehiculo(Vehiculo vehiculo) throws VehiculoNoEncontradoException {
        inventario.eliminarVehiculo(vehiculo);
    }

    public void eliminarVehiculoPorMatricula(String matricula) throws VehiculoNoEncontradoException {
        inventario.eliminarVehiculoPorMatricula(matricula);
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) throws VehiculoNoEncontradoException {
        return inventario.buscarVehiculoPorMatricula(matricula);
    }

    public Set<Vehiculo> buscarVehiculosFiltrado(Boolean estado, String marca, Double precio,
                                                 Year anio, TipoDeObject tipo) {
        return inventario.buscarVehiculos(estado, marca, precio, anio, tipo);
    }

    public void mostrarVehiculosFiltrados(Set<Vehiculo> vehiculosFiltrados) throws ListadoNoDisponibleException {
        inventario.mostrarVehiculosFiltrados(vehiculosFiltrados);
    }

    public void listarVehiculos() throws ListadoNoDisponibleException {
        inventario.listarVehiculos();
    }

    public int contarVehiculos() {
        return inventario.contarVehiculos();
    }

    public int contarVehiculosDisponibles() {
        return inventario.contarVehiculosConEstadoTrue();
    }
    public int contarPersonasDisponibles() {
        return inventario.contarPersonasConEstadoTrue();
    }

    public boolean actualizarVehiculo(String matricula, Vehiculo nuevoVehiculo) throws VehiculoNoEncontradoException {
        return inventario.actualizarVehiculo(matricula, nuevoVehiculo);
    }

    public boolean existeVehiculo(String matricula) {
        return inventario.existeVehiculo(matricula);
    }

    public JSONArray vehiculosToJSONArray() {
        return inventario.VehiculosToJSONArray();
    }

    public void leerVehiculos(String path) {
        inventario.leerVehiculos(path);
    }

    public void leerPersonas(String path) {
        inventario.leerPersonas(path);
    }

    /* Métodos de gestión de personas */
    public void agregarPersona(Personas persona) throws PersonaYaRegistradaException, PersonaNullException {
        inventario.agregarPersona(persona);
    }

    public void eliminarPersona(Personas persona) throws PersonaNoEncontradaException {
        inventario.eliminarPersona(persona);
    }

    public void eliminarPersonaPorDni(String dni) throws PersonaNoEncontradaException {
        inventario.eliminarPersonaPorDni(dni);
    }

    public Personas buscarPersonaPorDni(String dni) throws PersonaNoEncontradaException {
        return inventario.buscarPersonaPorDni(dni);
    }

    public Set<Personas> buscarPersonasFiltro(Boolean estado, String apellido,
                                         Genero genero, TipoDeObject tipo) {
        return inventario.buscarPersonasFiltro(estado,apellido,genero,tipo);
    }

    public void mostrarPersonasFiltradas(Set<Personas> personasFiltradas) throws ListadoNoDisponibleException {
        inventario.mostrarPersonasFiltradas(personasFiltradas);
    }

    public void listarPersonas() throws ListadoNoDisponibleException {
        inventario.listarPersonas();
    }

    public int contarPersonas() {
        return inventario.contarPersonas();
    }

    public boolean actualizarPersona(String dni, Personas nuevaPersona) throws PersonaNoEncontradaException {
        return inventario.actualizarPersona(dni, nuevaPersona);
    }

    public boolean existePersona(String dni) {
        return inventario.existePersona(dni);
    }

    public JSONArray personasToJSONArray() {
        return inventario.personasToJSONArray();
    }


    /* Métodos de gestión de compras */

    //Metodo que simula una validacion de pago. Aleatoriamente acepta o rechaza el pago
    private boolean validarPago() {
        Random random = new Random();

        return random.nextBoolean();
    }

    public void AgregarCompra(Compra compra) throws PersonaNoEncontradaException, VehiculoNoEncontradoException, PagoRechazadoException, CompraYaRegistradaException {
        if (!inventario.existePersona(compra.getCliente().getDni())) {
            throw new PersonaNoEncontradaException("Cliente no registrado en el sistema");
        }

        if (!inventario.existeVehiculo(compra.getVehiculo().getMatricula())) {
            throw new VehiculoNoEncontradoException("Vehiculo no registrado en el sistema");
        }

//        if (!validarPago()) {
//            throw new PagoRechazadoException("Error el pago fue rechazado");
//        }

        if (ListaCompras.contains(compra)) {
            throw new CompraYaRegistradaException("Esta compra ya fue registrada.");
        }
        ListaCompras.add(compra);
    }

    public void eliminarCompra(Compra compra) throws CompraNoEncontradaException {
        if (!ListaCompras.contains(compra)) {
            throw new CompraNoEncontradaException("Compra no encontrada en el registro");
        }
        ListaCompras.remove(compra);
    }

    public void pagarCuota(Compra compra) throws CuotaYaPagadaException, PagoRechazadoException, CompraNoEncontradaException {

        if (!ListaCompras.contains(compra)) {
            throw new CompraNoEncontradaException("Compra no encontrada en el sistema");
        }

        if (compra.getFactura().getCuotasRestantes() <= 0) {
            throw new CuotaYaPagadaException("No hay cuotas restantes para pagar");
        }

        if (!validarPago()) {
            throw new PagoRechazadoException("El pago fue rechazado");
        }

        Integer cuotasRestantes = compra.getFactura().getCuotasRestantes();
        compra.getFactura().setCuotasRestantes(cuotasRestantes - 1);

        if (compra.getFactura().getCuotasRestantes() == 0) {
            System.out.println("Todas las cuotas han sido pagadas.");
        } else {
            System.out.println("Cuota pagada con exito. Quedan " + compra.getFactura().getCuotasRestantes() + " cuotas.");
        }
    }

    public Set<Compra> buscarComprasEfectivoPorCliente(Cliente cliente) {
        return ListaCompras.stream()
                .filter(compra -> compra.getCliente().equals(cliente) && !compra.getEstado() && compra.getTipoDePago() == TipoDePago.EFECTIVO)
                .collect(Collectors.toSet());
    }

    public Set<Compra> buscarComprasCuotasPorCliente(Cliente cliente) {
        return ListaCompras.stream()
                .filter(compra -> compra.getCliente().equals(cliente) && !compra.getEstado() && compra.getTipoDePago() == TipoDePago.CREDITO)
                .collect(Collectors.toSet());
    }

    //Filtra las compras por Vehiculo
    public Set<Compra> buscarComprasPorVehiculo(Vehiculo vehiculo) {
        return ListaCompras.stream()
                .filter(compra -> compra.getVehiculo().equals(vehiculo))
                .collect(Collectors.toSet());
    }

    //Mostrar los resultados de las busquedas:
    public void mostrarComprasFiltradas(Set<Compra> comprasFiltradas) throws ListadoNoDisponibleException {
        if (comprasFiltradas.isEmpty()) {
            throw new ListadoNoDisponibleException("No se encontraron compras que cumplan los criterios");
        } else {
            System.out.println("Compras:");
            int indice = 1;
            for (Compra compra : comprasFiltradas) {
                System.out.println(indice + ". " + compra);
                indice++;
            }
        }
    }

    public void listarCompras() throws ListadoNoDisponibleException {
        if (ListaCompras.isEmpty()) {
            throw new ListadoNoDisponibleException("No hay compras registradas");
        } else {
            System.out.println("Compras en el sistema: ");
            for (Compra compra : ListaCompras) {
                System.out.println(compra);
            }
        }
    }

    public int contarCompras() {
        return ListaCompras.size();
    }

    public JSONArray ComprasToJSONArray() {
        JSONArray jsonArray = new JSONArray();
        for (Compra compra : ListaCompras) {
            jsonArray.put(compra.toJSON());
        }
        return jsonArray;
    }
    public void leerCompras(String archiCompras) {
        ListaCompras = LeerArchivoCompras(archiCompras);
    }

    /*=====================TOSTRING=================*/
    @Override
    public String toString() {
        return "Consecionaria{" +
                "Nombre='" + nombre + '\'' +
                ", Dirección='" + direccion + '\'' +
                ", Teléfono='" + telefono + '\'' +
                '}';
    }
}