package dev.shanuka.thesweetcupcakeshop.exception;

/**
 * A custom exception thrown when an object with the given field : value cannot be found.
 * 
 * @author Shanuka
 */
public class NotFoundError extends Exception {
    /**
     * Constructs the exception with a detail message.
     * 
     * @param message The detail message.
     */
    public NotFoundError(String message) {
        super(message);
    }
    
    /**
     * Constructs the exception with a message and a cause (for "exception chaining").
     * 
     * @param message The detail message.
     * @param cause The original exception that led to this one.
     */
    public NotFoundError(String message, Throwable cause) {
        super(message, cause);
    }
}
