package dev.shanuka.thesweetcupcakeshop.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * A final utility class to store commonly used constants.
 * 
 * @author Shanuka
 */
public final class AppConstants {
    private AppConstants() { }
    
    /** Formats dates like 20/10/2025 */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    
    /** Formats numbers with commas (e.g., 58,000) */
    public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###");

    /** Formats currency with commas and currency symbol (e.g., 58,000 LKR) */
    public static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,### LKR");
}
