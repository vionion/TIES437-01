package ties437.service.entities;

import org.json.simple.JSONObject;
import ties437.service.commons.OntologyConstants;

import java.util.Date;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class Booking extends Entity {

    public static final String TYPE_URI = OntologyConstants.PREFIX_CB + "Booking";

    private String bookerName;
    private Date startDate;
    private Date endDate;

    public Booking(String uri) {
        super(uri);
    }

    public Booking(String uri, String bookerName, Date startDate, Date endDate) {
        super(uri);
        this.bookerName = bookerName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "uri=" + uri +
                ", bookerName='" + bookerName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put("uri", this.uri);
        obj.put("bookerName", this.bookerName);
        obj.put("startDate", this.startDate.toString());
        obj.put("endDate", this.endDate.toString());

        return obj;
    }
}
