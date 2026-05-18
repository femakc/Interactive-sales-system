package org.example.exceptions;

public class ParseException extends FileProcessingException{
    public ParseException(String message, Throwable cause) {
        super (message, cause);
    }

    public ParseException(String message) {
        super(message);
    }
}
