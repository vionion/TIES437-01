package ties437.servlets;

import org.apache.jena.rdf.model.Model;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ties437.commons.Constants;
import ties437.commons.OntologyConstants;
import ties437.commons.RdfUtils;
import ties437.commons.Utils;
import ties437.dao.CityDAO;
import ties437.dao.CottageDAO;
import ties437.entities.Bedroom;
import ties437.entities.Booking;
import ties437.entities.City;
import ties437.entities.Cottage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chinhnk on 1/4/2016.
 */
public class BookingServlet extends HttpServlet {

    public static final String P_BOOKER_NAME = "bookerName";
    public static final String P_MIN_BEDROOM_COUNT = "minBedroomCount";
    public static final String P_MIN_TOTAL_PLACE_COUNT = "minTotalPlaceCount";
    public static final String P_MAX_DISTANCE_TO_LAKE = "maxDistanceToLake";
    public static final String P_CITY = "city";
    public static final String P_MAX_DISTANCE = "maxDistance";
    public static final String P_START_DATE_STRING = "startDateString";
    public static final String P_DURATION_DAY = "durationDay";
    public static final String P_FLEX_DAY = "flexDay";

    public static final String RDF_FILE_RELATIVE_URL = "/resources/rdf/cottage-booking-data.rdf";

