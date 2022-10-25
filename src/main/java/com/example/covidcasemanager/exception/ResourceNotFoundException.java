package com.example.covidcasemanager.exception;

import java.text.MessageFormat;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceId) {
        super(MessageFormat.format("Resource with id {0} not found", resourceId));
    }
}
