package com.teedjay.payaramicro.events;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("events")
@RequestScoped
public class EventsResource {

    @Inject
    Event<TestEvent> eventSender;

    @GET
    public String triggerNewEvent() {
        TestEvent eventData = new TestEvent();
        eventSender.fire(eventData);
        return String.format("Event with message '%s' was sent\n", eventData.message);
    }

}
