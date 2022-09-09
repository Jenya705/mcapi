package dev.mcapi.web.reactor;

import dev.mcapi.web.WebHandler;
import dev.mcapi.web.WebRequestPreProcessor;
import lombok.Data;

@Data
public class ReactorResponder {

    private final WebRequestPreProcessor preProcessor;
    private final WebHandler handler;

}
