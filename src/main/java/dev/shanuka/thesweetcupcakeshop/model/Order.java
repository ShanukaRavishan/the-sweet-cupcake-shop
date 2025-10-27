package dev.shanuka.thesweetcupcakeshop.model;

import java.util.Date;

/**
 * Model that represents an order placed
 *
 * @author Shanuka
 */
public class Order {

    private Integer id;
    private Date date;
    private String item;
    private Double itemPrice;
    private Integer quantity;
    private Double totalAmount;

    /**
     * Default constructor
     */
    public Order() {
    }

    /**
     * Constructs a new Order with all fields.
     *
     * @param id The order ID
     * @param date The date the order was placed
     * @param item The name of the item
     * @param itemPrice The price of a single item
     * @param quantity The number of items
     * @param totalAmount The total price for this order line
     */
    public Order(Integer id, Date date, String item, Double itemPrice, Integer quantity, Double totalAmount) {
        this.id = id;
        this.date = date;
        this.item = item;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getItem() {
        return item;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}