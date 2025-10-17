package dev.shanuka.thesweetcupcakeshop;

import com.formdev.flatlaf.FlatLightLaf;
import dev.shanuka.thesweetcupcakeshop.util.FormManager;
import dev.shanuka.thesweetcupcakeshop.view.forms.Login;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point for the application.
 * Launches login form.
 * 
 * @author Shanuka
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Initialize FlatLaf with light theme
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // Show the login form asynchronously
        SwingUtilities.invokeLater(() -> {
            FormManager.ShowForm(FormManager.getLoginForm(), null);
        });
    }
}
