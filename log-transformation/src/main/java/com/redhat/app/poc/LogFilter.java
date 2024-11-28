package com.redhat.app.poc;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class LogFilter implements Predicate {

    final String regex = "(?<date>\\d{4}-\\d{2}-\\d{2})\\s(?<time>\\d{2}:\\d{2}:\\d{2},\\d+)\\s(?<level>INFO|WARN|ERROR)\\s{2}(?<logger>\\[.*\\])\\s(?<thread>\\(.*?\\))\\s(.*)";
    final Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean matches(Exchange exchange) {
        if (null == exchange.getIn() || null == exchange.getIn().getBody()) {
            return false;
        }

        String body = exchange.getIn().getBody(String.class);
        Matcher matcher = pattern.matcher(body);

        return matcher.find() && matcher.group("logger").equals("[quote-generator]");
    }
}
