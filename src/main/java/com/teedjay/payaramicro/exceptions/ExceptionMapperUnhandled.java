package com.teedjay.payaramicro.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapperUnhandled implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        String message = "We just got RuntimeException of type '" + exception.getClass().getSimpleName() + "'.\n";
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN_TYPE).entity(message).build();
    }

}

