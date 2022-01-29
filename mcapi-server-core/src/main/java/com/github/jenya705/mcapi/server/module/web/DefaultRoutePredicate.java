package com.github.jenya705.mcapi.server.module.web;

import com.github.jenya705.mcapi.HttpMethod;
import com.github.jenya705.mcapi.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  DefaultRoutePredicate implements RoutePredicate, RouteParameters {

    private final UriPathTemplate pathTemplate;
    private final HttpMethod method;

    public DefaultRoutePredicate(Route route) {
        this.pathTemplate = new UriPathTemplate(route.getUri());
        this.method = route.getMethod();
    }

    @Override
    public Map<String, String> getParameters(String uri) {
        return pathTemplate.match(uri);
    }

    @Override
    public boolean apply(HttpMethod method, String uri) {
        return method == this.method && pathTemplate.matches(uri);
    }

    static final class UriPathTemplate {

        private static final Pattern FULL_SPLAT_PATTERN =
                Pattern.compile("[\\*][\\*]");
        private static final String FULL_SPLAT_REPLACEMENT = ".*";

        private static final Pattern NAME_SPLAT_PATTERN =
                Pattern.compile("\\{([^/]+?)\\}[\\*][\\*]");

        private static final Pattern NAME_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

        private static final Pattern URL_PATTERN =
                Pattern.compile("(?:(\\w+)://)?((?:\\[.+?])|(?<!\\[)(?:[^/?]+?))(?::(\\d{2,5}))?([/?].*)?");

        private final List<String> pathVariables = new ArrayList<>();

        private final Pattern uriPattern;

        private static String getNameSplatReplacement(String name) {
            return "(?<" + name + ">.*)";
        }

        private static String getNameReplacement(String name) {
            return "(?<" + name + ">[^\\/]*)";
        }

        static String filterQueryParams(String uri) {
            int hasQuery = uri.lastIndexOf('?');
            if (hasQuery != -1) {
                return uri.substring(0, hasQuery);
            } else {
                return uri;
            }
        }

        static String filterHostAndPort(String uri) {
            if (uri.startsWith("/")) {
                return uri;
            } else {
                Matcher matcher = URL_PATTERN.matcher(uri);
                if (matcher.matches()) {
                    String path = matcher.group(4);
                    return path == null ? "/" : path;
                } else {
                    throw new IllegalArgumentException("Unable to parse url [" + uri + "]");
                }
            }
        }

        UriPathTemplate(String uriPattern) {
            String s = "^" + filterQueryParams(filterHostAndPort(uriPattern));

            Matcher m = NAME_SPLAT_PATTERN.matcher(s);
            while (m.find()) {
                for (int i = 1; i <= m.groupCount(); i++) {
                    String name = m.group(i);
                    pathVariables.add(name);
                    s = m.replaceFirst(getNameSplatReplacement(name));
                    m.reset(s);
                }
            }

            m = NAME_PATTERN.matcher(s);
            while (m.find()) {
                for (int i = 1; i <= m.groupCount(); i++) {
                    String name = m.group(i);
                    pathVariables.add(name);
                    s = m.replaceFirst(getNameReplacement(name));
                    m.reset(s);
                }
            }

            m = FULL_SPLAT_PATTERN.matcher(s);
            while (m.find()) {
                s = m.replaceAll(FULL_SPLAT_REPLACEMENT);
                m.reset(s);
            }

            this.uriPattern = Pattern.compile(s + "$");
        }

        public boolean matches(String uri) {
            return matcher(uri).matches();
        }

        public final Map<String, String> match(String uri) {
            Map<String, String> pathParameters = new HashMap<>(pathVariables.size());

            Matcher m = matcher(uri);
            if (m.matches()) {
                int i = 1;
                for (String name : pathVariables) {
                    String val = m.group(i++);
                    pathParameters.put(name, val);
                }
            }
            return pathParameters;
        }

        private Matcher matcher(String uri) {
            uri = filterQueryParams(filterHostAndPort(uri));
            return uriPattern.matcher(uri);
        }

    }

}
