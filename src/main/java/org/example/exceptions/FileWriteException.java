package org.example.exceptions;

public class FileWriteException extends FileProcessingException{
    public FileWriteException(String message, Throwable cause) {
        super (message, cause);
    }

    public FileWriteException(String message) {
        super(message);
    }
}
