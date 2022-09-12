package dev.mcapi.web.reactor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.mcapi.config.ConfigStorage;
import dev.mcapi.mapper.Mapper;
import dev.mcapi.web.*;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class ReactorServer implements WebServer {

    private final HttpServer httpServer;
    private final DisposableServer disposableServer;
    private final Mapper mapper;
    private final List<ReactorResponder> responders = new CopyOnWriteArrayList<>();

    @Inject
    public ReactorServer(ConfigStorage configStorage, Mapper mapper) {
        this.mapper = mapper;
        httpServer = HttpServer.create()
                .handle(this::httpRequestHandler)
                .port(configStorage.getGlobalConfig().get("http.port"));
        disposableServer = httpServer.bindNow();
    }

    private Publisher<Void> httpRequestHandler(HttpServerRequest request, HttpServerResponse response) {
        ReactorWebRequest webRequest = new ReactorWebRequest(request, mapper);
        return response.sendString(
                responders.stream()
                        .filter(responder -> responder.getPreProcessor().isPassed(webRequest))
                        .findAny()
                        .map(responder -> {
                            responder.getPreProcessor()
                                    .fillParameters(webRequest, webRequest.getParameters());
                            return responder.getHandler();
                        })
                        .map(handler -> handler.respond(webRequest))
                        .orElseGet(() -> Mono.just(WebResponse.create().status(404)))
                        .doOnNext(webResponse -> response.status(webResponse.status()))
                        .doOnNext(webResponse -> webResponse.headers().forEach(response::header))
                        .map(webResponse -> webResponse.buildBody(mapper))
                        .onErrorResume(throwable -> Mono.just(
                                mapper.toJson(mapper.fromThrowable(throwable))
                        ))
        );
    }

    @Override
    public WebServer handler(Route route, WebHandler handler) {
        return handler(new RouteWebRequestPreProcessor(route), handler);
    }

    @Override
    public WebServer handler(WebRequestPreProcessor preProcessor, WebHandler handler) {
        responders.add(new ReactorResponder(preProcessor, handler));
        return this;
    }
}
