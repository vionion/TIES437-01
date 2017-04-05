package ties437.webrunner;

/**
 * Created by chinhnk on 9/25/2016.
 */

import ties437.servlets.BookingServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

public class JettyRunner {
    public static final int PORT = 1309;
    public static final String WEB_RESOURCE_BASE = "./web";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase(WEB_RESOURCE_BASE);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(BookingServlet.class, "/BookingServlet");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, servletHandler, new DefaultHandler()});

        Server server = new Server(PORT);
        server.setHandler(handlers);
        server.start();

        System.out.println("Server Started.");
    }
}