package dev.shanuka.thesweetcupcakeshop.util;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Utility class for showing messages to the user
 * 
 * @author Shanuka
 */
public class Messages {
    /**
     * Displays an error message popup.
     * 
     * @param parent Parent form that the message is shown from.
     * @param title Error title to show.
     * @param message Error message to show.
     */
    public static void showError(JFrame parent, String title, String message) {
        JLabel label = new JLabel(message);
        label.putClientProperty(FlatClientProperties.STYLE, "font: 14 'Noto Sans'; foreground: #222831;");

        JOptionPane.showMessageDialog(
                parent,
                label,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Displays an application error message popup.
     * 
     * @param parent Parent form that the message is shown from.
     */
    public static void showApplicationError(JFrame parent, String title, String message) {
        showError(parent, "Application Error", "An unexpected error has occurred.");
    }
}
