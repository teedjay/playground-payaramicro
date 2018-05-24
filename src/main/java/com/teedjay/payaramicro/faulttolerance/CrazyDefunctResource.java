package com.teedjay.payaramicro.faulttolerance;

import org.eclipse.microprofile.faulttolerance.Retry;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CrazyDefunctResource {

    private long numberOfTimesTried;

    @Retry(maxRetries = 3, retryOn = { IllegalStateException.class })
    public String simulateCallingAFunctionThatFails(long timesToFailInARow) {
        numberOfTimesTried++;
        if (timesToFailInARow > numberOfTimesTried) {
            String failure = "This is try " + numberOfTimesTried + ", triggers failure";
            System.out.println(failure);
            throw new IllegalStateException(failure);
        }
        String success = "This is try " + numberOfTimesTried + ", returning success";
        System.out.println(success);
        return success;
    }

}
