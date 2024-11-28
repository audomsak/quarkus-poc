package com.redhat.app.poc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class Route extends RouteBuilder {
    @Inject
    Quote quote;

    @Override
    public void configure() throws Exception {
        from("timer://quote?fixedRate=true&period=2000")
                .routeId("quote-generator")
                .bean(quote, "getQuote")
                .log("${body}");
    }
}
