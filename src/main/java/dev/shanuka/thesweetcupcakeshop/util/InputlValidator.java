package dev.shanuka.thesweetcupcakeshop.util;

import java.util.regex.Pattern;

/**
 * Utility class for validating input values.
 * 
 * @author Shanuka
 */
public class InputlValidator {
    /**
     * Checks if a given string is a valid email address according to a regex pattern.
     * @param email The string to be validated
     * @return {@code true} if the string is a valid email address, otherwise {@code false}.
     */
    public static boolean validateEmail(String email) {
        // Validate email using a RegEx for validating the basic structure of an email.
        return email.matches("^.+@.+\\..+$");
    }
}
