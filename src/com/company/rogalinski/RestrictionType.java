package com.company.rogalinski;

public enum RestrictionType {
    UNLIMITED("Unlimited"),
    TWO_SIDE("Two-side"),
    ONE_SIDE("One-side"),
    THREE_CARS_ON_BRIDGE("Three cars on bridge");

    String value;

    RestrictionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
