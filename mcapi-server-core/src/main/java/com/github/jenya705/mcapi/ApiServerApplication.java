package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.database.ApiServerDatabase;
import com.github.jenya705.mcapi.database.ApiServerDatabaseImpl;
import com.github.jenya705.mcapi.jackson.ApiServerJacksonProvider;
import com.github.jenya705.mcapi.permission.ApiServerPermissionContainer;
import com.github.jenya705.mcapi.permission.ApiServerPermissionContainerImpl;
import com.github.jenya705.mcapi.permission.ApiServerPermissionRepository;
import com.github.jenya705.mcapi.permission.ApiServerPermissionRepositoryImpl;
import com.github.jenya705.mcapi.token.ApiServerTokenRepository;
import com.github.jenya705.mcapi.token.ApiServerTokenRepositoryImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

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
@Setter(AccessLevel.PROTECTED)
public class ApiServerApplication {

    @Getter
    private static ApiServerApplication application;

    private Collection<Class<?>> classes;

    // Core
    private ApiServerCore core;

    // Database
    private ApiServerDatabase database;

    // Permission
    private ApiServerPermissionContainer permissionContainer;
    private ApiServerPermissionRepository permissionRepository;

    // Token
    private ApiServerTokenRepository tokenRepository;

    private Server jettyServer;

    public ApiServerApplication(ApiServerCore core) {
        if (application != null) throw new IllegalStateException("Can not instantiate object twice");
        application = this;
        this.core = core;
        database = new ApiServerDatabaseImpl();
        permissionRepository = new ApiServerPermissionRepositoryImpl();
        permissionContainer = new ApiServerPermissionContainerImpl();
        tokenRepository = new ApiServerTokenRepositoryImpl();
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
