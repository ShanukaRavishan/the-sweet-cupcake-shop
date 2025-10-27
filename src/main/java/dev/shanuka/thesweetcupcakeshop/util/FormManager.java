package dev.shanuka.thesweetcupcakeshop.util;

import dev.shanuka.thesweetcupcakeshop.view.forms.Dashboard;
import dev.shanuka.thesweetcupcakeshop.view.forms.Login;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Manages the lifecycle and navigation of all JFrames in the application.
 *
 * @author Shanuka
 */
public class FormManager {

    // Static fields to store forms
    private static JFrame LoginForm;
    private static JFrame DashboardForm;

    private FormManager() {
    }

    /**
     * Gets the instance of Login JFrame.
     */
    public static JFrame getLoginForm() {
        if (LoginForm == null) {
            LoginForm = new Login();
        }
        return LoginForm;
    }

    /**
     * Gets the instance of Dashboard JFrame.
     */
    public static JFrame getDashboardForm() {
        if (DashboardForm == null) {
            DashboardForm = new Dashboard();
        }
        return DashboardForm;
    }

    /**
     * Shows a target form and centers it on the screen. Neither hides the
     * current form not disposes it
     *
     * @param targetForm JFrame that should be shown
     */
    public static void ShowForm(JFrame targetForm) {
        ShowForm(targetForm, null, false);
    }

    /**
     * Shows a target form and hides the current form without disposing the
     * currentForm
     *
     * @param targetForm JFrame that should be shown
     * @param currentForm JFrame to be hidden
     */
    public static void ShowForm(JFrame targetForm, JFrame currentForm) {
        ShowForm(targetForm, currentForm, false);
    }

    /**
     * Shows a target form and optionally hides or disposes the current form
     *
     * @param targetForm JFrame that should be shown
     * @param currentForm JFrame to be hidden or disposed
     * @param disposeCurrent If true, the currentForm will be disposed
     */
    public static void ShowForm(JFrame targetForm, JFrame currentForm, boolean disposeCurrent) {
        // If a current form exists, position the new form over it
        if (currentForm != null) {
            targetForm.setLocationRelativeTo(currentForm);
        } // Otherwise center the target form on the screen
        else {
            targetForm.setLocationRelativeTo(null);
        }

        targetForm.setVisible(true); // Make the target form visible

        // Hide or dispose the current form if it was provided
        if (currentForm != null) {
            if (disposeCurrent) {
                currentForm.dispose(); // Dispose the form (releases resources)

                // Nullify the static reference so getForm() can create a new one next time
                if (currentForm instanceof Login) {
                    LoginForm = null;
                } else if (currentForm instanceof Dashboard) {
                    DashboardForm = null;
                }

            } else {
                currentForm.setVisible(false); // Just hide the form
            }
        }
    }

    /**
     * Clears a container panel and sets a new panel as its content
     * 
     * Removes all existing components from the container, sets its layout to BorderLayout, 
     * and add the new content panel to the CENTER, which makes it fill the container
     *
     * @param container The JPanel to be cleared and updated
     * @param newContent The new JPanel to be displayed
     */
    public static void setContentPanel(javax.swing.JPanel container, javax.swing.JPanel newContent) {
        // Clear all components from the container panel
        container.removeAll();

        // Set the layout
        container.setLayout(new java.awt.BorderLayout());

        // Add the new panel to fill the center
        container.add(newContent, java.awt.BorderLayout.CENTER);

        // Revalidate and repaint the container to show the changes
        container.revalidate();
        container.repaint();
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
