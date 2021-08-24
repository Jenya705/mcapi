package com.github.jenya705.mcapi;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * @author Jenya705
 */
@Provider
@JerseyClass
public class ServerExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return Response
                .status(418)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ApiError() {
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
                })
                .build();
    }
}
