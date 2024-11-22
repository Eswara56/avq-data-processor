package com.maybank.util;

public enum StatusMessageType {
    COMPLETED("COMPLETED"),
    FAILED("FAILED"),
    IN_PROGRESS("IN_PROGRESS"),
    PENDING("PENDING");

    private String value;

    StatusMessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
