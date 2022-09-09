package dev.mcapi.web;

import com.google.inject.ImplementedBy;
import dev.mcapi.web.reactor.ReactorServer;

@ImplementedBy(ReactorServer.class)
public interface WebServer {

    WebServer handler(Route route, WebHandler handler);

    WebServer handler(WebRequestPreProcessor preProcessor, WebHandler handler);

}
