package dev.shanuka.thesweetcupcakeshop.service;

import dev.shanuka.thesweetcupcakeshop.enums.DataFile;
import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.model.Item;
import dev.shanuka.thesweetcupcakeshop.util.DataStore;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A static service class for managing all business logic related to the Inventory (Items).
 *
 * @author Shanuka
 */
public final class InventoryService {

    // Initialize the DataStore for the Item model
    private static final DataStore<Item> dataStore = new DataStore<>(DataFile.ITEMS, Item.class);

    // Private constructor to prevent instantiation
    private InventoryService() { }

    /**
     * Creates a new item and adds it to the data store.
     * The ID is calculated automatically.
     *
     * @param name The name of the item
     * @param price The price of the item
     * @throws ApplicationError if data retrieval or saving fails
     */
    public static void addItem(String name, Double price) throws ApplicationError {
        // Get all existing item IDs
        List<Integer> itemIds = getAllItems().stream()
                .map(Item::getId)
                .collect(Collectors.toList());

        // Generate the next available ID
        int newId = Helpers.generateNextAvailableId(itemIds);

        // Create the new Item object
        Item newItem = new Item(newId, name, price);

        // Add the new item to the data store
        dataStore.add(newItem);
    }

    /**
     * Removes an item from the data store based on its ID.
     *
     * @param itemId The ID of the item to be removed
     * @throws ApplicationError if the data operation fails
     * @throws NotFoundError if the item to remove is not found
     */
    public static void removeItem(Integer itemId) throws ApplicationError, NotFoundError {
        dataStore.remove("id", itemId);
    }

    /**
     * Searches for items where the name contains the given query string.
     * The search is case-insensitive.
     *
     * @param query The text to search for in the item names
     * @return A List of matching Item objects. Returns an empty list if no matches are found.
     * @throws ApplicationError if data retrieval fails
     */
    public static List<Item> searchItems(String query) throws ApplicationError {
        // Get all items
        List<Item> allItems = getAllItems();
        
        // Normalize the query to lower case for case-insensitive matching
        String lowerCaseQuery = query.toLowerCase();

        // Use a stream to filter and collect results
        return allItems.stream()
                .filter(item -> 
                    // Check if the item's name (in lower case) contains the query
                    item.getName().toLowerCase().contains(lowerCaseQuery)
                )
                .collect(Collectors.toList());
    }
    
    /**
     * Helper method to retrieve all items from the data store.
     *
     * @return A List of all Item objects
     * @throws ApplicationError if data retrieval fails
     */
    public static List<Item> getAllItems() throws ApplicationError {
        return dataStore.retrieveData();
    }
}