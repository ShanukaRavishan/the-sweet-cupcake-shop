package dev.shanuka.thesweetcupcakeshop.service;

import dev.shanuka.thesweetcupcakeshop.enums.DataFile;
import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.model.Order;
import dev.shanuka.thesweetcupcakeshop.util.DataStore;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A static service class for managing all business logic related to Orders
 *
 * @author Shanuka
 */
public final class OrderService {

    // Initialize the DataStore for the Order model
    private static final DataStore<Order> dataStore = new DataStore<>(DataFile.ORDERS, Order.class);

    private OrderService() { }

    /**
     * Adds a new order to the data store
     *
     * @param newOrder New order data
     * @throws ApplicationError if data saving fails
     */
    public static void saveOrderData(Order newOrder) throws ApplicationError {
        // Get all existing order IDs using a stream
        List<Integer> orderIds = getAllOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        // Generate the next available ID
        int newId = Helpers.generateNextAvailableId(orderIds);
        
        // Add the new order to the data store
        dataStore.add(newOrder);
    }

    /**
     * Finds a specific order by its unique ID
     *
     * @param id The ID of the order to find
     * @return The found Order object
     * @throws NotFoundError if no order with the specified ID is found
     * @throws ApplicationError if data retrieval fails
     */
    public static Order findOrderById(Integer id) throws NotFoundError, ApplicationError {
        // Use a stream to find the first order that matches the ID
        return getAllOrders().stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundError(String.format("An order with ID %d cannot be found.", id)));
    }

    /**
     * Helper method to retrieve all orders from the data store
     *
     * @return A List of all Order objects
     * @throws ApplicationError if data retrieval fails
     */
    public static List<Order> getAllOrders() throws ApplicationError {
        return dataStore.retrieveData();
    }
}
