package com.github.jenya705.mcapi.server.module.web.reactor;

import com.github.jenya705.mcapi.HttpMethod;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class ReactorNettyUtils {

    public HttpMethod wrap(io.netty.handler.codec.http.HttpMethod httpMethod) {
        return HttpMethod.valueOf(httpMethod.name().toUpperCase(Locale.ROOT));
    }

}
