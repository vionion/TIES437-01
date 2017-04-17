package ties437.service.dao;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import ties437.service.commons.OntologyConstants;
import ties437.service.commons.RdfUtils;
import ties437.service.entities.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class CityDAO {
    public static City getByURI(Model model, String uri) {
        City city = null;

        String queryString = "SELECT *\n" +
                "WHERE {\n" +
                "   <" + uri + "> a <" + City.TYPE_URI + "> .\n" +
                "   <" + uri + "> ?p ?o .\n" +
                "}\n";
        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();

            if (city ==  null) {
                city = new City(uri);
            }
            addProperty(city, row, model);
        }

        return city;
    }

    public static List<City> getAll(Model model) {
        List<City> cityList = new ArrayList<City>();

        String queryString = "SELECT *\n" +
                "WHERE {\n" +
                "   ?s a <" + City.TYPE_URI + "> .\n" +
                "   ?s ?p ?o .\n" +
                "}\n";
        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();

            String subjectURI = row.get("?s").asResource().getURI();
            City city = new City(subjectURI);

            if (cityList.contains(city)) {
                city = cityList.get(cityList.indexOf(city));
            } else {
                cityList.add(city);
            }
            addProperty(city, row, model);
        }

        return cityList;
    }

    public static void main(String[] args) throws Exception {
        String dataSourcePath = "D:\\Future Data\\WISE\\Spring 2017\\TIES437 Everything to Everything Interfaces\\TIES437-01\\web\\resources\\rdf\\cottage-booking-data.rdf";
        Model model = RdfUtils.getModelFromRDF(dataSourcePath);

//        String uri = "http://localhost:8080/resources/rdf/city-booking-data.rdf#Susiraja_82_1";
//        City city = CityDAO.getByURI(dataSourcePath, uri);
//        System.out.println(city.toJSON().toJSONString().replace("\\/", "/"));

        List<City> cityList = CityDAO.getAll(model);
        for (City city : cityList) {
            System.out.println(city.toJSON().toJSONString().replace("\\/", "/"));
        }
    }

    private static void addProperty(City city, QuerySolution row, Model model) {
        String predicateURI = row.get("?p").asResource().getURI();
        if (predicateURI.equals(OntologyConstants.PROP_HAS_NAME)) {
            String name = row.get("?o").asLiteral().getString();
            city.setName(name);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_LATITUDE)) {
            double latitude = row.get("?o").asLiteral().getDouble();
            city.setLatitude(latitude);
        } else if (predicateURI.equals(OntologyConstants.PROP_HAS_LONGITUDE)) {
            double longitude = row.get("?o").asLiteral().getDouble();
            city.setLongitude(longitude);
        }
    }
}
