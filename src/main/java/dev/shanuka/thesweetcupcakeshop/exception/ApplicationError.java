package dev.shanuka.thesweetcupcakeshop.exception;

/**
 * A custom exception thrown when an error has been occured outside the business logic.
 * 
 * @author Shanuka
 */
public class ApplicationError extends Exception {
    /**
     * Constructs the exception with a detail message.
     * 
     * @param message The detail message.
     */
    public ApplicationError(String message) {
        super(message);
    }
    
    /**
     * Constructs the exception with a message and a cause (for "exception chaining").
     * 
     * @param message The detail message.
     * @param cause The original exception that led to this one.
     */
    public ApplicationError(String message, Throwable cause) {
        super(message, cause);
    }
}
