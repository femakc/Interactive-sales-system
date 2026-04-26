package org.example.exceptions;

public class FileReadException extends FileProcessingException {
    public FileReadException(String message, Throwable cause) {
        super (message, cause);
    }
}
