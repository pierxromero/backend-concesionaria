package Models.Compras;

import Enums.Compras.Plazo;
import Interfaz.JsonInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

import static Enums.Compras.Plazo.DOCE_MESES;
import static Enums.Compras.Plazo.VEINTICUATRO_MESES;

public class PlanCompra implements JsonInterface {
    private static final Logger logger = Logger.getLogger(PlanCompra.class.getName());
    private Plazo plazo; //User
    private Double montoInicial; //User
    private Double interes; //System
    private Double montoTotal; //System

    public PlanCompra() {
    }

    public PlanCompra(Double interes, Double montoInicial, Double montoTotal, Plazo plazo) {
        this.interes = interes;
        this.montoInicial = montoInicial;
        this.montoTotal = montoTotal;
        this.plazo = plazo;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(Double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Plazo getPlazo() {
        return plazo;
    }

    public void setPlazo(Plazo plazo) {
        this.plazo = plazo;
    }

    @Override
    public void fromJSON(JSONObject json) {
        try {
            this.interes = json.getDouble("interes");
            this.montoInicial = json.getDouble("montoInicial");
            this.montoTotal = json.getDouble("montoTotal");
            this.plazo = Plazo.valueOf(json.getString("plazo"));
        } catch (JSONException e) {
            logger.severe("Error al cargar PlanCompra desde JSON: " + e.getMessage());
        }
    }

    @Override
    public JSONObject toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("interes", this.interes);
            jsonObject.put("montoInicial", this.montoInicial);
            jsonObject.put("montoTotal", this.montoTotal);
            jsonObject.put("plazo", this.plazo.name());
            return jsonObject;
        } catch (JSONException e) {
            logger.severe("Error al convertir PlanCompra a JSON: " + e.getMessage());
        }
        return null;
    }
    public Double calcularInteres() {
        switch (getPlazo()) {
            case DOCE_MESES:
                return 0.40;
            case VEINTICUATRO_MESES:
                return 0.60;
            case TREINTA_Y_SEIS_MESES:
                return 0.80;
            default:
                return 0.0; // Caso por defecto
        }
    }

    public Double calcularCuotas() {
        // Determinar el número de meses según el plazo
        int meses = 12; // Valor por defecto
        switch (getPlazo()) {
            case VEINTICUATRO_MESES:
                meses = 24;
                break;
            case TREINTA_Y_SEIS_MESES:
                meses = 36;
                break;
        }
        return (double) meses;
    }
    @Override
    public String toString() {
        return String.format(
                """
                        +--------------------------------------------------------+
                        |                  DETALLES DEL PLAN DE COMPRA           |
                        +--------------------------------------------------------+
                        | Interés:                 %-30.2f |
                        | Plazo:                   %-30s |
                        | Monto Inicial:           %-30.2f |
                        | Monto Total:             %-30.2f |
                        +--------------------------------------------------------+
                        """,
                interes,
                plazo,
                montoInicial,
                montoTotal
        );

    }
}
