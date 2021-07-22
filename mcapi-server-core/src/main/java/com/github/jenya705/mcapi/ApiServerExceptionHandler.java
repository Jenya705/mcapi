package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.exception.ApiException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
public class ApiServerExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiException.map(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
