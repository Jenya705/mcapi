package com.github.jenya705.mcapi;

/**
 * @author Jenya705
 */
public interface ApiError {

    static ApiError raw(Exception exception) {
        return new ApiError() {
            @Override
            public int getStatusCode() {
                return exception instanceof ApiError ?
                        ((ApiError) exception).getStatusCode() : 500;
            }

            @Override
            public int getCode() {
                return exception instanceof ApiError ?
                        ((ApiError) exception).getCode() : 0;
            }

            @Override
            public String getNamespace() {
                return exception instanceof ApiError ?
                        ((ApiError) exception).getNamespace() : null;
            }

            @Override
            public String getReason() {
                return exception instanceof ApiError ?
                        ((ApiError) exception).getReason() :
                        exception.getMessage();
            }
        };
    }

    String defaultNamespace = "mcapi";

    int getStatusCode();

    int getCode();

    String getNamespace();

    String getReason();
}
