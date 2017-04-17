package ties437.ui.servlets;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Created by vtsybulko on 17/04/17.
 */

@RequestMapping("/client")
@RestController()
public class ClientServlet {

    final static Logger logger = LoggerFactory.getLogger(ClientServlet.class);
    public static final String MEDIATOR_URL = "localhost:8082/mediator/";

    @RequestMapping(value = "/getRDG", method = RequestMethod.POST)
    public void getRDG(@RequestParam("serviceUrl") String serviceUrl) throws IOException, URISyntaxException {
        logger.debug("URL received: {}", serviceUrl);
        HttpClient client = new DefaultHttpClient();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(MEDIATOR_URL).setPath(serviceUrl);
        URI uri = builder.build();

        HttpGet request = new HttpGet(uri);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        logger.debug("Sending 'GET' request to URL : " + serviceUrl);
        logger.debug("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
    }

}