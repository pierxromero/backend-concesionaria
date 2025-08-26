package Models.Vehiculos;

import Enums.TipoDeObject;
import Enums.Vehiculos.TipoDeCombustible;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Year;
import java.util.logging.Logger;


public class Auto extends Vehiculo {
    private static final Logger logger = Logger.getLogger(Auto.class.getName());
    private Integer numeroPuertas;
    private Boolean sistemaNavegacion; //true = si, false = no
    private String color;

    public Auto() {
        super();
    }

    public Auto(Boolean estado, String marca, String modelo, String matricula,
                Double precio, Year anio, Integer kilometraje,
                TipoDeCombustible combustible, Integer capacidadCombustible,
                String color, Integer numeroPuertas, Boolean sistemaNavegacion, TipoDeObject tipo){
        super(estado, marca, modelo, matricula, precio, anio, kilometraje, combustible, capacidadCombustible, tipo);
        this.color = color;
        this.numeroPuertas = numeroPuertas;
        this.sistemaNavegacion = sistemaNavegacion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNumeroPuertas() {
        return numeroPuertas;
    }

    public void setNumeroPuertas(Integer numeroPuertas) {
        this.numeroPuertas = numeroPuertas;
    }

    public Boolean getSistemaNavegacion() {
        return sistemaNavegacion;
    }

    public void setSistemaNavegacion(Boolean sistemaNavegacion) {
        this.sistemaNavegacion = sistemaNavegacion;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = super.toJSON();
            jsonObject.put("color", this.color);
            jsonObject.put("numeroPuertas", this.numeroPuertas);
            jsonObject.put("sistemaNavegacion", this.sistemaNavegacion);
        } catch (JSONException e) {
           logger.severe("Error al convertir Auto a JSON: " + e.getMessage());
        }

        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        try {
            super.fromJSON(json);
            this.color = json.getString("color");
            this.numeroPuertas = json.getInt("numeroPuertas");
            this.sistemaNavegacion = json.getBoolean("sistemaNavegacion");
        }catch (JSONException e){
            logger.severe("Error al cargar Auto desde JSON: " + e.getMessage());
        }

    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                """
                |                  CARACTERÍSTICAS DEL AUTO              |
                +--------------------------------------------------------+
                | Color:                  %-30s |
                | N° Puertas:             %-30d |
                | Navegación:             %-30s |
                +--------------------------------------------------------+
                """,
                color,
                numeroPuertas,
                sistemaNavegacion ? "Sí" : "No"
        );
    }
}