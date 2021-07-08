package com.github.jenya705.mcapi;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.fu.jafu.ApplicationDsl;
import org.springframework.fu.jafu.Jafu;
import org.springframework.fu.jafu.JafuApplication;
import org.springframework.fu.jafu.webflux.WebFluxServerDsl;
import org.springframework.fu.jafu.webmvc.WebMvcServerDsl;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@SpringBootApplication
@Getter
@Setter(AccessLevel.PROTECTED)
public class JavaServerApplication {

    private List<Consumer<ApplicationDsl>> configurations = new ArrayList<>();

    private JafuApplication application;

    public JavaServerApplication(Class<? extends JavaServerCore> beanClazz) {
        configurations.add(applicationDsl -> applicationDsl
                .beans(beanDefinitionDsl -> beanDefinitionDsl
                        .bean(beanClazz)
                        .bean(JavaPlayerRestController.class)
                )
                .enable(
                        WebMvcServerDsl.webMvc(web -> web
                                .router(router -> { // Player rest
                                    JavaPlayerRestController playerRestController = applicationDsl.ref(JavaPlayerRestController.class);
                                    router
                                            .GET("/player/{id}", playerRestController::getPlayer)
                                            .PUT("/player/{id}/send", playerRestController::sendMessage)
                                            .onError((e) -> true, (exception, request) ->
                                                    ServerResponse.ok().body(new JavaApiError(exception.getMessage()))
                                            );
                                })
                                .port(applicationDsl.env() == null ?
                                        8080 :
                                        applicationDsl.env().getProperty("port", int.class, 8080)
                                )
                                .converters(c -> c.string().jackson())
                        )
                )
        );
    }

    public void run() {
        setApplication(Jafu.webApplication(
                app -> {
                    configurations.forEach(it -> it.accept(app));
                }
        ));
        getApplication().run();
    }

}
