package ties437.service.servlets;

import org.apache.jena.rdf.model.Model;
import org.json.simple.JSONArray;
import ties437.service.commons.RdfUtils;
import ties437.service.dao.CityDAO;
import ties437.service.entities.City;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by chinhnk on 1/4/2016.
 */
@WebServlet("/city")
@MultipartConfig
public class CityServlet extends HttpServlet {

    public static final String PATH = "/city";

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONArray cityArray = new JSONArray();
        List<City> cityList = CityDAO.getAll(this.model);
        
        for (City city : cityList) {
            cityArray.add(city.toJSON());
        }

        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(cityArray);
    }
}
