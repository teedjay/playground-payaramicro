package com.teedjay.payaramicro.faulttolerance;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/faulttolerance")
@RequestScoped
public class FaultToleranceResource {

    @Inject
    private CrazyDefunctRepository crazyDefunctRepository;

    @GET
    @Path("retry/{timesToFail}")
    public String retry(@PathParam("timesToFail") long timesToFail) {
        if (timesToFail < 1) return "Failing attempts in a row should be a positive number.";
        if (timesToFail > 4) {
            System.out.println("Expect call to fail with IllegalStateException after 4 attempts (first call and three retries).");
        } else {
            System.out.println("Expect call to work at attempt " + timesToFail);
        }
        return crazyDefunctRepository.functionThatFailsMultipleTimesInARow(timesToFail);
    }

    @GET
    @Path("timeout/{duration}")
    public String timeout(@PathParam("duration") long duration) {
        return crazyDefunctRepository.slowFunctionThatSleepsForAWhile(duration);
    }

    @GET
    @Path("fallback/{duration}")
    public String fallback(@PathParam("duration") long duration) {
        return crazyDefunctRepository.slowFunctionWithFallbackAfterAWhile(duration);
    }

    @GET
    @Path("bulkhead/{duration}")
    public String bulkhead(@PathParam("duration") long duration) {
        return crazyDefunctRepository.functionThatOnlyAllowsTwoConcurrentExecutions(duration);
    }

    @GET
    @Path("circuitbreaker/{shouldFail}")
    public String circuitBreaker(@PathParam("shouldFail") boolean shouldFail) {
        return crazyDefunctRepository.functionThatFailsOnCommand(shouldFail);
    }

}

