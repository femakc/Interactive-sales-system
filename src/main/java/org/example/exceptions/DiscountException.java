package org.example.exceptions;

public class DiscountException extends IllegalArgumentException {
    public DiscountException(String message,  Throwable cause)
    {
        super(message, cause);
    }

    public DiscountException(String message)
    {
        super(message);
    }
}
