package com.teedjay.payaramicro.quotes;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(CdiRunner.class)
public class QuoteRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_QUOTES = 13;

    @Inject
    private QuoteRepository quoteRepository;

    @Test
    public void makeSureQuoteRepositoryHasBeenInjected() {
        assertNotNull("CDI has not been started, no QuoteRepository injected", quoteRepository);
    }

    @Test
    public void findQuoteById() {
        Quote firstQuote = quoteRepository.allQuotes().get(0);
        Quote readQuote = quoteRepository.findQuoteById(firstQuote.id);
        assertEquals(firstQuote.id, readQuote.id);
        assertEquals(firstQuote.quote, readQuote.quote);
        assertEquals(firstQuote.author, readQuote.author);
    }

    @Test
    public void allQuotes() {
        assertEquals(EXPECTED_NUMBER_OF_QUOTES, quoteRepository.allQuotes().size());
    }

    @Test
    public void insertQuote() {
        Quote quoteToBeInserted = new Quote();
        quoteToBeInserted.quote = "Det er ofte lysere om dagen enn natta";
        quoteToBeInserted.author = "Unknown";
        String uuid = quoteRepository.insertQuote(quoteToBeInserted);
        Quote quoteThatWasInserted = quoteRepository.findQuoteById(uuid);
        assertEquals(uuid, quoteThatWasInserted.id);
        assertEquals(quoteToBeInserted.quote, quoteThatWasInserted.quote);
        assertEquals(quoteToBeInserted.author, quoteThatWasInserted.author);
        assertEquals(EXPECTED_NUMBER_OF_QUOTES + 1, quoteRepository.allQuotes().size());
    }

    @Test
    public void removeQuote() {
        Quote quoteToBeRemoved = quoteRepository.allQuotes().get(0);
        Quote quoteThatWasRemoved = quoteRepository.removeQuote(quoteToBeRemoved.id);
        assertEquals(quoteToBeRemoved.id, quoteThatWasRemoved.id);
        assertEquals(quoteToBeRemoved.quote, quoteThatWasRemoved.quote);
        assertEquals(quoteToBeRemoved.author, quoteThatWasRemoved.author);
        Quote quoteThatHasAlreadyBeenRemoved = quoteRepository.removeQuote(quoteThatWasRemoved.id);
        assertNull("This quote should already be removed", quoteThatHasAlreadyBeenRemoved);
    }

}
