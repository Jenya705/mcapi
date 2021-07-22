package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.jackson.ApiServerJacksonProvider;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.servlet.ServletContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 * A server application
 *
 * @since 1.0
 * @author Jenya705
 */
@Getter
public class ApiServerApplication {

    @Getter
    private static ApiServerApplication application;

    private final Collection<Class<?>> classes;
    private final ApiServerCore core;

    private Server jettyServer;

    public ApiServerApplication(ApiServerCore core) {
        if (application != null) throw new IllegalStateException("Can not instantiate object twice");
        application = this;
        this.core = core;
        classes = new ArrayList<>();
        classes.add(ApiServerPlayerRestController.class);
        classes.add(ApiServerExceptionHandler.class);
        classes.add(ApiServerJacksonProvider.class);
    }

    public void start() throws Exception {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");

        jettyServer = new Server(8080);
        jettyServer.setHandler(contextHandler);

        ServletHolder jerseyServlet = contextHandler.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                classes
                        .stream()
                        .map(Class::getCanonicalName)
                        .collect(Collectors.joining(","))
        );

        jettyServer.start();

    }

    public void stop() throws Exception {
        jettyServer.stop();
    }

    public <T> void change(Class<T> from, Class<? extends T> to) {
        getClasses().remove(from);
        getClasses().add(to);
    }

}
