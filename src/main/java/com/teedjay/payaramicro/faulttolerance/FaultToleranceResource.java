package com.teedjay.payaramicro.faulttolerance;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("faulttolerance")
@RequestScoped
public class FaultToleranceResource {

    @Inject
    private CrazyDefunctResource crazyDefunctResource;

    @GET
    @Path("retry/{timesToFail}")
    public String retry(@PathParam("timesToFail") long timesToFail) {
        if (timesToFail < 1) return "Failing attempts in a row should be a positive number.";
        if (timesToFail > 4) {
            System.out.println("Expect call to fail with IllegalStateException after 4 attempts (first call and three retries).");
        } else {
            System.out.println("Expect call to work at attempt " + timesToFail);
        }
        return crazyDefunctResource.simulateCallingAFunctionThatFails(timesToFail);
    }

    @GET
    @Path("circuitBreaker/{id}")
    public String circuitBreaker(@PathParam("id") long id) {
        return "Allows system to fail fast after detecting a failure.";
    }

    @GET
    @Path("bulkhead/{id}")
    public String bulkhead(@PathParam("id") long id) {
        return "Bulkhead isolates faults in one part of the system from cascading out of control.";
    }

    @GET
    @Path("fallback/{alternative}")
    public String fallback(@PathParam("alternative") long alternative) {
        return "When execution fails you can provide an alternative return value.";
    }

    @GET
    @Path("timeout/{duration}")
    public String timeout(@PathParam("duration") long duration) {
        return "Define a duration for timeout that will abandon the execution.";
    }

}
