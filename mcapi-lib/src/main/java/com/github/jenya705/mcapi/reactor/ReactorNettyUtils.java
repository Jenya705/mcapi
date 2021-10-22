package com.github.jenya705.mcapi.reactor;

import io.netty.handler.codec.http.HttpMethod;
import lombok.experimental.UtilityClass;

import java.util.Locale;

/**
 * @author Jenya705
 */
@UtilityClass
public class ReactorNettyUtils {

    public HttpMethod wrap(com.github.jenya705.mcapi.HttpMethod method) {
        return HttpMethod.valueOf(method.getName().toUpperCase(Locale.ROOT));
    }

    public String formatUri(String uri, Object... args) {
        return String.format(
                uri.replaceAll(
                        "\\{[^/]*}",
                        "%s"
                ),
                args
        );
    }
}
