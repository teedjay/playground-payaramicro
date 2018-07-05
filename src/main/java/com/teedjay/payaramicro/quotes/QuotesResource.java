package com.teedjay.payaramicro.quotes;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/quotes")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class QuotesResource {

    @Inject
    private QuoteRepository quoteRepository;

    @GET
    public List<Quote> listAllQuotes() {
        return quoteRepository.allQuotes();
    }

    @GET
    @Path("{id}")
    public Response getQuote(@PathParam("id") String id) {
        Quote quote = quoteRepository.findQuoteById(id);
        if (quote == null) return createBadRequestWithMessage("Quote not found");
        return Response.ok(quote).build();
    }

    @POST
    public Response addQuote(Quote quote) throws URISyntaxException {
        String uuid = quoteRepository.insertQuote(quote);
        return Response.created(new URI("quotes/" + uuid)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteQuote(@PathParam("id") String id) {
        Quote quote = quoteRepository.removeQuote(id);
        if (quote == null) return createBadRequestWithMessage("Quote not found, unable to delete");
        return Response.ok(quote).build();
    }

    private Response createBadRequestWithMessage(String message) {
        QuoteError quoteError = new QuoteError();
        quoteError.message = message;
        return Response.status(Response.Status.BAD_REQUEST).entity(quoteError).build();
    }

}
