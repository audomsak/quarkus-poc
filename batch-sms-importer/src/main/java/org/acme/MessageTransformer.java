package org.acme;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MessageTransformer implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        // Custom transformation
        String message = exchange.getIn().getBody(String.class);
        String[] messages = message.split("\\|");

        SmsMessage  smsMessage = new SmsMessage();
        smsMessage.setMobileNumber(messages[0]);
        smsMessage.setTextMessage(messages[1]);

        exchange.getIn().setBody(smsMessage);
    }
}
