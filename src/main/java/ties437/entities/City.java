package ties437.entities;

import org.json.simple.JSONObject;
import ties437.commons.OntologyConstants;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class City extends Entity{

    public static final String TYPE_URI = OntologyConstants.PREFIX_CB + "City";

    private String name;
    private double latitude;
    private double longitude;

    public City(String uri) {
        super(uri);
    }

    public City(String uri, String name, double latitude, double longitude) {
        super(uri);
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "uri=<" + uri + ">" +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put("uri", this.uri);
        obj.put("name", this.name);
        obj.put("latitude", this.latitude);
        obj.put("longitude", this.longitude);

        return obj;
    }
}