    private Model model;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext context = config.getServletContext();
        String fullPath = context.getRealPath(RDF_FILE_RELATIVE_URL);
        // load data from rdf file
        this.model = RdfUtils.getModelFromRDF(fullPath);
    }

    /**
     * required amount of places (people);
     * required amount of bedrooms;
     * max distance of a cottage from a lake (meters);
     * city (next to which cottage is located) and a max distance to that city from a cottage;
     * required amount of days;
     * starting day of a booking (dd.mm.yyyy) and max possible shift of it (+/- n days).
     * <p>
     * name of the booker;
     * booking number;
     * address of the cottage;
     * image of the cottage (URL of the image in the web);
     * actual amount of places in the cottage;
     * actual amount of bedrooms in the cottage;
     * actual distance to the lake (meters);
     * nearest city and a distance to it from the cottage;
     * booking period.
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String bookerName = request.getParameter(P_BOOKER_NAME);

        int minBedroomCount = Integer.parseInt(request.getParameter(P_MIN_BEDROOM_COUNT));
        int minTotalPlaceCount = Integer.parseInt(request.getParameter(P_MIN_TOTAL_PLACE_COUNT));
        double maxDistanceToLake = Double.parseDouble(request.getParameter(P_MAX_DISTANCE_TO_LAKE));

        City city = CityDAO.getByURI(model, OntologyConstants.PREFIX_CBD + request.getParameter(P_CITY));
        double maxDistance = Double.parseDouble(request.getParameter(P_MAX_DISTANCE));

        String startDateString = request.getParameter(P_START_DATE_STRING);
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_JS);

        Date startDate = null;
        try {
            startDate = formatter.parse(startDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int durationDay = Integer.parseInt(request.getParameter(P_DURATION_DAY));
        int flexDay = Integer.parseInt(request.getParameter(P_FLEX_DAY));

        JSONArray eligibleResponseArray = new JSONArray();
        List<Cottage> allCottageList = CottageDAO.getAll(this.model);
        for (Cottage cottage : allCottageList) {
            JSONObject eligibleResponse = getEligible(cottage, minBedroomCount, minTotalPlaceCount,
                    maxDistanceToLake, city, maxDistance, startDate, durationDay, flexDay);
            if (eligibleResponse != null) {
                eligibleResponse.put("bookerName", bookerName);
                System.out.println(eligibleResponse.toJSONString().replace("\\/", "/"));
                eligibleResponseArray.add(eligibleResponse);
            }
        }

        PrintWriter writer = response.getWriter();
        writer.print(eligibleResponseArray.toJSONString().replace("\\/", "/"));

//        Model model = BookingServlet.getModelFromRDFa(fileURL);
//
//        String ruleSrc = request.getParameter(P_RULES);
//
//        InfModel infModel = BookingServlet.applyRules(model, ruleSrc);
//
//        String queryString = request.getParameter(P_QUERY);
//
//        String responseString = BookingServlet.runQuery(infModel, queryString);
//
//        PrintWriter writer = response.getWriter();
//        writer.print(responseString);
//
//        System.out.println(responseString);


    }

    public static void main(String[] args) throws Exception {

        String dataSourcePath = "D:\\Future Data\\WISE\\Spring 2017\\TIES437 Everything to Everything Interfaces\\TIES437-01\\web\\resources\\rdf\\cottage-booking-data.rdf";
        Model model = RdfUtils.getModelFromRDF(dataSourcePath);

        String bookerName = "Pekka";

        int minBedroomCount = 2;
        int minTotalPlaceCount = 4;
        double maxDistanceToLake = 200.0;

        City city = CityDAO.getByURI(model, OntologyConstants.PREFIX_CBD + "Helsinki");
        double maxDistance = 7.0;

        String startDateString = "2017-08-07T12:00:00+02:00";
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_ONTO);

        Date startDate = formatter.parse(startDateString);
        int durationDay = 5;
        int flexDay = 2;

        List<Cottage> allCottageList = CottageDAO.getAll(model);
        for (Cottage cottage : allCottageList) {
            JSONObject eligibleResponse = getEligible(cottage, minBedroomCount, minTotalPlaceCount,
                    maxDistanceToLake, city, maxDistance, startDate, durationDay, flexDay);
            if (eligibleResponse != null) {
                eligibleResponse.put("bookerName", bookerName);
                System.out.println(eligibleResponse.toJSONString().replace("\\/", "/"));
            }
        }
//
//        // Shit has
//        String queryString1 = "PREFIX cb: <http://users.jyu.fi/~chnguyen/TIES437/cottage-booking.owl#>\n" +
//                "PREFIX cbd: <http://localhost:8080/resources/rdf/cottage-booking-data.rdf#>\n" +
//                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
//                "\n" +
//                "SELECT ?cottage\n" +
//                "   (COUNT(DISTINCT ?bedroom) AS ?bedroomCount)\n" +
//                "   (SUM(?placeCount) AS ?totalPlaceCount)\n" +
//                "   ?distanceToLake\n" +
//                "WHERE {\n" +
//                "   ?cottage a cb:Cottage .\n" +
//                "   ?cottage cb:hasBedroom ?bedroom .\n" +
//                "   ?bedroom cb:hasPlaceCount ?placeCount .\n" +
//                "   ?cottage cb:hasDistanceToLake ?distanceToLake .\n" +
//                "   ?booking a cb:Booking .\n" +
//                "   FILTER NOT EXISTS {?booking cb:hasCottage ?cottage} .\n" +
//                "}\n" +
//                "GROUP BY ?cottage ?distanceToLake\n" +
//                "HAVING (\n" +
//                "   ?bedroomCount >= " + minBedroomCount + "\n" +
//                "   &&\n" +
//                "   ?totalPlaceCount >= " + minTotalPlaceCount + "\n" +
//                "   &&\n" +
//                "   ?distanceToLake <= " + maxDistanceToLake + "\n" +
//                ")";
//
//        String queryString =
//                "SELECT *\n" +
//                        "WHERE {\n" +
//                        "   ?s a <" + Bedroom.TYPE_URI + "> .\n" +
//                        "   ?s ?p ?o .\n" +
//                        "}\n";
//
//        String queryString2 = "PREFIX cb: <http://users.jyu.fi/~chnguyen/TIES437/cottage-booking.owl#>\n" +
//                "PREFIX cbd: <http://localhost:8080/resources/rdf/cottage-booking-data.rdf#>\n" +
//                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
//                "\n" +
//                "SELECT ?cottage\n" +
//                "   (COUNT(DISTINCT ?bedroom) AS ?bedroomCount)\n" +
//                "   (SUM(?placeCount) AS ?totalPlaceCount)\n" +
//                "   ?distanceToLake\n" +
//                "   ?booking\n" +
//                "   ?bookedStartDate\n" +
//                "WHERE {\n" +
//                "   ?cottage a cb:Cottage .\n" +
//
//                "   ?cottage cb:hasBedroom ?bedroom .\n" +
//                "   ?bedroom cb:hasPlaceCount ?placeCount .\n" +
//
//                "   ?cottage cb:hasDistanceToLake ?distanceToLake .\n" +
//
//                "   ?booking a cb:Booking .\n" +
//                "   ?booking cb:hasCottage ?cottage .\n" +
//                "   ?booking cb:hasStartDate ?bookedStartDate .\n" +
//                "   ?booking cb:hasEndDate ?bookedEndDate .\n" +
//                "   FILTER (\n" +
//                "       ?bookedStartDate < \"2017-07-08T12:00:00+02:00\"^^xsd:dateTime\n" +
//                "       ||\n" +
//                "       ?bookedEndDate > \"2017-07-02T12:00:00+02:00\"^^xsd:dateTime\n" +
//                "   ) .\n" +
//                "}\n" +
//                "GROUP BY ?cottage ?distanceToLake ?booking ?bookedStartDate";

        // cb:hasEndDate "2017-07-07T12:00:00+02:00"^^xsd:dateTime ;
        // cb:hasStartDate "2017-07-01T12:00:00+02:00"^^xsd:dateTime .

//        BookingServlet.runQuery(model, queryString);
//        BookingServlet.runQuery(model, queryString1);
//        BookingServlet.runQuery(model, queryString2);
    }

    private static JSONObject getEligible(Cottage cottage,
                                          int minBedroomCount,
                                          int minTotalPlaceCount,
                                          double maxDistanceToLake,
                                          City city, double maxDistance,
                                          final Date startDate, int durationDay, int flexDay) {
        // check bedroomCount
        if (cottage.getBedroomList().size() < minBedroomCount) {
            return null;
        }

        // check totalPlaceCount
        int totalPlaceCount = 0;
        for (Bedroom bedroom : cottage.getBedroomList()) {
            totalPlaceCount += bedroom.getPlaceCount();
        }
        if (totalPlaceCount < minTotalPlaceCount) {
            return null;
        }

        // check distanceToLake
        if (cottage.getDistanceToLake() > maxDistanceToLake) {
            return null;
        }

        // check distance
        double distance = Utils.distanceFromCoords(
                city.getLatitude(), city.getLongitude(),
                cottage.getLatitude(), cottage.getLongitude());
        if (distance > maxDistance) {
            return null;
        }

        // check date
        List<Date> possibleStartDateList = new ArrayList<Date>();
        if (!cottage.getBookingList().isEmpty()) {
            for (Booking booking : cottage.getBookingList()) {
                Date bookedStartDate = booking.getStartDate();
                Date bookedEndDate = booking.getEndDate();

                for (int i = -flexDay; i <= flexDay; i++) {
                    Date possibleStartDate = Utils.addDays(startDate, i);
                    Date possibleEndDate = Utils.addDays(possibleStartDate, durationDay);
                    if (!(((possibleStartDate.compareTo(bookedStartDate) >= 0) && (possibleStartDate.compareTo(bookedEndDate) <= 0))
                            || ((possibleEndDate.compareTo(bookedStartDate) >= 0) && (possibleEndDate.compareTo(bookedEndDate) <= 0)))) {
                        if (!possibleStartDateList.contains(possibleStartDate)) {
                            possibleStartDateList.add(possibleStartDate);
                        }
                    }
                }

                if (possibleStartDateList.isEmpty()) {
                    return null;
                }
            }
        }

//     name of the booker;
//     booking number;
//     address of the cottage;
//     image of the cottage (URL of the image in the web);
//     actual amount of places in the cottage;
//     actual amount of bedrooms in the cottage;
//     actual distance to the lake (meters);
//     nearest city and a distance to it from the cottage;
//     booking period.
        Collections.sort(possibleStartDateList, new Comparator<Date>() {
            public int compare(Date o1, Date o2) {
                return Long.compare(Utils.getDateDiff(startDate, o1), Utils.getDateDiff(startDate, o2));
            }
        });

        JSONObject obj = new JSONObject();

        obj.put("address", cottage.getName());
        obj.put("imageURL", cottage.getImageURL());
        obj.put("bedroomCount", cottage.getBedroomList().size());
        obj.put("totalPlaceCount", totalPlaceCount);
        obj.put("distanceToLake", cottage.getDistanceToLake());
        obj.put("city", city.getName());
        obj.put("distance", distance);

        JSONArray possibleStartDateJsonArray = new JSONArray();
        for (Date date : possibleStartDateList) {
            possibleStartDateJsonArray.add(date.toString());
        }
        obj.put("possibleStartDateList", possibleStartDateJsonArray);

        return obj;
    }

}
