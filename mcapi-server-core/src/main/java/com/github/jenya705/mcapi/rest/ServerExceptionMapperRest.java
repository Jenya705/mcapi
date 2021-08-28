package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.JerseyClass;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * @author Jenya705
 */
@Provider
@JerseyClass
public class ServerExceptionMapperRest implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return Response
                .status(418)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(ApiError.raw(exception))
                .build();
    }
}
