package com.jumkid.usercentral.event;

public enum SystemModules {

    ACTIVITY("activity"),
    CONTENT("content"),
    VEHICLE("vehicle");

    private final String value;

    SystemModules(String value) { this.value = value; }

    public String value() { return this.value; }
}
