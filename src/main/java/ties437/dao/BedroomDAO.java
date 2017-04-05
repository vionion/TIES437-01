package ties437.dao;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import ties437.commons.OntologyConstants;
import ties437.commons.RdfUtils;
import ties437.entities.Bedroom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class BedroomDAO {
    public static Bedroom getByURI(Model model, String uri) {
        Bedroom bedroom = null;

        String queryString = "SELECT *\n" +
                "WHERE {\n" +
                "   <" + uri + "> a <" + Bedroom.TYPE_URI + "> .\n" +
                "   <" + uri + "> ?p ?o .\n" +
                "}\n";
        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();

            if (bedroom ==  null) {
                bedroom = new Bedroom(uri);
            }
            addProperty(bedroom, row, model);
        }

        return bedroom;
    }

    public static List<Bedroom> getAll(Model model) {
        List<Bedroom> bedroomList = new ArrayList<Bedroom>();

        String queryString = "SELECT *\n" +
                "WHERE {\n" +
                "   ?s a <" + Bedroom.TYPE_URI + "> .\n" +
                "   ?s ?p ?o .\n" +
                "}\n";
        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();

            String subjectURI = row.get("?s").asResource().getURI();
            Bedroom bedroom = new Bedroom(subjectURI);

            if (bedroomList.contains(bedroom)) {
                bedroom = bedroomList.get(bedroomList.indexOf(bedroom));
            } else {
                bedroomList.add(bedroom);
            }
            addProperty(bedroom, row, model);

        }

        return bedroomList;
    }

    public static void main(String[] args) throws Exception {
        String dataSourcePath = "D:\\Future Data\\WISE\\Spring 2017\\TIES437 Everything to Everything Interfaces\\TIES437-01\\web\\resources\\rdf\\cottage-booking-data.rdf";
        Model model = RdfUtils.getModelFromRDF(dataSourcePath);

//        String uri = "http://localhost:8080/resources/rdf/cottage-booking-data.rdf#Susiraja_82_1";
//        Bedroom bedroom = BedroomDAO.getByURI(dataSourcePath, uri);
//        System.out.println(bedroom.toJSON().toJSONString().replace("\\/", "/"));

        List<Bedroom> bedroomList = BedroomDAO.getAll(model);
        for (Bedroom bedroom : bedroomList) {
            System.out.println(bedroom.toJSON().toJSONString().replace("\\/", "/"));
        }
    }

    private static void addProperty(Bedroom bedroom, QuerySolution row, Model model) {
        String predicateURI = row.get("?p").asResource().getURI();
        if (predicateURI.equals(OntologyConstants.PROP_HAS_PLACE_COUNT)) {
            int placeCount = row.get("?o").asLiteral().getInt();
            bedroom.setPlaceCount(placeCount);
        }
    }
}
