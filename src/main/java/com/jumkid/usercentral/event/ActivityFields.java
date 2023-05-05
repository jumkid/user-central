package com.jumkid.usercentral.event;

public enum ActivityFields {

    NAME("name"),
    CREATED_BY("createdBy"),
    ASSIGNEE_ID("assigneeId"),
    ACTIVITY_ASSIGNEES("activityAssignees");

    private final String value;

    ActivityFields(String value) { this.value = value; }

    public String value() { return this.value; }
}
