package com.github.jenya705.mcapi.module.web;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.module.BaseServerModule;
import com.github.jenya705.mcapi.module.web.rest.ServerPlayerRest;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Jenya705
 */
@Slf4j
@Getter
public class WebModule extends BaseServerModule {

    private final Collection<Class<?>> classes = new ArrayList<>();

    private HttpServer server;

    public WebModule(ServerApplication application) {
        super(application);
        classes.add(ServerPlayerRest.class);
    }

    @Override
    public void load() {
        /* NOTHING */
    }

    @Override
    public void start() {
        URI uri = UriBuilder.fromUri("http://localhost/").port(8080).build();
        ResourceConfig configuration = new ResourceConfig(new HashSet<>(classes));
        server = GrizzlyHttpServerFactory.createHttpServer(uri, configuration);
        try {
            server.start();
        } catch (Exception e) {
            log.error("Can not start jetty server", e);
        }
    }

    @Override
    public void stop() {
        try {
            server.shutdown().wait();
        } catch (Exception e) {
            log.error("Can not stop jetty server", e);
        }
    }
}
