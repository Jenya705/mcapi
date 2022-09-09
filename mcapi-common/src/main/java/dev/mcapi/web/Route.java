package dev.mcapi.web;

import lombok.Data;

@Data(staticConstructor = "of")
public final class Route {

    public enum Type {
        GET,
        PUT,
        POST,
        DELETE,
        PATCH
    }

    public static Route get(String path) {
        return of(Type.GET, path);
    }

    public static Route post(String path) {
        return of(Type.POST, path);
    }

    public static Route put(String path) {
        return of(Type.PUT, path);
    }

    public static Route delete(String path) {
        return of(Type.DELETE, path);
    }

    public static Route patch(String path) {
        return of(Type.PATCH, path);
    }

    private final Type type;
    private final String path;

}
