package Interfaz;

import org.json.JSONObject;

public interface JsonInterface {
    public JSONObject toJSON();
    public void fromJSON (JSONObject json);
}
