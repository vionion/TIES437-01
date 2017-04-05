package ties437.entities;

import org.json.simple.JSONObject;
import ties437.commons.OntologyConstants;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class Bedroom extends Entity {

    public static final String TYPE_URI = OntologyConstants.PREFIX_CB + "Bedroom";

    private int placeCount;

    public Bedroom(String uri) {
        super(uri);
    }

    public Bedroom(String uri, int placeCount) {
        super(uri);
        this.placeCount = placeCount;
    }

    public int getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    @Override
    public String toString() {
        return "Bedroom{" +
                "uri=" + uri +
                ", placeCount=" + placeCount +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put("uri", this.uri);
        obj.put("placeCount", this.placeCount);

        return obj;
    }
}
