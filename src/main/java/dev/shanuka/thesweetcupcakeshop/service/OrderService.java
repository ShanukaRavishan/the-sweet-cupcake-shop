package dev.shanuka.thesweetcupcakeshop.service;

import dev.shanuka.thesweetcupcakeshop.enums.DataFile;
import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.model.Order;
import dev.shanuka.thesweetcupcakeshop.util.DataStore;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import java.util.Date;
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
     * Creates a new order and adds it to the data store
     * The ID, Date, and TotalAmount are calculated automatically
     *
     * @param item The name of the item being ordered
     * @param itemPrice The price of a single item
     * @param quantity The number of items
     * @throws ApplicationError if data retrieval or saving fails
     */
    public static void addOrder(String item, Double itemPrice, Integer quantity) throws ApplicationError {
        // Get all existing order IDs using a stream
        List<Integer> orderIds = getAllOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        // Generate the next available ID
        int newId = Helpers.generateNextAvailableId(orderIds);

        // Set the order date to the current time
        Date orderDate = new Date();

        // Calculate the total amount
        Double totalAmount = itemPrice * quantity;

        // Create the new Order object
        Order newOrder = new Order(newId, orderDate, item, itemPrice, quantity, totalAmount);

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
     * Removes an order from the data store based on its ID
     * (Assumes DataStore has a 'remove' method)
     *
     * @param orderId The ID of the order to be removed
     * @throws ApplicationError if the data operation fails
     * @throws NotFoundError if the item to remove is not found
     */
    public static void removeOrder(Integer orderId) throws ApplicationError, NotFoundError {
        dataStore.remove("id", orderId);
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
