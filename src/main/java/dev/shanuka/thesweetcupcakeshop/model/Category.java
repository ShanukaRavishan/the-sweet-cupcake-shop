package dev.shanuka.thesweetcupcakeshop.model;

/**
 * Model that represents a product category.
 *
 * @author Shanuka
 */
public class Category {

    private Integer id;
    private String name;

    /**
     * Default constructor.
     * Required for reflection-based instantiation by the DataStore.
     */
    public Category() {
    }

    /**
     * Constructs a new Category with all fields.
     *
     * @param id The unique ID for the category
     * @param name The name of the category (e.g. "Cupcakes", "Brownies")
     */
    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the category ID.
     *
     * @return The category ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the category name.
     *
     * @return The category name.
     */
    public String getName() {
        return name;
    }
}
