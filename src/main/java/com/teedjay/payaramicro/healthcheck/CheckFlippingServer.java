package com.teedjay.payaramicro.healthcheck;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@Health
@ApplicationScoped
public class CheckFlippingServer implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        if (System.currentTimeMillis() % 2 == 1) {
            return HealthCheckResponse.named("flipper").up().build();
        } else {
            return HealthCheckResponse.named("flipper").down().build();
        }
    }

}

