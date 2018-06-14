package com.teedjay.payaramicro.metrics;

import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Random;

@Timed(name = "timed", absolute = true)
@Metered(name = "metered", absolute = true)
@RequestScoped
@Path("metricsdemo")
public class MetricsResource {

    private static final int MIN_SLEEP = 1;
    private static final int MAX_SLEEP = 10_000;

    private static final Random random = new Random();

    @GET
    public Response sleepRandom() {
        int millis = random.nextInt(MAX_SLEEP);
        return Response.ok().entity(sleep(millis)).build();
    }

    @GET
    @Path("{millis}")
    public Response sleepFixed(@PathParam("millis") int millis) {
        return Response.ok().entity(sleep(millis)).build();
    }

    private String sleep(int millis) {
        if (millis < MIN_SLEEP) millis = MIN_SLEEP;
        if (millis > MAX_SLEEP) millis = MAX_SLEEP;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            /* ignore, doesn't really matter, this is just a demo */
        }
        return "Slept for " + millis + " milliseconds";
    }

}
