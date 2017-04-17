package ties437.service.commons;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by chinhnk on 4/3/2017.
 */
public class RdfUtils {

    public static final String KEYWORD_SELECT = "SELECT";
    public static final String KEYWORD_CONSTRUCT = "CONSTRUCT";
    public static final String KEYWORD_DESCRIBE = "DESCRIBE";
    public static final String KEYWORD_ASK = "ASK";

    public static final String RDFA_API_LINK = "http://rdf-translator.appspot.com/convert/rdfa/xml/";

    public static void main(String[] args) throws Exception {
//        String fileURL = "http%3A%2F%2Fusers.jyu.fi%2F~chnguyen%2FTIES452%2Ffinal%2Ffinal.xhtml";

        Model model = RdfUtils.getModelFromRDF("D:\\Future Data\\WISE\\Spring 2017\\TIES437 Everything to Everything Interfaces\\TIES437-01\\web\\resources\\rdf\\cottage-booking-data.rdf");

        // required amount of places (people);
        // required amount of bedrooms;
        // max distance of a cottage from a lake (meters);
        // city (next to which cottage is located) and a max distance to that city from a cottage;
        // required amount of days;
        // starting day of a booking (dd.mm.yyyy) and max possible shift of it (+/- n days).

        // name of the booker;
        // booking number;
        // address of the cottage;
        // image of the cottage (URL of the image in the web);
        // actual amount of places in the cottage;
        // actual amount of bedrooms in the cottage;
        // actual distance to the lake (meters);
        // nearest city and a distance to it from the cottage;
        // booking period.

        String queryString = "PREFIX cb: <http://users.jyu.fi/~chnguyen/TIES437/cottage-booking.owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "\n" +
                "SELECT *\n" +
                "WHERE {\n" +
                "   <http://localhost:8080/resources/rdf/cottage-booking-data.rdf#Susiraja_82_1> ?p ?o .\n" +
                "}\n";

        RdfUtils.runQuery(model, queryString);
    }

    public static Model getModelFromRDFa(String fileURL) throws IOException {
        URL url = new URL(RDFA_API_LINK + fileURL);

        System.out.println("GET RDFa URL:");
        System.out.println(RDFA_API_LINK + fileURL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String modelText = "";
        String line;
        while ((line = br.readLine()) != null) {
            modelText += line + "\n";
        }

        ByteArrayInputStream stream = new ByteArrayInputStream(modelText.getBytes());
        Model model = ModelFactory.createDefaultModel();
        model.read(stream, null);

        conn.disconnect();
        model.write(System.out, Constants.RDF_LANG);

        return model;
    }

    public static Model getModelFromRDF(String fileURL) {
        Model model = ModelFactory.createDefaultModel();
        try {
            model.read(new FileInputStream(fileURL), null, Constants.RDF_LANG);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return model;
    }

    public static InfModel applyRules(Model model, String ruleSrc) throws IOException {
        PrintWriter writer = new PrintWriter("temp.rules", "UTF-8");
        writer.println(ruleSrc);
        writer.close();

        List<Rule> rules = Rule.rulesFromURL("temp.rules");

        System.out.println(rules.size() + " rule(s).");

        Reasoner reasoner = new GenericRuleReasoner(rules);
        reasoner.setDerivationLogging(true);
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        PrintWriter out = new PrintWriter(System.out);
        for (StmtIterator i = infModel.listStatements(); i.hasNext(); ) {
            Statement s = i.nextStatement();

            for (Iterator id = infModel.getDerivation(s); id.hasNext(); ) {
                System.out.println("Statement is " + s);
                Derivation derivation = (Derivation) id.next();
                derivation.printTrace(out, true);
            }
        }
        out.flush();

        return infModel;
    }

    public static String runQuery(Model model, String queryString) {
        String result = "";

        Dataset dataset = DatasetFactory.create(model);
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, dataset);

        if (Pattern.compile(Pattern.quote(KEYWORD_SELECT), Pattern.CASE_INSENSITIVE).matcher(queryString).find()) {
            ResultSet resultSet = queryExecution.execSelect();
            String resultHead = "";
            String resultBody = "";
            boolean passFirstRow = false;
            while (resultSet.hasNext()) {
                QuerySolution row = resultSet.next();
                Iterator<String> varNamesIterator = row.varNames();
                while (varNamesIterator.hasNext()) {
                    String varName = varNamesIterator.next();
                    if (!passFirstRow) {
                        resultHead += varName + "\t";
                    }
                    resultBody += row.get(varName).toString() + "\t";
                }
                passFirstRow = true;
                resultBody += "\n";
            }
            result = resultHead + "\n" + resultBody;

        } else if (Pattern.compile(Pattern.quote(KEYWORD_ASK), Pattern.CASE_INSENSITIVE).matcher(queryString).find()) {
            boolean answer = queryExecution.execAsk();
            result = (answer) ? "Yes !!!" : "No...";

        } else if (Pattern.compile(Pattern.quote(KEYWORD_CONSTRUCT), Pattern.CASE_INSENSITIVE).matcher(queryString).find()) {
            Model resultModel = queryExecution.execConstruct();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resultModel.write(outputStream, Constants.RDF_LANG);
            result = outputStream.toString();

        } else if (Pattern.compile(Pattern.quote(KEYWORD_DESCRIBE), Pattern.CASE_INSENSITIVE).matcher(queryString).find()) {
            Model resultModel = queryExecution.execDescribe();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resultModel.write(outputStream, Constants.RDF_LANG);
            result = outputStream.toString();
        }

        System.out.println(result);

        return result;
    }
}
