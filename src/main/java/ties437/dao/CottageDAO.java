package ties437.dao;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import ties437.commons.OntologyConstants;
import ties437.commons.RdfUtils;
import ties437.entities.Bedroom;
import ties437.entities.Booking;
import ties437.entities.Cottage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class CottageDAO {
    public static Cottage getByURI(Model model, String uri) {
        Cottage cottage = null;

        String queryString = "SELECT *\n" +
                "WHERE {\n" +
                "   <" + uri + "> a <" + Cottage.TYPE_URI + "> .\n" +
                "   <" + uri + "> ?p ?o .\n" +
                "}\n";
        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();

            if (cottage ==  null) {
                cottage = new Cottage(uri);
            }
            addProperty(cottage, row, model);
        }

        return cottage;
    }

    public static List<Cottage> getAll(Model model) {
        List<Cottage> cottageList = new ArrayList<Cottage>();

        String queryString = "SELECT *\n" +
                "WHERE {\n" +
                "   ?s a <" + Cottage.TYPE_URI + "> .\n" +
                "   ?s ?p ?o .\n" +
                "}\n";
        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();

            String subjectURI = row.get("?s").asResource().getURI();
            Cottage cottage = new Cottage(subjectURI);

            if (cottageList.contains(cottage)) {
                cottage = cottageList.get(cottageList.indexOf(cottage));
            } else {
                cottageList.add(cottage);
            }
            addProperty(cottage, row, model);
        }

        return cottageList;
    }

    public static void main(String[] args) throws Exception {
        String dataSourcePath = "D:\\Future Data\\WISE\\Spring 2017\\TIES437 Everything to Everything Interfaces\\TIES437-01\\web\\resources\\rdf\\cottage-booking-data.rdf";
        Model model = RdfUtils.getModelFromRDF(dataSourcePath);

//        String uri = "http://localhost:8080/resources/rdf/cottage-booking-data.rdf#Susiraja_82_1";
//        Cottage cottage = CottageDAO.getByURI(dataSourcePath, uri);
//        System.out.println(cottage.toJSON().toJSONString().replace("\\/", "/"));

        List<Cottage> cottageList = CottageDAO.getAll(model);
        for (Cottage cottage : cottageList) {
            System.out.println(cottage.toJSON().toJSONString().replace("\\/", "/"));
        }
    }

    private static void addProperty(Cottage cottage, QuerySolution row, Model model) {
        String predicateURI = row.get("?p").asResource().getURI();
        if (predicateURI.equals(OntologyConstants.PROP_HAS_NAME)) {
            String name = row.get("?o").asLiteral().getString();
            cottage.setName(name);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_DISTANCE_TO_LAKE)) {
            double distanceToLake = row.get("?o").asLiteral().getDouble();
            cottage.setDistanceToLake(distanceToLake);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_LATITUDE)) {
            double latitude = row.get("?o").asLiteral().getDouble();
            cottage.setLatitude(latitude);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_LONGITUDE)) {
            double longitude = row.get("?o").asLiteral().getDouble();
            cottage.setLongitude(longitude);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_IMAGE_URL)) {
            String imageURL = row.get("?o").asLiteral().getString();
            cottage.setImageURL(imageURL);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_BED_ROOM)) {
            String bedroomURI = row.get("?o").asResource().getURI();
            Bedroom bedroom = BedroomDAO.getByURI(model, bedroomURI);
            cottage.getBedroomList().add(bedroom);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_BOOKING)) {
            String bookingURI = row.get("?o").asResource().getURI();
            Booking booking = BookingDAO.getByURI(model, bookingURI);
            cottage.getBookingList().add(booking);
        }
    }
}
