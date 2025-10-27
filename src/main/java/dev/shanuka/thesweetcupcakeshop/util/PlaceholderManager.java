package dev.shanuka.thesweetcupcakeshop.util;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextField;

/**
 * Utility class for managing placeholders of text fields.
 * 
 * @author Shanuka
 */
public class PlaceholderManager {
    private static Map<String, String> placeholders = new HashMap<String, String>();
    
    static {
        // Initialize placeholders
        placeholders.put("emailField", "Email");
        placeholders.put("passwordField", "Password");
    }
    
    public static String getPlaceholderFor(String textFieldName) {
        return placeholders.getOrDefault(textFieldName, "");
    }
    
    public static void addPlaceholder(JTextField textField) {
        // Restore the placeholder if text field contains no text
        if(textField.getText().isBlank()) textField.setText(getPlaceholderFor(textField.getName()));
    }
    
    public static void removePlaceholder(JTextField textField) {
        // Avoid clearing text if it contains some text that's not the placeholder
        if(!textField.getText().isBlank() && !textField.getText().equals(getPlaceholderFor(textField.getName()))) return;
        
        // Otherwise, clear the text content
        textField.setText("");
    }
}
