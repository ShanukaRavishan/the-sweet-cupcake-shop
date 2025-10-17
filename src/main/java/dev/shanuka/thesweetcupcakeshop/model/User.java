package dev.shanuka.thesweetcupcakeshop.model;

/**
 * Model that represents a user within the system.
 * 
 * @author Shanuka
 */
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String salt;

    public User() { }

    public User(Integer id, String firstName, String lastName, String email, String passwordHash, String salt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }
}
