package Models.Vehiculos;

import Enums.TipoDeObject;
import Enums.Vehiculos.TipoDeCombustible;
import Enums.Vehiculos.TipoDeMoto;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Year;
import java.util.logging.Logger;

public class Moto extends Vehiculo{
    private static final Logger logger = Logger.getLogger(Moto.class.getName());  // Logger para la subclase Moto

    private TipoDeMoto tipoDeMoto;
    private Boolean sistemaAudio;
    private Integer capacidadCarga;

    public Moto() {
        super();
    }

    public Moto(Boolean estado, String marca, String modelo,
                String matricula, Double precio, Year anio,
                Integer kilometraje, TipoDeCombustible combustible,
                Integer capacidadCombustible, TipoDeObject tipo, Integer capacidadCarga,
                Boolean sistemaAudio, TipoDeMoto tipoDeMoto) {
        super(estado, marca, modelo, matricula, precio, anio, kilometraje, combustible, capacidadCombustible, tipo);
        this.capacidadCarga = capacidadCarga;
        this.sistemaAudio = sistemaAudio;
        this.tipoDeMoto = tipoDeMoto;
    }

    public Integer getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(Integer capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public Boolean getSistemaAudio() {
        return sistemaAudio;
    }

    public void setSistemaAudio(Boolean sistemaAudio) {
        this.sistemaAudio = sistemaAudio;
    }

    public TipoDeMoto getTipoDeMoto() {
        return tipoDeMoto;
    }

    public void setTipoDeMoto(TipoDeMoto tipoDeMoto) {
        this.tipoDeMoto = tipoDeMoto;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = super.toJSON();
            jsonObject.put("capacidadCarga", this.capacidadCarga);
            jsonObject.put("sistemaAudio", this.sistemaAudio);
            jsonObject.put("tipoDeMoto", this.tipoDeMoto.name());
        } catch (JSONException e) {
            logger.severe("Error al convertir Moto a JSON: " + e.getMessage());
        }

        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        try {
            super.fromJSON(json);
            this.capacidadCarga = json.getInt("capacidadCarga");
            this.sistemaAudio = json.getBoolean("sistemaAudio");
            this.tipoDeMoto = TipoDeMoto.valueOf(json.getString("tipoDeMoto"));
        } catch (JSONException e) {
            logger.severe("Error al cargar Moto desde JSON: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                """
                |                  CARACTERÍSTICAS DE LA MOTO            |
                +--------------------------------------------------------+
                | Tipo de Moto:           %-30s |
                | Capacidad de Carga:     %-27d kg |
                | Sistema de Audio:       %-30s |
                +--------------------------------------------------------+
                """,
                tipoDeMoto,
                capacidadCarga,
                sistemaAudio ? "Sí" : "No"
        );
    }
}