package dev.shanuka.thesweetcupcakeshop.exception;

/**
 * A custom exception thrown when an existing entry with matching attribute(s) is found.
 * 
 * @author Shanuka
 */
public class DuplicateEntryError extends Exception {
    /**
     * Constructs the exception with a detail message.
     * 
     * @param message The detail message.
     */
    public DuplicateEntryError(String message) {
        super(message);
    }
    
    /**
     * Constructs the exception with a message and a cause (for "exception chaining").
     * 
     * @param message The detail message.
     * @param cause The original exception that led to this one.
     */
    public DuplicateEntryError(String message, Throwable cause) {
        super(message, cause);
    }
}
