package com.example.covidcasemanager.exception;

import java.text.MessageFormat;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resourceId) {
        super(MessageFormat.format("Resource with id {0} already exists", resourceId));
    }
}
