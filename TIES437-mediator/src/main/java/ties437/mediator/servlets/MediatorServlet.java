package ties437.mediator.servlets;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Created by vtsybulko on 17/04/17.
 */

@RequestMapping("/mediator")
@RestController()
public class MediatorServlet {

    final static Logger logger = LoggerFactory.getLogger(MediatorServlet.class);

    @RequestMapping(value = "/{url:.+}", method = RequestMethod.GET)
    public String getRDG(@PathVariable("url") String url) throws IOException, URISyntaxException {
        logger.debug("URL received: {}", url);
        HttpClient client = new DefaultHttpClient();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost(url);
        URI uri = builder.build();

        HttpGet request = new HttpGet(uri);
        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        logger.debug("Sending 'GET' request to URL : " + url);
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
        return result.toString();
    }

}