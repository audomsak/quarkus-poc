package com.redhat.app.poc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class Route extends RouteBuilder {
    @Inject
    private LogTransformer messageTransformer;

    @Inject LogFilter validMessage;

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .handled(true)
                .logHandled(true)
                .logStackTrace(true)
                .log(LoggingLevel.ERROR, "There was an error occurred. ${exception}");

        // Consume JSON logs and insert to DB
        from("kafka:{{kafka.app.json.log.topic.name}}?groupId=log-processor")
                .routeId("json-log-to-db")
                .log("Consumed message: ${body}")
                .unmarshal().json(LogMessage.class)
                .to("direct:log-to-db");

        // Consume Text logs and insert to DB also publish to a Kafka topic
        from("kafka:{{kafka.app.text.log.topic.name}}?groupId=log-processor")
                .routeId("text-log-to-db")
                .log("Consumed message: ${body}")
                .choice()
                    .when(validMessage)
                        .process(messageTransformer)
                        .to(ExchangePattern.InOnly, "direct:log-to-db")
                        .marshal().json()
                        .to("kafka:{{kafka.analytic.topic.name}}")
                    .otherwise()
                        .log("The message was discarded.")
                .end();

        // Common route accepts LogMessage object to be inserted to DB
        from("direct:log-to-db")
                .routeId("log-to-db")
                .to("jpa:com.redhat.app.poc.LogMessage")
                .log("The message was stored in database successfully");

    }
}
