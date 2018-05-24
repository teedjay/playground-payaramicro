package com.teedjay.payaramicro.exceptions;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("exception")
@RequestScoped
public class ExceptionTriggerResource {

    @GET
    @Path("{divisor}")
    public Response getFixedDividendByDivisor(@PathParam("divisor") long divisor) {
        String result =  "The integer result of 10/" + divisor + " = " + (10L / divisor);
        return Response.ok(result).build();
    }

}
