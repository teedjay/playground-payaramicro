package com.teedjay.payaramicro.faulttolerance;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
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

    @Timeout(1000)
    public String slowFunctionThatSleepsForAWhile(long milliSeconds) {
        return sleep(milliSeconds);
    }

    @Timeout(1000)
    @Fallback(fallbackMethod = "fallbackForSlowFunction")
    public String slowFunctionWithFallbackAfterAWhile(long milliSeconds) {
        return sleep(milliSeconds);
    }

    public String fallbackForSlowFunction(long milliSeconds) {
        return "Timeout was exceeded, we return this fallback text instead.";
    }

    @Bulkhead(2)
    public String callFunctionThatOnlyAllowsTwoConcurrentExecutions(long milliSeconds) {
        return sleep(milliSeconds);
    }

    private String sleep(long milliSeconds) {
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

}
