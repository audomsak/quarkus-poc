package com.redhat.app.poc;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Random;


@ApplicationScoped
public class Quote {
    // List to hold our quotes we will load in

    static private final ArrayList<String> listQuotes = new ArrayList<String>(0);
    static private final Random r = new Random();

    static {
        listQuotes.add("May the Force be with you.");
        listQuotes.add("There's no place like home.");
        listQuotes.add("I'm the king of the world!");
        listQuotes.add("You're gonna need a bigger boat.");
        listQuotes.add("Houston, we have a problem.");
        listQuotes.add("You can't handle the truth!");
        listQuotes.add("If you build it, he will come.");
        listQuotes.add("Just keep swimming.");
        listQuotes.add("That'll do, pig. That'll do.");
        listQuotes.add("It was beauty killed the beast.");
        listQuotes.add("Nobody puts Baby in a corner.");
        listQuotes.add("Well, nobody's perfect.");
    }

    public String getQuote() {
        return listQuotes.get(r.nextInt(listQuotes.size()));
    }
}

