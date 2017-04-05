package ties437.dao;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ties437.commons.RdfUtils;

/**
 * Created by chinhnk on 4/4/2017.
 */
public class ExperimentalDAO {
    public static JSONObject getByURI(String dataSourcePath, String uri) {
        JSONObject obj = new JSONObject();
        obj.put("uri", uri);

        Model model = RdfUtils.getModelFromRDF(dataSourcePath);

        String queryString = "PREFIX cb: <http://users.jyu.fi/~chnguyen/TIES437/cottage-booking.owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "\n" +
                "SELECT *\n" +
                "WHERE {\n" +
                "   <" + uri + "> ?p ?o .\n" +
                "}\n";

        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        ResultSet resultSet = queryExecution.execSelect();

        while (resultSet.hasNext()) {
            resultSet.next();   // skip first row
        }
        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();
            RDFNode predicateNode = row.get("?p");
            RDFNode objectNode = row.get("?o");

            if (row.get("?o").isLiteral()) {
                if (obj.get(predicateNode.toString()) instanceof JSONArray) {
                    ((JSONArray) obj.get(predicateNode.toString())).add(objectNode.asLiteral().getValue());
                } else if (obj.get(predicateNode.toString()) != null) {
                    JSONArray arr = new JSONArray();
                    arr.add(obj.get(predicateNode.toString()));
                    arr.add(objectNode.asLiteral().getValue());
                    obj.put(predicateNode.toString(), arr);
                } else {
                    obj.put(predicateNode.toString(), objectNode.asLiteral().getValue());
                }
            } else if (objectNode.isURIResource()) {
                if (obj.get(predicateNode.toString()) instanceof JSONArray) {
                    ((JSONArray) obj.get(predicateNode.toString())).add(ExperimentalDAO.getByURI(dataSourcePath, objectNode.asResource().getURI()));
                } else if (obj.get(predicateNode.toString()) != null) {
                    JSONArray arr = new JSONArray();
                    arr.add(obj.get(predicateNode.toString()));
                    arr.add(ExperimentalDAO.getByURI(dataSourcePath, objectNode.asResource().getURI()));
                    obj.put(predicateNode.toString(), arr);
                } else {
                    obj.put(predicateNode.toString(), ExperimentalDAO.getByURI(dataSourcePath, objectNode.asResource().getURI()));
                }
            }
        }

        return obj;
    }

//    public static List<Bedroom> getAll(String dataSourcePath, String uri) {
//
//    }

    public static void main(String[] args) throws Exception {
        String dataSourcePath = "D:\\Future Data\\WISE\\Spring 2017\\TIES437 Everything to Everything Interfaces\\TIES437-01\\web\\resources\\rdf\\cottage-booking-data.rdf";
        String uri = "http://localhost:8080/resources/rdf/cottage-booking-data.rdf#cottage1";

        JSONObject obj = ExperimentalDAO.getByURI(dataSourcePath, uri);

        System.out.println(obj.toJSONString().replace("\\/", "/"));
    }
}
