package Models.Persona;

import Enums.Personas.Genero;
import Enums.TipoDeObject;
import com.sun.source.tree.TryTree;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.logging.Logger;

public class Cliente extends Personas {
    private static final Logger logger = Logger.getLogger(Cliente.class.getName());
    private String email;
    private String telefono;
    private Boolean licenciaConducir;

    public Cliente() {
        super();
    }

    public Cliente(TipoDeObject tipo, String apellido, String direccion, String dni, Boolean estado,
                   Genero genero, String nombre, String email, Boolean licenciaConducir,
                   String telefono) {
        super(apellido, direccion, dni, estado, genero, nombre, tipo);
        this.email = email;
        this.licenciaConducir = licenciaConducir;
        this.telefono = telefono;
    }

    public void pagarCuota() {
        System.out.println("Pagando Cuota...");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getLicenciaConducir() {
        return licenciaConducir;
    }

    public void setLicenciaConducir(Boolean licenciaConducir) {
        this.licenciaConducir = licenciaConducir;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = super.toJSON();
            jsonObject.put("email", this.email);
            jsonObject.put("telefono", this.telefono);
            jsonObject.put("licenciaConducir", this.licenciaConducir);
        } catch (JSONException e) {
            logger.severe("Error al convertir Cliente a JSON: " + e.getMessage());
        }
        return jsonObject;
    }

    @Override
    public void fromJSON(JSONObject json) {
        super.fromJSON(json);
        this.email = json.getString("email");
        this.telefono = json.getString("telefono");
        this.licenciaConducir = json.getBoolean("licenciaConducir");
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                """
                        | Email:                  %-30s |
                        | Teléfono:               %-30s |
                        | Licencia de Conducir:   %-30s |
                        +--------------------------------------------------------+
                        """,
                email,
                telefono,
                licenciaConducir ? "Sí" : "No"
        );
    }
}
