package com.advanced.bigdata.indexing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used for the conflict request in the service
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
