package dev.shanuka.thesweetcupcakeshop.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Loads and stores custom fonts used in the application.
 * 
 * @author Shanuka
 */
public class Fonts {
    private Fonts() {}
    
    public static Font robotoRegular;
    public static Font robotoMedium;
    public static Font robotoBold;
    
    static {
        try {
            // Load all required fonts
            robotoRegular = loadFont("/fonts/Roboto-Regular.ttf");
            robotoMedium = loadFont("/fonts/Roboto-Medium.ttf");
            robotoBold = loadFont("/fonts/Roboto-Bold.ttf");
        } catch (IOException | FontFormatException e) {
            System.err.println("Error loading custom fonts: " + e);
            
            // Use fallback fonts on error
            robotoRegular = new Font("SansSerif", Font.PLAIN, 12);
            robotoMedium = new Font("SansSerif", Font.PLAIN, 12);
            robotoBold = new Font("SansSerif", Font.BOLD, 12);
        }
    }
    
    /**
     * Loads a font from the the given path.
     * 
     * @param path Path to the font file (e.g. "/resources/fonts/Roboto-Regular.ttf")
     * @return The loaded Font object.
     * 
     * @throws IOException If the font file is not found.
     * @throws FontFormatException If the font file is corrupted or not a valid format.
     */
    private static Font loadFont(String path) throws IOException, FontFormatException {
        // The path must be absolute from the root of the classpath
        InputStream is = Fonts.class.getResourceAsStream(path);
        
        if (is == null) {
            throw new IOException("Font file not found at " + path);
        }
        
        // Create and return a base font of size 1.
        // We can use the deriveFont() method later to set a desired font size.
        return Font.createFont(Font.TRUETYPE_FONT, is);
    }
}
