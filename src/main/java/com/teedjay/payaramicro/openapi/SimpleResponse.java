package com.teedjay.payaramicro.openapi;

public class SimpleResponse {

    public String id;
    public String description;

    public static SimpleResponse createDemoResponse(String id) {
        SimpleResponse dr = new SimpleResponse();
        dr.id = id;
        dr.description = "Dette er ID'en du sendte inn '" + id + "'";
        return dr;
    }

}
