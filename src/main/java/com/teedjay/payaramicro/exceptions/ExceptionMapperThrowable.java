package com.teedjay.payaramicro.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapperThrowable implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        String message = "We just got Throwable of type '" + exception.getClass().getSimpleName() + "'.\n";
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN_TYPE).entity(message).build();
    }

}

