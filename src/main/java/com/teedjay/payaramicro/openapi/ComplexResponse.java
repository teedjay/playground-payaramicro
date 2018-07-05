package com.teedjay.payaramicro.openapi;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name="ComplexResponse", description="POJO that is used to return some data structure from the API.")
public class ComplexResponse {

    @Schema(required = true, example = "lowercasetext")
    public String id;

    @Schema(example = "Human readable description of the ID")
    public String description;

    public static ComplexResponse createComplexResponse(String id) {
        ComplexResponse cr = new ComplexResponse();
        cr.id = id;
        cr.description = "Her er ID'en du sendte inn '" + id + "'";
        return cr;
    }

}
