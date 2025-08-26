package Models.Compras;

import Enums.Compras.TipoDePago;
import Interfaz.JsonInterface;
import Models.Persona.Cliente;
import Models.Persona.Empleado;
import Models.Vehiculos.Auto;
import Models.Vehiculos.Camion;
import Models.Vehiculos.Moto;
import Models.Vehiculos.Vehiculo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class Compra implements JsonInterface {
    private static final Logger logger = Logger.getLogger(Compra.class.getName());
    private String codigoID; //System
    private Boolean estado; //true = pagado, false = no pagado //System
    private Empleado empleado; //System
    private Cliente cliente; //System
    private Vehiculo vehiculo; //System
    private TipoDePago tipoDePago; //User
    private Factura factura; //System
    private PlanCompra planCompra; //Puede ser NULL
    ///Constructor que recibe PLAN DE COMPRA
    public Compra(){
        this.codigoID = generarIDunico();
    }
    public Compra(Cliente cliente, Empleado empleado, Boolean estado,
                  Factura factura, PlanCompra planCompra, TipoDePago tipoDePago, Vehiculo vehiculo) {
        this.cliente = cliente;
        this.codigoID = generarIDunico();
        this.empleado = empleado;
        this.estado = estado;
        this.factura = factura;
        this.planCompra = planCompra;
        this.tipoDePago = tipoDePago;
        this.vehiculo = vehiculo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compra compra)) return false;
        return Objects.equals(getCodigoID(), compra.getCodigoID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCodigoID());
    }

    /// ID UNICO LIMITADO A 10 CARACTERES
    private String generarIDunico(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
    public Cliente getCliente() {
        return cliente;
    }
    public Factura getFactura() {
        return factura;
    }
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    @Override
    public JSONObject toJSON() {
        JSONObject principal = new JSONObject();
        try {
            principal.put("codigoID", this.codigoID);
            principal.put("estado", this.estado);
            principal.put("tipoDePago", this.tipoDePago.name());
            JSONObject facturaJSON = factura.toJSON();
            principal.put("factura", facturaJSON);
            if (this.planCompra != null) {
                JSONObject planCompraJSON = planCompra.toJSON();
                principal.put("planCompra", planCompraJSON);
            }
            JSONObject vehiculoJSON = vehiculo.toJSON();
            principal.put("vehiculo", vehiculoJSON);
            JSONObject clienteJSON = cliente.toJSON();
            principal.put("cliente", clienteJSON);
            JSONObject empleadoJSON = empleado.toJSON();
            principal.put("empleado", empleadoJSON);
            return principal;
        } catch (JSONException e) {
            logger.severe("Error al convertir Compra a JSON: " + e.getMessage());
        }
        return principal;
    }
    @Override
    public void fromJSON(JSONObject json) {
        try {
            // Acceder correctamente al tipo de vehículo
            if (json.has("vehiculo")) {
                JSONObject vehiculoJSON = json.getJSONObject("vehiculo");

                // Asegurarse de que tipo esté presente en el objeto vehiculo
                if (vehiculoJSON.has("tipo")) {
                    String tipoVehiculo = vehiculoJSON.getString("tipo");

                    // Crear el vehículo correspondiente según el tipo
                    if ("AUTO".equals(tipoVehiculo)) {
                        this.vehiculo = new Auto();  // Crear un Auto
                    } else if ("CAMION".equals(tipoVehiculo)) {
                        this.vehiculo = new Camion();  // Crear un Camion
                    } else if ("MOTO".equals(tipoVehiculo)) {
                        this.vehiculo = new Moto();  // Crear una Moto
                    } else {
                        logger.warning("Tipo de vehículo desconocido: " + tipoVehiculo);
                    }
                } else {
                    logger.severe("Campo 'tipo' no encontrado en 'vehiculo'.");
                }
            }

            // Cargar el resto de los atributos de la compra
            this.codigoID = json.getString("codigoID");
            this.estado = json.getBoolean("estado");
            this.tipoDePago = TipoDePago.valueOf(json.getString("tipoDePago"));
            this.factura = new Factura();
            this.planCompra = null;
            this.cliente = new Cliente();
            this.empleado = new Empleado();
            factura.fromJSON(json.getJSONObject("factura"));
            if (json.has("planCompra")) {
                this.planCompra = new PlanCompra();
                planCompra.fromJSON(json.getJSONObject("planCompra"));
            }
            this.vehiculo.fromJSON(json.getJSONObject("vehiculo"));
            cliente.fromJSON(json.getJSONObject("cliente"));
            empleado.fromJSON(json.getJSONObject("empleado"));

        } catch (JSONException e) {
            logger.severe("Error al cargar Compra desde JSON: " + e.getMessage());
        }
    }
    public String getCodigoID() {
        return codigoID;
    }

    public void setCodigoID(String codigoID) {
        this.codigoID = codigoID;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public TipoDePago getTipoDePago() {
        return tipoDePago;
    }

    public void setTipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public PlanCompra getPlanCompra() {
        return planCompra;
    }

    public void setPlanCompra(PlanCompra planCompra) {
        this.planCompra = planCompra;
    }
    @Override
    public String toString() {
        return String.format(
                """
                +--------------------------------------------------------+
                |                  DETALLES DE LA COMPRA                 |
                +--------------------------------------------------------+
                |                        CLIENTE                         |
                +--------------------------------------------------------+
                %s
                +--------------------------------------------------------+
                |                        EMPLEADO                        |
                +--------------------------------------------------------+
                %s
                %s
                %s
                +--------------------------------------------------------+
                |                  INFORMACIÓN DE LA COMPRA              |
                +--------------------------------------------------------+
                | Código ID:               %-30s|
                | Estado:                  %-30s|
                | Tipo de Pago:            %-30s|
                %s
                +--------------------------------------------------------+
                """,
                cliente.toString(), // Llamada al toString() formateado de Cliente
                empleado.toString(), // Llamada al toString() formateado de Empleado
                vehiculo.toString(), // Llamada al toString() formateado de Vehículo
                factura.toString(),  // Llamada al toString() formateado de Factura
                codigoID,
                estado ? "Activo" : "Inactivo",
                tipoDePago,
                planCompra != null
                        ? String.format("| Plan de Compra:          %-30s|\n%s", "Sí", planCompra.toString())
                        : "| Plan de Compra:          %-30s|\n".formatted("No")
        );
    }
}
