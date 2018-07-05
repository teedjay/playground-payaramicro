package com.teedjay.payaramicro.openapi;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/openapidemo")
@Produces(MediaType.APPLICATION_JSON)
public class OpenApiResource {

    @GET
    @Operation(summary = "Simple POJO demo", description = "Vise hvordan POJO returnert direkte automatisk kommer med i OpenAPI output")
    public SimpleResponse simpleQueryParam(@QueryParam("id") String id) {
        return SimpleResponse.createDemoResponse(id);
    }

    @GET
    @Path("/{lowercase : ([a-z])*}")
    @Operation(summary = "Complex Response demo", description = "Viser hvordan POJO returnert indirekte via response kan beskrives inn i OpenAPI output")
    @APIResponse(content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = ComplexResponse.class)), description = "Return value is a complex POJO")
    public Response complexPathParam(@PathParam("lowercase") String lowerCaseString) {
        return Response.ok().entity(ComplexResponse.createComplexResponse(lowerCaseString)).build();
    }

}
