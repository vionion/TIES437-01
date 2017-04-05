package ties437.dao;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import ties437.commons.Constants;
import ties437.commons.OntologyConstants;
import ties437.commons.RdfUtils;
import ties437.entities.Booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class BookingDAO {
    public static Booking getByURI(Model model, String uri) {
        Booking booking = null;

        String queryString = "SELECT *\n" +
                "WHERE {\n" +
                "   <" + uri + "> a <" + Booking.TYPE_URI + "> .\n" +
                "   <" + uri + "> ?p ?o .\n" +
                "}\n";
        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();

            if (booking ==  null) {
                booking = new Booking(uri);
            }
            addProperty(booking, row, model);
        }

        return booking;
    }

    public static List<Booking> getAll(Model model) {
        List<Booking> bookingList = new ArrayList<Booking>();

        String queryString = "SELECT *\n" +
                "WHERE {\n" +
                "   ?s a <" + Booking.TYPE_URI + "> .\n" +
                "   ?s ?p ?o .\n" +
                "}\n";
        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();

            String subjectURI = row.get("?s").asResource().getURI();
            Booking booking = new Booking(subjectURI);

            if (bookingList.contains(booking)) {
                booking = bookingList.get(bookingList.indexOf(booking));
            } else {
                bookingList.add(booking);
            }
            addProperty(booking, row, model);
        }

        return bookingList;
    }

    public static void main(String[] args) throws Exception {
        String dataSourcePath = "D:\\Future Data\\WISE\\Spring 2017\\TIES437 Everything to Everything Interfaces\\TIES437-01\\web\\resources\\rdf\\cottage-booking-data.rdf";
        Model model = RdfUtils.getModelFromRDF(dataSourcePath);

//        String uri = "http://localhost:8080/resources/rdf/booking-booking-data.rdf#Susiraja_82_1";
//        Booking booking = BookingDAO.getByURI(dataSourcePath, uri);
//        System.out.println(booking.toJSON().toJSONString().replace("\\/", "/"));

        List<Booking> bookingList = BookingDAO.getAll(model);
        for (Booking booking : bookingList) {
            System.out.println(booking.toJSON().toJSONString().replace("\\/", "/"));
        }
    }

    private static void addProperty(Booking booking, QuerySolution row, Model model) {
        String predicateURI = row.get("?p").asResource().getURI();
        if (predicateURI.equals(OntologyConstants.PROP_HAS_BOOKER_NAME)) {
            String bookerName = row.get("?o").asLiteral().getString();
            booking.setBookerName(bookerName);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_START_DATE)) {
            try {
                String startDateString = row.get("?o").asLiteral().getString();
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_ONTO);

                Date startDate = formatter.parse(startDateString);
                booking.setStartDate(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_END_DATE)) {
            try {
                String endDateString = row.get("?o").asLiteral().getString();
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_ONTO);

                Date endDate = formatter.parse(endDateString);
                booking.setEndDate(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
