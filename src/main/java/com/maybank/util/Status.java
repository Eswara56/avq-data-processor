package com.maybank.util;

public class Status {
    private String messageType;
    private Double percentage;

    public Status() {
    }

    public Status(String messageType, Double percentage) {
        this.messageType = messageType;
        this.percentage = percentage;
    }

    public String getMessageType() {
        return messageType;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Status{" +
                "messageType='" + messageType + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
