package dev.shanuka.thesweetcupcakeshop.model;

import dev.shanuka.thesweetcupcakeshop.enums.UserRole;

/**
 * Model that represents a user within the system
 *
 * @author Shanuka
 */
public class User {
    private Integer id;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User() {
    }

    public User(Integer id, UserRole role, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getemail() {
        return email;
    }
    
    public String getpassword() {
        return password;
    }
}
