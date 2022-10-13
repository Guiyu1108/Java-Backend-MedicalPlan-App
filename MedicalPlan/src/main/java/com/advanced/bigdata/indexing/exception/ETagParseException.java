package com.advanced.bigdata.indexing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used for the bad request of ETAG in the service
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ETagParseException extends RuntimeException{
    public ETagParseException( String message ) {
        super(message);
    }
    public ETagParseException() {
    }
}
