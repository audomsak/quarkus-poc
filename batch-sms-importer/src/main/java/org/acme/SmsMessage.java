package org.acme;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsMessage {
    @JsonProperty
    private String mobileNumber;

    @JsonProperty
    private String textMessage;

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
