package ties437.service.servlets;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
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

}