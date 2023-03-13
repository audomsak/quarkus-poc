package org.acme;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class Route extends RouteBuilder {
    private static final String SFTP_ENDPOINT = "sftp://{{ftp.host}}:{{ftp.port}}/upload" +
            "?username={{ftp.username}}" +
            "&password={{ftp.password}}" +
            "&download=true" +
            "&move=done";

    @Override
    public void configure() throws Exception {
        getContext().setManagementName("batch-sms-importer");

        onException(Exception.class)
                .handled(true)
                .logStackTrace(true)
                .log(LoggingLevel.ERROR, "There was an error occurred. ${exception}");

        from(SFTP_ENDPOINT)
                .routeId("sftp-to-kafka")
                .log("Consumed messages: ${body}")
                .split(body().tokenize("\n"))
                .log("Split message: ${body}")
                .process(new MessageTransformer())
                .marshal().json()
                .log("Sending message to Kafka: ${body}")
                .to("kafka:{{kafka.topic.name}}")
                .log(("The message was sent successfully."));
    }
}
