package Models.Persona;

import Enums.Personas.Genero;
import Enums.Personas.Sector;
import Enums.TipoDeObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class Empleado extends Personas {
    private static final Logger logger = Logger.getLogger(Empleado.class.getName());
    private Sector sector;
    private Double sueldoBase;
    private Integer Antiguedad;
    private String contrasena;

    public Empleado() {
        super();
    }

    public Empleado(TipoDeObject tipoDeObject, String apellido, String direccion, String dni, Boolean estado,
                    Genero genero, String nombre, Integer antiguedad, Sector sector,
                    Double sueldoBase, String contrasena) {
        super(apellido, direccion, dni, estado, genero, nombre, tipoDeObject);
        Antiguedad = antiguedad;
        this.sector = sector;
        this.sueldoBase = sueldoBase;
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer getAntiguedad() {
        return Antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        Antiguedad = antiguedad;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Double getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(Double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    @Override
    public void fromJSON(JSONObject json) {
        try {
            super.fromJSON(json);
            this.Antiguedad = json.getInt("Antiguedad");
            this.sector = Sector.valueOf(json.getString("sector"));
            this.sueldoBase = json.getDouble("sueldoBase");
            this.contrasena = json.getString("contrasena");
        } catch (JSONException e) {
            logger.severe("Error al cargar Empleado desde JSON: " + e.getMessage());
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = super.toJSON();
            jsonObject.put("Antiguedad", this.Antiguedad);
            jsonObject.put("sector", this.sector.name());
            jsonObject.put("sueldoBase", this.sueldoBase);
            jsonObject.put("contrasena", this.contrasena);
            return jsonObject;
        } catch (JSONException e) {
            logger.severe("Error al convertir Empleado a JSON: " + e.getMessage());
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                """
                        | Sector:                 %-30s |
                        | Sueldo Base:            $%,29.2f |
                        | Antigüedad:             %-25d años |
                        | Contraseña:             %-30s |
                        +--------------------------------------------------------+
                        """,
                sector,
                sueldoBase,
                Antiguedad,
                contrasena
                //"********" // Ocultar contraseña por seguridad
        );
    }
}
