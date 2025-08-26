package Models.Persona;

import Enums.Personas.Genero;
import Enums.TipoDeObject;
import Interfaz.JsonInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.logging.Logger;

public abstract class Personas implements JsonInterface{
    private static final Logger logger = Logger.getLogger(Personas.class.getName());
    private static Integer idPersona = 0;
    private Boolean estado;
    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private Genero genero;
    private TipoDeObject tipo;
    public Personas() {
        idPersona++;
    }
    public Personas(String apellido, String direccion, String dni,
                    Boolean estado, Genero genero, String nombre, TipoDeObject tipo) {
        idPersona++;
        this.apellido = apellido;
        this.direccion = direccion;
        this.dni = dni;
        this.estado = estado;
        this.genero = genero;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public TipoDeObject getTipo() {
        return tipo;
    }
    public void setTipo(TipoDeObject tipo) {
        this.tipo = tipo;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getDni() {
        return dni;
    }
    public Boolean getEstado() {
        return estado;
    }
    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    public Integer getContador() {
        return idPersona;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public void fromJSON(JSONObject json) {
        try {
            this.apellido = json.getString("apellido");
            this.direccion = json.getString("direccion");
            this.dni = json.getString("dni");
            this.estado = json.getBoolean("estado");
            this.genero = Genero.valueOf(json.getString("genero"));
            this.nombre = json.getString("nombre");
            this.tipo = TipoDeObject.valueOf(json.getString("tipo"));
        }catch (JSONException e) {
            logger.severe("Error al cargar Persona desde JSON: " + e.getMessage());
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apellido", this.apellido);
            jsonObject.put("direccion", this.direccion);
            jsonObject.put("dni", this.dni);
            jsonObject.put("estado", this.estado);
            jsonObject.put("genero", this.genero.name());
            jsonObject.put("nombre", this.nombre);
            jsonObject.put("tipo", this.tipo.name());
            return jsonObject;
        } catch (JSONException e) {
            logger.severe("Error al convertir Persona a JSON: " + e.getMessage());
        }
        return jsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personas personas)) return false;
        return Objects.equals(getDni(), personas.getDni());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDni());
    }

    public String toString() {
        return String.format(
                """
                +--------------------------------------------------------+
                |                  DETALLES DE LA PERSONA                |
                +--------------------------------------------------------+
                | Nombre:                 %-30s |
                | Apellido:               %-30s |
                | DNI:                    %-30s |
                | Dirección:              %-30s |
                | Género:                 %-30s |
                | Estado:                 %-30s |
                | Tipo:                   %-30s |
                +--------------------------------------------------------+
                """,
                nombre,
                apellido,
                dni,
                direccion,
                genero,
                estado ? "Activo" : "Inactivo",
                tipo
        );
    }
}
