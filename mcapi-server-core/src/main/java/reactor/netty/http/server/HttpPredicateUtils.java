package reactor.netty.http.server;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.Route;
import lombok.experimental.UtilityClass;

import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@UtilityClass
public class HttpPredicateUtils {

    public Predicate<HttpServerRequest> predicate(HttpMethod method, String uri) {
        return new HttpPredicate(
                uri,
                null,
                io.netty.handler.codec.http.HttpMethod.valueOf(method.getName())
        );
    }

    public Predicate<HttpServerRequest> predicate(Route route) {
        return predicate(route.getMethod(), route.getUri());
    }
}
