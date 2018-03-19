package com.teedjay.payaramicro.quotes;

import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.jglue.cdiunit.jaxrs.SupportJaxRs;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

@RunWith(CdiRunner.class)
@SupportJaxRs
@InRequestScope
public class QuotesResourceTest {

    @Inject
    private QuotesResource quotesResource;

    @Test
    public void listAllQuotes() {
        assertEquals(12, quotesResource.listAllQuotes().size());
    }

    @Test
    public void getQuote() {
        String uuid = quotesResource.listAllQuotes().get(0).id;
        Response response = quotesResource.getQuote(uuid);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void getNonExistingQuote() {
        Response response = quotesResource.getQuote("does-not-exist");
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
        QuoteError error = (QuoteError) response.getEntity();
        assertEquals("Quote not found", error.message);
    }

    @Test
    public void addQuote() throws Exception {
        Quote quote = quotesResource.listAllQuotes().get(0);
        Response response = quotesResource.addQuote(quote);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
        assertTrue("Expected location to be 'quote/uuid'", response.getHeaderString(HttpHeaders.LOCATION).startsWith("quotes/"));
    }

    @Test
    public void deleteQuote() {
        Quote firstQuote = quotesResource.listAllQuotes().get(0);
        Response response = quotesResource.deleteQuote(firstQuote.id);
        assertEquals(Response.Status.OK, response.getStatusInfo());
        Quote deletedQuote = (Quote) response.getEntity();
        assertEquals(firstQuote.id, deletedQuote.id);
        assertEquals(firstQuote.quote, deletedQuote.quote);
        assertEquals(firstQuote.author, deletedQuote.author);
    }

    @Test
    public void deleteNonExistingQuote() {
        Response response = quotesResource.deleteQuote("does-not-exist");
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
        QuoteError error = (QuoteError) response.getEntity();
        assertEquals("Quote not found, unable to delete", error.message);
    }

}