package dev.shanuka.thesweetcupcakeshop;

import com.formdev.flatlaf.FlatLightLaf;
import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.service.AuthService;
import dev.shanuka.thesweetcupcakeshop.util.FormManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point for the application. Launches login form.
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

        try {
            boolean isLoginSuccess = AuthService.login("sachintha@gmail.com", "#Wolf@2003");
            
            System.out.println(isLoginSuccess ? "Login successful!" : "Login failed! Please check your password.");
        } catch (NotFoundError ex) {
            System.err.println("Login faild. Email is not associated with any user: sachintha@gmail.com.");
        } catch (ApplicationError ex) {
            System.err.println("Login faild. An unexpected error has been occured.");
        }
        
        // Show the login form asynchronously
        SwingUtilities.invokeLater(() -> {
            FormManager.ShowForm(FormManager.getDashboardForm());
        });
    }
}
