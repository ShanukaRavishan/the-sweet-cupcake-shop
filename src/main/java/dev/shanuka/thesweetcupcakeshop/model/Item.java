package dev.shanuka.thesweetcupcakeshop.model;

/**
 * Model that represents an item in the shop's catalog
 *
 * @author Shanuka
 */
public class Item {
    private Integer id;
    private String name;
    private String category;
    private Double price;

    /**
     * Default constructor
     */
    public Item() {
    }

    /**
     * Constructs a new Item with all fields.
     *
     * @param id The item's unique ID
     * @param name The name of the item
     * @param price The price of the item
     */
    public Item(Integer id, String name, String category, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }
}