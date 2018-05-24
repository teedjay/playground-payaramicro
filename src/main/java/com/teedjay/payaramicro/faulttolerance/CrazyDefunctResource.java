package com.teedjay.payaramicro.faulttolerance;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

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

    @Timeout(2000)
    public String slowFunctionThatSleepsForAWhile(long milliSeconds) {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            long duration = System.currentTimeMillis() - start;
            String failure = "Thread was interrupted after " + duration + "ms, while sleeping for " + milliSeconds + "ms.";
            System.out.println(failure);
            return failure;
        }
        String success =  "Function simulated work for " + milliSeconds + "ms.";
        System.out.println(success);
        return success;
    }

    @Timeout(1000)
    @Fallback(fallbackMethod = "fallbackForSlowFunction")
    public String slowFunctionWithFallbackAfterAWhile(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            String failure = "Thread was interrupted while sleeping for " + milliSeconds + "ms.";
            System.out.println(failure);
            return failure;
        }
        String success =  "Function simulated work for " + milliSeconds + "ms.";
        System.out.println(success);
        return success;
    }

    public String fallbackForSlowFunction(long milliSeconds) {
        return "Timeout was exceeded, we return this fallback text instead";
    }

}
