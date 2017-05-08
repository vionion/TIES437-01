package ties437.service.servlets;


import info.sswap.api.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Created by vtsybulko on 17/04/17.
 */

@RequestMapping("/")
@RestController()
public class ServiceDescriptionServlet {

    @Value("${rdg.file.path}")
    private String rdgFilePath;

    final static Logger logger = LoggerFactory.getLogger(ServiceDescriptionServlet.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getRDG(){
        ClassLoader classLoader = getClass().getClassLoader();
        File rdg = new File(classLoader.getResource(rdgFilePath).getFile());
        StringBuilder result = new StringBuilder("");
        try (Scanner scanner = new Scanner(rdg)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args) throws URISyntaxException {
        RDG rdg = SSWAP.createRDG(
                new URI("http://foo.bar"),
                "CottageBookingService",
                "Service returns available cottage bookings based on request.",
                new URI("http://foo-foo.bar"));

        SSWAPResource resource = rdg.getResource();

        SSWAPGraph graph = rdg.createGraph();
        resource.setGraph(graph);
        System.out.println(resource.getGraph());

        SSWAPSubject subject = rdg.createSubject();
        graph.setSubject(subject);
        System.out.println(graph.getSubject());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        rdg.serialize(outputStream, RDFRepresentation.TURTLE, false);
//        System.out.println(outputStream.toString());
//        System.out.println("==============================");
//
//        outputStream = new ByteArrayOutputStream();
        rdg.getResource().serialize(outputStream, RDFRepresentation.TURTLE, false);
        System.out.println(outputStream.toString());
        System.out.println("==============================");

//        rdg.getResource().getGraph().serialize(outputStream, RDFRepresentation.TURTLE, false);
//        System.out.println(outputStream.toString());
//        System.out.println("==============================");

        System.out.println(rdg.getResource().getName());
        System.out.println(rdg.getResource().getOneLineDescription());
    }

}