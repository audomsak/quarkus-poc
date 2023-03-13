package org.acme;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        getContext().setManagementName("sms-processor");

        onException(Exception.class)
                .handled(true)
                .logStackTrace(true)
                .log(LoggingLevel.ERROR, "There was an error occurred. ${exception}");

        from("kafka:{{kafka.topic.name}}")
                .routeId("kafka-to-db")
                .log("Consumed messages: ${body}")
                .unmarshal().json(SmsMessage.class)
                .to("jpa:org.acme.SmsMessage")
                .log("The message was stored in database successfully");
    }
}
