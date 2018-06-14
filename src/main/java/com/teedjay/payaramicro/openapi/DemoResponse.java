package com.teedjay.payaramicro.openapi;

public class DemoResponse {

    public String id;
    public String description;

    public static DemoResponse createDemoResponse(String id) {
        DemoResponse dr = new DemoResponse();
        dr.id = id;
        dr.description = "Dette er ID'en du sendte inn '" + id + "'";
        return dr;
    }

}
