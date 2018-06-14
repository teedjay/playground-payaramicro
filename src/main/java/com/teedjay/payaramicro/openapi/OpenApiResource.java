package com.teedjay.payaramicro.openapi;

import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/openapidemo")
public class OpenApiResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Demo av OpenAPI", description = "Demo av hvordan man selv kan kommentere OpenAPI 3.0 output")
    public DemoResponse demo(@QueryParam("id") String id) {
        return DemoResponse.createDemoResponse(id);
    }

}
