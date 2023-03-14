package org.acme;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        getContext().setManagementName("sms-processor");
        getContext().setUseMDCLogging(true);

        onException(Exception.class)
                .handled(true)
                .logHandled(true)
                .logStackTrace(true)
                .log(LoggingLevel.ERROR, "There was an error occurred. ${exception}");

        from("kafka:{{kafka.topic.name}}?groupId=sms-processor-group")
                .routeId("kafka-to-db")
                .log("Consumed messages: ${body}")
                .unmarshal().json(SmsMessage.class)
                .to("jpa:org.acme.SmsMessage")
                .log("The message was stored in database successfully");
    }
}
