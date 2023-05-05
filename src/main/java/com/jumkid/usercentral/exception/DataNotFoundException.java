package com.jumkid.usercentral.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String entity, Object value) {
        super(String.format("The entity %s with the value '%s' could not be found.", entity, value));
    }

    public DataNotFoundException(String entityId) {
        super(String.format("The entity with id '%s' could not be found.", entityId));
    }

}
