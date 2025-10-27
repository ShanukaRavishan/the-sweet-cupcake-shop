package dev.shanuka.thesweetcupcakeshop.service;

import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.model.User;

/**
 * 
 * 
 * @author Shanuka
 */
public class AuthService {
    // Field to store currently logged user
    public static User loggedUser = null;
    
    public static boolean login(String email, String password) throws NotFoundError, ApplicationError {
        // Check if a user is found with the given email
        // Throws NotFoundError if a user with the given email cannot be found
        User user = UserService.findUser(email);
        
        // Compare the provided password against the stored one
        if(password.equals(user.getpassword())) {
            loggedUser = user;
            
            return true; // Passwords match
        }
        
        return false; // Passwords don't match
    }
    
    public static void logout() {
        loggedUser = null;
    }
}
