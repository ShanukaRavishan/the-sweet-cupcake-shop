package dev.shanuka.thesweetcupcakeshop.service;

import dev.shanuka.thesweetcupcakeshop.enums.DataFile;
import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.DuplicateEntryError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.model.Category;
import dev.shanuka.thesweetcupcakeshop.model.Item;
import dev.shanuka.thesweetcupcakeshop.util.DataStore;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A static service class for managing all business logic related to the
 * Inventory (Items and Categories).
 *
 * @author Shanuka
 */
public final class InventoryService {

    // Initialize datastores for Item and Category models
    private static final DataStore<Item> productsStore = new DataStore<>(DataFile.ITEMS, Item.class);
    private static final DataStore<Category> categoriesStore = new DataStore<>(DataFile.CATEGORIES, Category.class);

    // Private constructor to prevent instantiation
    private InventoryService() {
    }

    /**
     * Creates a new item and adds it to the data store The ID is calculated
     * automatically
     *
     * @param name Item name
     * @param category Item category
     * @param price Item price
     * @throws ApplicationError if data retrieval or saving fails
     */
    public static void addItem(String name, String category, Double price) throws ApplicationError {
        // Get all existing item IDs
        List<Integer> itemIds = getAllItems().stream()
                .map(Item::getId)
                .collect(Collectors.toList());

        // Generate the next available ID
        int newId = Helpers.generateNextAvailableId(itemIds);

        // Create the new Item object
        Item newItem = new Item(newId, name, category, price);

        // Add the new item to the data store
        productsStore.add(newItem);
    }

    /**
     * Removes an item from the data store based on its ID.
     *
     * @param itemId The ID of the item to be removed
     * @return The deleted item's name
     * @throws ApplicationError if the data operation fails
     * @throws NotFoundError if the item to remove is not found
     */
    public static String removeItem(Integer itemId) throws ApplicationError, NotFoundError {
        // Retrieve the list of items from the products store
        List<Item> items = productsStore.retrieveData();

        // Remove the matching item
        for (Item item : items) {
            if (item.getId() == itemId) {
                // Remove the matching item from the list
                items.remove(item);

                // Save updated item list
                productsStore.saveData(items);

                return item.getName();
            }
        }

        throw new NotFoundError("No item matches the given item id");
    }

    /**
     * Helper method to retrieve all items from the data store.
     *
     * @return A List of all Item objects
     * @throws ApplicationError if data retrieval fails
     */
    public static List<Item> getAllItems() throws ApplicationError {
        return productsStore.retrieveData();
    }

    /**
     * Adds a new category to the data store.
     *
     * @param categoryName New category name
     * @throws ApplicationError if data saving fails
     * @throws DuplicateEntryError if a category with the same name already
     * exists
     */
    public static void addCategory(String categoryName) throws ApplicationError, DuplicateEntryError {
        // Get all existing category IDs
        List<Integer> categoryIds = getAllCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        // Generate the next available ID
        int newId = Helpers.generateNextAvailableId(categoryIds);
        
        // Construct the new category object
        Category newCategory = new Category(newId, categoryName);
        
        // Throw an error if a category with the given name already exists
        for (Category category : getAllCategories()) {
            if (category.getName().equalsIgnoreCase(newCategory.getName())) {
                throw new DuplicateEntryError("The category entered already exists");
            }
        }

        // Add the new category to the data store
        categoriesStore.add(newCategory);
    }

    /**
     * Removes a category from the data store based on its ID.
     *
     * @param categoryId The ID of the category to be removed
     * @return The deleted category's name
     * @throws ApplicationError if the data operation fails
     * @throws NotFoundError if the category to remove is not found
     */
    public static String removeCategory(Integer categoryId) throws ApplicationError, NotFoundError {
        // Retrieve the list of matching categories from the categories store
        List<Category> categories = categoriesStore.retrieveData();

        // Remove the matching category
        for (Category category : categories) {
            System.out.println("Checking category id: " + category.getId());
            
            if (category.getId() == categoryId) {
                // Remove the matching from the retrieved categories list
                categories.remove(category);

                // Save updated categories list
                categoriesStore.saveData(categories);

                return category.getName();
            }
        }

        throw new NotFoundError("No category matches the given category id");
    }

    /**
     * Helper method to retrieve all available categories from the data store.
     *
     * @return A List of all Category objects
     * @throws ApplicationError if data retrieval fails
     */
    public static List<Category> getAllCategories() throws ApplicationError {
        return categoriesStore.retrieveData();
    }
}
