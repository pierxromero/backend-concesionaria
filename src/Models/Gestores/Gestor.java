package Models.Gestores;

import Enums.Personas.Genero;
import Enums.TipoDeObject;
import Exceptions.*;
import Exceptions.Personas.PersonaNoEncontradaException;
import Exceptions.Personas.PersonaNullException;
import Exceptions.Personas.PersonaYaRegistradaException;
import Exceptions.Vehiculos.VehiculoNoEncontradoException;
import Exceptions.Vehiculos.VehiculoNullException;
import Exceptions.Vehiculos.VehiculoYaRegistradoException;
import Models.Persona.Personas;
import Models.Vehiculos.Vehiculo;
import org.json.JSONArray;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import static Models.Gestores.JSONUtil.LeerArchivoPersonas;
import static Models.Gestores.JSONUtil.LeerArchivoVehiculos;

//=================== RAZON DE GESTOR ===================
/*La clase gestora utiliza tipos genericos para manejar tanto la coleccion
 *de personas (P), como la de vehiculos (V)                               */
//=======================================================
public class Gestor<V extends Vehiculo, P extends Personas> {
    private Set<V> vehiculos;
    private Map<String, P> personas;
    //   Constructor Default - Vacio Sin Parametros -
    public Gestor() {
        this.vehiculos = new HashSet<>();
        this.personas = new HashMap<>();
    }
    //  Constructor Parametrizado - Recibe 2 Colecciones (Esto en caso de leerlo
    //  desde un archivo json)
    public Gestor(Set<V> vehiculos, Map<String, P> personas) {
        this.vehiculos = vehiculos;
        this.personas = personas;
    }
    // Constructor Parametrizado (1 solo Parametro) - Recibe 1 Coleccion (Desde Archivo json)
    public Gestor(Set<V> vehiculos) {
        this.vehiculos = vehiculos;
        this.personas = new HashMap<>();
    }
    // Constructor Parametrizado (1 solo Parametro) - Recibe 1 Coleccion (Desde Archivo json)
    public Gestor(Map<String, P> personas) {
        this.vehiculos = new HashSet<>();
        this.personas = personas;
    }
    /* ==================================================================  *
     * Metodos de Vehiculos                                                *
     * ==================================================================* */
    public void agregarVehiculo(V vehiculo)throws VehiculoYaRegistradoException {
        if (vehiculos.contains(vehiculo)) {
            throw new VehiculoYaRegistradoException("Vehiculo ya registrado");
        } else if (vehiculo == null) {
            throw new VehiculoNullException("Vehiculo no puede ser nulo");
        }
        vehiculos.add(vehiculo);
    }
    public void eliminarVehiculo(V vehiculo) throws VehiculoNoEncontradoException {
        if (!vehiculos.contains(vehiculo)) {
            throw new VehiculoNoEncontradoException("Vehiculo no encontrado");
        }
        vehiculos.remove(vehiculo);
    }
    /* Busqueda de eliminacion Mediante la Matricula */
    public void eliminarVehiculoPorMatricula(String matricula) throws VehiculoNoEncontradoException {
        for (V vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equals(matricula)) {
                vehiculos.remove(vehiculo);
                return;
            }
        }
        throw new VehiculoNoEncontradoException("Vehiculo no encontrado");
    }
    /* Busqueda Mediante la Matricula */
    public V buscarVehiculoPorMatricula(String matricula) throws VehiculoNoEncontradoException {
        for (V vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equals(matricula)) {
                return vehiculo;
            }
        }
        throw new VehiculoNoEncontradoException("Vehiculo no encontrado");
    }
    //* Metodo para buscar vehiculos de acuerdo a filtros. Podemos pasar por parametro null en caso de que no nos interese filtrar por algun criterio */
    /* Se aplican 2 grandes filtros a la hora de obtener el set */
    public Set<V> buscarVehiculos(Boolean estado, String marca, Double precio,
                                  Year anio, TipoDeObject tipo) {
        return vehiculos.stream()
                .filter(vehiculo ->
                        // Primero filtrar por estado
                        (estado == null || estado.equals(vehiculo.getEstado()))
                )
                .filter(vehiculo ->
                        // Luego filtrar por tipo si el estado ya está filtrado
                        (tipo == null || tipo.equals(vehiculo.getTipo()))
                )
                .filter(vehiculo ->
                        // Después filtrar por los otros criterios de manera opcional
                                (marca == null || vehiculo.getMarca().equalsIgnoreCase(marca)) &&
                                (precio == null || precio.equals(vehiculo.getPrecio())) &&
                                (anio == null || anio.equals(vehiculo.getAnio()))
                )
                .collect(Collectors.toSet());
    }
    /* Muestra el set resultado del filtrado de vehiculos */
    public void mostrarVehiculosFiltrados(Set<V> vehiculosFiltrados) throws ListadoNoDisponibleException {
        if (vehiculosFiltrados.isEmpty()) {
            throw new ListadoNoDisponibleException("Listado de Vehiculos no disponible");
        } else {
            System.out.println("Vehiculos disponibles:");
            int indice = 1;
            for (V vehiculo : vehiculosFiltrados) {
                System.out.println(indice + ". " + vehiculo);
                indice++;
            }
        }
    }
    /* Mostrar Todos Los Vehiculos */
    public void listarVehiculos() throws ListadoNoDisponibleException {
        if (vehiculos.isEmpty()) {
            throw new ListadoNoDisponibleException("Listado de Vehiculos no disponible");
        } else {
            System.out.println("Vehículos: ");
            for (V vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        }
    }
    public int contarVehiculos() {
        return vehiculos.size();
    }
    public int contarVehiculosConEstadoTrue() {
        return (int) vehiculos.stream()
                .filter(vehiculo -> Boolean.TRUE.equals(vehiculo.getEstado()))
                .count();
    }
    public int contarPersonasConEstadoTrue() {
        return (int) personas.values().stream()
                .filter(persona -> Boolean.TRUE.equals(persona.getEstado()))
                .count();
    }
    /* Actualizar Vehiculo */
    public boolean actualizarVehiculo(String matricula, V nuevoVehiculo) throws VehiculoNoEncontradoException {
        for (V vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equals(matricula)) {
                vehiculos.remove(vehiculo);
                vehiculos.add(nuevoVehiculo);
                System.out.println("Vehículo actualizado con éxito.");
                return true;
            }
        }
        throw new VehiculoNoEncontradoException("Vehiculo no encontrado");
    }
    /* Existe Vehiculo */
    public boolean existeVehiculo(String matricula) {
        return vehiculos.stream()
                .anyMatch(vehiculo -> vehiculo.getMatricula().equals(matricula));
    }
    /* Devolver JSON de Vehiculos */
    public JSONArray VehiculosToJSONArray (){
        JSONArray jsonArray = new JSONArray();
        for (V vehiculo : vehiculos) {
            jsonArray.put(vehiculo.toJSON());
        }
        return jsonArray;
    }
    public void leerVehiculos(String path){
        Set<V> vehiculosLeidos = (Set<V>) LeerArchivoVehiculos(path);
        vehiculos = new HashSet<>(vehiculosLeidos);
    }
    /* ==================================================================  *
     * Metodos de Personas                                                *
     * ==================================================================* */
    public void agregarPersona(P persona) throws PersonaYaRegistradaException, PersonaNullException {
        if (personas.containsKey(persona.getDni())) {
            throw new PersonaYaRegistradaException("Persona ya registrada");
        } else if (persona == null) {
            throw new PersonaNullException("Persona pasada nula");
        }
        personas.put(persona.getDni(), persona);
    }
    /* Eliminar Persona */
    public void eliminarPersona(P persona) throws PersonaNoEncontradaException {
        if (!personas.containsKey(persona.getDni())) {
            throw new PersonaNoEncontradaException("Persona no encontrada");
        }
        personas.remove(persona.getDni());
    }
    /* Eliminar Persona mediante el DNI */
    public void eliminarPersonaPorDni(String dni) throws PersonaNoEncontradaException {
        if (!personas.containsKey(dni)) {
            throw new PersonaNoEncontradaException("Persona no encontrada");
        }
        personas.remove(dni);
    }
    /* Buscar Persona mediante el DNI */
    public Personas buscarPersonaPorDni(String dni) throws PersonaNoEncontradaException {
        if (personas.containsKey(dni)) {
            return personas.get(dni);
        }
        throw new PersonaNoEncontradaException("Persona no encontrada");
    }
    /* Buscar Personas con filtros */
    public Set<Personas> buscarPersonasFiltro(Boolean estado, String apellido,
                                        Genero genero, TipoDeObject tipo) {
        return personas.values().stream()
                .filter(persona ->
                        /* Primero filtrar por estado (disponibilidad) */
                        (estado == null || estado.equals(persona.getEstado()))
                )
                .filter(persona ->
                        /* Luego filtrar por tipo si el estado ya está filtrado */
                        (tipo == null || tipo.equals(persona.getTipo()))
                )
                .filter(persona ->
                        /* Después filtrar por los otros criterios de manera opcional */
                                (apellido == null || persona.getApellido().equalsIgnoreCase(apellido)) &&
                                (genero == null || genero.equals(persona.getGenero()))  // Comparación de enum Genero
                )
                .collect(Collectors.toSet());
    }
    /* Mostrar Personas filtradas */
    public void mostrarPersonasFiltradas(Set<Personas> personasFiltradas) throws ListadoNoDisponibleException {
        if (personasFiltradas.isEmpty()) {
            throw new ListadoNoDisponibleException("Listado de personas no disponible");
        } else {
            System.out.println("Personas disponibles:");
            int indice = 1;
            for (Personas persona : personasFiltradas) {
                System.out.println(persona);
                indice++;
            }
        }
    }
    /* Listar todas las Personas */
    public void listarPersonas() throws ListadoNoDisponibleException {
        if (personas.isEmpty()) {
            throw new ListadoNoDisponibleException("Listado de personas no disponible");
        } else {
            System.out.println("Personas: ");
            for (Personas persona : personas.values()) {
                System.out.println(persona);
            }
        }
    }
    /* Contar Personas */
    public int contarPersonas() {
        return personas.size();
    }
    /* Actualizar Persona */
    public boolean actualizarPersona(String dni, P nuevaPersona) throws PersonaNoEncontradaException {
        if (personas.containsKey(dni)) {
            personas.put(dni, nuevaPersona);
            System.out.println("Persona actualizada con éxito.");
            return true;
        }
        throw new PersonaNoEncontradaException("Persona no encontrada");
    }
    /* Verificar si existe una Persona */
    public boolean existePersona(String dni) {
        return personas.containsKey(dni);
    }
    /* Convertir las Personas a JSON */
    public JSONArray personasToJSONArray() {
        JSONArray jsonArray = new JSONArray();
        for (Personas persona : personas.values()) {
            jsonArray.put(persona.toJSON());
        }
        return jsonArray;
    }
    public void leerPersonas(String path){
        Map<String, P> personasLeidas = (Map<String, P>) LeerArchivoPersonas(path);
        personas = new HashMap<>(personasLeidas);
    }
}
