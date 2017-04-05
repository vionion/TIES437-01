package ties437.entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ties437.commons.OntologyConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class Cottage extends Entity {

    public static final String TYPE_URI = OntologyConstants.PREFIX_CB + "Cottage";

    private String name;
    private double latitude;
    private double longitude;
    private double distanceToLake;
    private String imageURL;
    private List<Bedroom> bedroomList = new ArrayList<Bedroom>();
    private List<Booking> bookingList = new ArrayList<Booking>(); 

    public Cottage(String uri) {
        super(uri);
    }

    public Cottage(String uri, String name, double latitude, double longitude, double distanceToLake, String imageURL, List<Bedroom> bedroomList, List<Booking> bookingList) {
        super(uri);
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceToLake = distanceToLake;
        this.imageURL = imageURL;
        this.bedroomList = bedroomList;
        this.bookingList = bookingList;
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

    public List<Bedroom> getBedroomList() {
        return bedroomList;
    }

    public void setBedroomList(List<Bedroom> bedroomList) {
        this.bedroomList = bedroomList;
    }

    public double getDistanceToLake() {
        return distanceToLake;
    }

    public void setDistanceToLake(double distanceToLake) {
        this.distanceToLake = distanceToLake;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @Override
    public String toString() {
        return "Cottage{" +
                "uri=" + uri +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distanceToLake=" + distanceToLake +
                ", imageURL='" + imageURL + '\'' +
                ", bedroomList=" + bedroomList +
                ", bookingList=" + bookingList +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put("uri", this.uri);
        obj.put("name", this.name);
        obj.put("latitude", this.latitude);
        obj.put("longitude", this.longitude);
        obj.put("distanceToLake", this.distanceToLake);
        obj.put("imageURL", this.imageURL);

        JSONArray bedroomJsonArray = new JSONArray();
        for (Bedroom bedroom : this.bedroomList) {
            bedroomJsonArray.add(bedroom.toJSON());
        }
        obj.put("bedroomList", bedroomJsonArray);

        JSONArray bookingJsonArray = new JSONArray();
        for (Booking booking : this.bookingList) {
            bookingJsonArray.add(booking.toJSON());
        }
        obj.put("bookingList", bookingJsonArray);

        return obj;
    }
}
