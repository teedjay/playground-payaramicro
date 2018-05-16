package com.teedjay.payaramicro.events;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

/**
 * Example : In a real application the received event could add a billing record for invoicing
 * without the sender of the event knowing about this happening at all.
 */
@Dependent
public class BillingObserver {

    public void receivesTestEvents(@Observes TestEvent event) {
        System.out.printf("BillingObserver handled TestEvent with message %s%n", event.message);
    }

}
