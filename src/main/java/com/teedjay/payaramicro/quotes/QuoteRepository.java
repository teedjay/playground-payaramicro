package com.teedjay.payaramicro.quotes;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class QuoteRepository {

    private List<Quote> quotes = new ArrayList<>();

    @PostConstruct
    public void init() {
        Arrays.stream(steveJobsQuotes).forEach(this::addAsNewQuote);
    }

    public Quote findQuoteById(String uuid){
        return quotes.stream().filter(q -> q.id.equals(uuid)).findFirst().orElse(null);
    }

    public List<Quote> allQuotes() {
        return quotes;
    }

    public String insertQuote(Quote quote) {
        String newId = UUID.randomUUID().toString();
        quote.id = newId;
        quotes.add(quote);
        return newId;
    }

    public Quote removeQuote(String uuid) {
        Quote quote = findQuoteById(uuid);
        if (quote != null) quotes.remove(quote);
        return quote;
    }

    private void addAsNewQuote(String text) {
        Quote quote = new Quote();
        quote.quote = text;
        quote.author = "Steve Jobs";
        insertQuote(quote);
    }

    private static String[] steveJobsQuotes = {
            "Be a yardstick of quality. Some people aren't used to an environment where excellence is expected.",
            "Design is not just what it looks like and feels like. Design is how it works.",
            "Great things in business are never done by one person. They're done by a team of people.",
            "Sometimes life is going to hit you in the head with a brick. Don't lose faith.",
            "You can't just ask customers what they want and then try to give that to them. By the time you get it built, they'll want something new.",
            "It's really hard to design products by focus groups. A lot of times, people don't know what they want until you show it to them.",
            "I’m as proud of many of the things we haven’t done as the things we have done. Innovation is saying no to a thousand things.",
            "Have the courage to follow your heart and intuition. They somehow know what you truly want to become.",
            "Sometimes when you innovate, you make mistakes. It is best to admit them quickly, and get on with improving your other innovations.",
            "Quality is more important than quantity. One home run is much better than two doubles.",
            "The people who are crazy enough to think they can change the world are the ones who do.",
            "Things don’t have to change the world to be important."
    };

}
