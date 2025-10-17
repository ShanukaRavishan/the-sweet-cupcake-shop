package dev.shanuka.thesweetcupcakeshop.util;

import dev.shanuka.thesweetcupcakeshop.view.forms.Dashboard;
import dev.shanuka.thesweetcupcakeshop.view.forms.Login;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Manages the lifecycle and navigation of all JFrames in the application.
 * 
 * @author Shanuka
 */
public class FormManager {
    // Static fields to store forms
    private static JFrame LoginForm;
    private static JFrame DashboardForm;
    
    private FormManager() {}

    /**
     * Gets the instance of Login JFrame.
     */
    public static JFrame getLoginForm() {
        if(LoginForm == null) LoginForm = new Login();
        return LoginForm;
    }
    
    /**
     * Gets the instance of Dashboard JFrame.
     */
    public static JFrame getDashboardForm() {
        if(DashboardForm == null) DashboardForm = new Dashboard();
        return DashboardForm;
    }
    
    /**
     * Shows a target form and optionally hides the current form.
     *
     * @param targetForm  JFrame that should be shown.
     * @param currentForm JFrame to be hidden (Optional).
     * If empty, no form will be hidden.
     */
    public static void ShowForm(JFrame targetForm, JFrame currentForm) {
        // If a current form exists, position the new form over it.
        if (currentForm != null) {
            targetForm.setLocationRelativeTo(currentForm); 
        } 
        
        // Otherwise, center the target form on the screen.
        else {
            targetForm.setLocationRelativeTo(null);
        }

        targetForm.setVisible(true); // Make the target form visible.

        // Hide the current form if it was provided.
        if (currentForm != null) {
            currentForm.setVisible(false);
        }
    }
    
    /**
     * Sets the application icon for a JFrame.
     * 
     * @param form JFrame that the icon must be applied to.
     */
    public static void setAppIcon(JFrame form) {
        try {
            // Path to the icon within the resources folder
            String iconPath = "/images/icons/cupcake_icon.png";

            // Load the image resource from the classpath
            URL imageUrl = FormManager.class.getResource(iconPath);

            // Check if the resource was found
            if (imageUrl == null) {
                System.err.println("Resource not found: " + iconPath);
                return;
            }

            // Create an ImageIcon and set it as the frame's icon
            ImageIcon icon = new ImageIcon(imageUrl);
            form.setIconImage(icon.getImage());

        } catch (Exception e) {
            System.err.println("Error setting application icon: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
