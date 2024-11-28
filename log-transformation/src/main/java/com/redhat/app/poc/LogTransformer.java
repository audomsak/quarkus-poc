package com.redhat.app.poc;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jboss.logging.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class LogTransformer implements Processor {

    private static final Logger log = Logger.getLogger(LogTransformer.class);

    final String regex = "(?<date>\\d{4}-\\d{2}-\\d{2})\\s(?<time>\\d{2}:\\d{2}:\\d{2},\\d+)\\s(?<level>INFO|WARN|ERROR)\\s{2}(?<logger>\\[.*\\])\\s(?<thread>\\(.*?\\))\\s(.*)";
    final Pattern pattern = Pattern.compile(regex);

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);

        log.info("Exchange body: " + body);

        Matcher matcher = pattern.matcher(body);
        matcher.find();

        LogMessage logMessage = new LogMessage();
        String message = body.substring(body.lastIndexOf(')') + 2);
        String logger = matcher.group("logger");

        logMessage.setMessage(message);
        logMessage.setLevel(matcher.group("level"));
        logMessage.setTimestamp(matcher.group("date") + " " + matcher.group("time"));
        logMessage.setLoggerName(logger.substring(1, logger.length() - 1));

        log.info("New exchange body: " + logMessage);

        exchange.getIn().setBody(logMessage);
    }
}
