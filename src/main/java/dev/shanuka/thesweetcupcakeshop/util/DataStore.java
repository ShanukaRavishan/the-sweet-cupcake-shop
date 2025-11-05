package dev.shanuka.thesweetcupcakeshop.util;

import dev.shanuka.thesweetcupcakeshop.enums.DataFile;
import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for saving and retrieving data from text files.
 *
 * @author Shanuka
 */
public class DataStore<T> {

    // Path to the text file containing data
    private String FilePath;

    // Store the provided model
    private Class<T> type;

    // Fields that the provided model has
    List<Field> fields;

    // Filed names of the provided model
    List<String> fieldNames;

    // Delimiter to split the objects
    String delimiter = "------- OBJECT -------";

    public DataStore(DataFile dataFileType, Class<T> type) {
        this.type = type;

        // Retrieve all fields that the provided model has
        fields = Arrays.asList(type.getDeclaredFields());

        // Convert them to a list of field names containing the name of each field
        Stream<String> fieldsStream = fields.stream().map(field -> field.getName());
        fieldNames = fieldsStream.collect(Collectors.toList());

        // Assign the resource path based on the enum.
        FilePath = switch (dataFileType) {
            case DataFile.USERS ->
                "data/users.txt";
            case DataFile.ITEMS ->
                "data/items.txt";
            case DataFile.CATEGORIES ->
                "data/categories.txt";
            case DataFile.ORDERS ->
                "data/orders.txt";
            default ->
                throw new IllegalArgumentException("Unsupported data file type: " + dataFileType);
        };
    }

    public void add(T object) throws ApplicationError {
        List<T> retrievedData = retrieveData();

        // Append the new object
        retrievedData.add(object);

        // Save the updated list of objects
        saveData(retrievedData);
    }

    /**
     * Converts a generic object into a multi-line formatted string using
     * reflection.
     *
     * @param object The object to be converted.
     * @return A formatted string representation (e.g., "key: value").
     * @throws ApplicationError if there's an issue accessing the object's
     * fields.
     */
    private String stringifyObject(T object) throws ApplicationError {
        StringBuilder sb = new StringBuilder();
        sb.append(delimiter).append("\n");

        // Get all fields from the object's class.
        List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());

        try {
            // Loop through each field to extract its name and value
            for (Field field : fields) {
                // Make private fields accessible
                field.setAccessible(true);

                // Get the name and value of the field from the object
                String name = field.getName();
                Object value = field.get(object);

                // Format Date fields as ISO UTC
                if (value instanceof Date) {
                    SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    
                    value = isoFormat.format((Date) value);
                }
                
                // Append the formatted "key: value" line
                sb.append(name)
                        .append(": ")
                        .append(String.valueOf(value))
                        .append("\n");
            }
        } catch (Exception e) {
            throw new ApplicationError("Could not access object fields for stringification! ", e);
        }

        // Return the complete string.
        return sb.toString();
    }

    // Saves data from "retrievedData" to the data file
    public void saveData(List<T> retrievedData) throws ApplicationError {
        StringBuilder sb = new StringBuilder();

        for (T object : retrievedData) {
            // Append the stringified object
            sb.append(stringifyObject(object));
        }

        // Get the final string content
        String str = sb.toString().trim();

        try {
            // Create a Path object from our FilePath
            Path path = Path.of(FilePath);

            // Write the string to the file
            Files.writeString(path, str);

        } catch (IOException e) {
            System.err.println("Error while saving data: " + e.getMessage());
            e.printStackTrace();

            throw new ApplicationError("Failed to save data to file: " + FilePath, e);
        }
    }

    public ArrayList<T> retrieveData() throws ApplicationError {
        // ArrayList to store each block in its strigified state
        ArrayList<String> objectDataBlocks = new ArrayList<String>();

        // ArrayList to store each block after parsing each block into a Map of key : value pairs
        ArrayList<Map<String, String>> parsedBlocks = new ArrayList<Map<String, String>>();

        // Retrieve data -> Parse them -> Encapsulate them into a <T> object
        try (InputStream inputStream = Files.newInputStream(Path.of(FilePath))) {
            // Throw an IOException if data file is missing 
            if (inputStream == null) {
                throw new IOException("Resource not found at: " + FilePath);
            }

            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Split the content string by the delimiter and add to objectDataBlocks
            objectDataBlocks.addAll(Arrays.asList(content.split(delimiter)));

            // Remove the first data block as it's an empty string
            // This happens because our data file begins with the delimiter
            // split() looks for the content before this first delimiter
            // Since there is nothing there, it returns an empty string "" as the first element of the array
            objectDataBlocks.removeFirst();

            // Filter out key : value pairs for each block and add them to parsedBlocks
            for (String block : objectDataBlocks) {
                // Extract the list of lines
                ArrayList<String> lines = new ArrayList<String>(Arrays.asList(block.split("\n")));

                // Remove the first element as it contains an empty string
                lines.removeFirst();

                Map<String, String> dataBlock = new HashMap<String, String>();

                for (String line : lines) {
                    String[] _line = line.split(":", 2);
                    dataBlock.put(_line[0], _line[1].trim());
                }

                parsedBlocks.add(dataBlock);
            }
        } catch (IOException e) {
            System.err.println("Error while reading data at: " + FilePath);
        }

        // Create a new instance of T using reflection
        ArrayList<T> data = new ArrayList<T>();

        // Iterate through each data block and create an object
        for (Map<String, String> block : parsedBlocks) {
            try {
                // Create a new instance of T using reflection
                T newInstance = type.getDeclaredConstructor().newInstance();

                for (String key : block.keySet()) {
                    if (fieldNames.contains(key)) {
                        Field field = type.getDeclaredField(key);

                        // Allow setting private fields
                        field.setAccessible(true);

                        // Convert the String value to a field type
                        Object value = convertStringToFieldType(block.get(key), field.getType());

                        // Set the value on newInstance
                        field.set(newInstance, value);
                    } // If the retrieved block and given type is incompatible 
                    // (i.e. block contains a key that type doen't have as a field)
                    else {
                        throw new ApplicationError("Error while parsing data store: " + FilePath);
                    }
                }

                // Add the populated newInstance to our data list
                data.add(newInstance);
            } catch (Exception e) {
                throw new ApplicationError("Error while parsing data store: " + FilePath, e.getCause());
            }
        }

        return data;
    }

    /**
     * Helper method to convert a String value to the correct type for a field.
     *
     * @param stringValue The value from the text file.
     * @param fieldType The type of the field we want to set (e.g. int.class,
     * String.class).
     *
     * @return The converted object.
     */
    private Object convertStringToFieldType(String stringValue, Class<?> fieldType) throws IllegalArgumentException {
        if (fieldType == String.class) {
            return stringValue;
        }

        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(stringValue);
        }

        if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(stringValue);
        }

        // Date from an ISO 8601 string
        if (fieldType == Date.class) {
            // This parses the standard format "2025-10-22T14:45:10.123Z"
            return Date.from(Instant.parse(stringValue));
        }

        
        // Handle enums by converting the string to an enum constant
        if (fieldType.isEnum()) {
            return Enum.valueOf((Class<Enum>) fieldType, stringValue);
        }

        throw new IllegalArgumentException("Unsupported field type for conversion: " + fieldType.getName());
    }
}
