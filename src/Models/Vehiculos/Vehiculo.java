package Models.Vehiculos;

import Enums.TipoDeObject;
import Enums.Vehiculos.TipoDeCombustible;
import Interfaz.JsonInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Year;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class Vehiculo implements JsonInterface{
    private static final Logger logger = Logger.getLogger(Vehiculo.class.getName()); //Mejor control de Excepciones

    private static Integer id_vehiculo = 0;
    private Boolean estado;
    private String marca;
    private String modelo;
    private String matricula;
    private Double precio;
    private Year anio;
    private Integer kilometraje;
    private TipoDeCombustible combustible;
    private Integer capacidadCombustible;
    private TipoDeObject tipo;
    public Vehiculo() {
        id_vehiculo++;
    }
    public Vehiculo(Boolean estado, String marca, String modelo, String matricula,
                    Double precio, Year anio, Integer kilometraje,
                    TipoDeCombustible combustible, Integer capacidadCombustible, TipoDeObject tipo) {
        id_vehiculo++;
        this.estado = estado;
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.precio = precio;
        this.anio = anio;
        this.kilometraje = kilometraje;
        this.combustible = combustible;
        this.capacidadCombustible = capacidadCombustible;
        this.tipo = tipo;
    }
    public Integer getContador() {
        return id_vehiculo;
    }
    public TipoDeObject getTipo() {
        return tipo;
    }
    public void setTipo(TipoDeObject tipo) {
        this.tipo = tipo;
    }
    public Year getAnio() {
        return anio;
    }
    public void setAnio(Year anio) {
        this.anio = anio;
    }
    public Integer getCapacidadCombustible() {
        return capacidadCombustible;
    }
    public void setCapacidadCombustible(Integer capacidadCombustible) {
        this.capacidadCombustible = capacidadCombustible;
    }
    public TipoDeCombustible getCombustible() {
        return combustible;
    }
    public void setCombustible(TipoDeCombustible combustible) {
        this.combustible = combustible;
    }
    public Boolean getEstado() {
        return estado;
    }
    public static Integer getId_vehiculo() {
        return id_vehiculo;
    }
    public Integer getKilometraje() {
        return kilometraje;
    }
    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getMatricula() {
        return matricula;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    @Override
    public void fromJSON(JSONObject json) {
        try {
            this.estado = json.getBoolean("estado");
            this.marca = json.getString("marca");
            this.modelo = json.getString("modelo");
            this.matricula = json.getString("matricula");
            this.precio = json.getDouble("precio");
            this.anio = Year.of(json.getInt("anio"));
            this.kilometraje = json.getInt("kilometraje");
            this.combustible = TipoDeCombustible.valueOf(json.getString("combustible"));
            this.capacidadCombustible = json.getInt("capacidadCombustible");
            this.tipo = TipoDeObject.valueOf(json.getString("tipo"));
        } catch (JSONException e) {
            logger.severe("Error al cargar datos desde JSON: " + e.getMessage());
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("estado", this.estado);
            json.put("marca", this.marca);
            json.put("modelo", this.modelo);
            json.put("matricula", this.matricula);
            json.put("precio", this.precio);
            json.put("anio", this.anio.getValue());
            json.put("kilometraje", this.kilometraje);
            json.put("combustible", this.combustible.name());
            json.put("capacidadCombustible", this.capacidadCombustible);
            json.put("tipo", this.tipo.name());
            return json;
        } catch (JSONException e) {
            logger.severe("Error al convertir a JSON: " + e.getMessage());
        }
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehiculo vehiculo)) return false;
        return Objects.equals(getMatricula(), vehiculo.getMatricula());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMatricula());
    }

    @Override
    public String toString() {
        return String.format(
                """
                +--------------------------------------------------------+
                |                  DETALLES DEL VEHÍCULO                 |
                +--------------------------------------------------------+
                | Tipo:                   %-30s |
                | Marca:                  %-30s |
                | Modelo:                 %-30s |
                | Año:                    %-30d |
                | Estado:                 %-30s |
                | Matrícula:              %-30s |
                | Precio:                 $%,29.2f |
                | Kilometraje:            %,27d km |
                | Combustible:            %-30s |
                | Capacidad Combustible:  %-28d L |
                +--------------------------------------------------------+
                """,
                tipo,
                marca,
                modelo,
                anio.getValue(),
                estado ? "Activo" : "Inactivo",
                matricula,
                precio,
                kilometraje,
                combustible,
                capacidadCombustible
        );
    }
}
