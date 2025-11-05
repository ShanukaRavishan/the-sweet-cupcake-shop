package dev.shanuka.thesweetcupcakeshop.service;

import dev.shanuka.thesweetcupcakeshop.enums.DataFile;
import dev.shanuka.thesweetcupcakeshop.enums.UserRole;
import dev.shanuka.thesweetcupcakeshop.exception.ApplicationError;
import dev.shanuka.thesweetcupcakeshop.exception.NotFoundError;
import dev.shanuka.thesweetcupcakeshop.model.User;
import dev.shanuka.thesweetcupcakeshop.util.DataStore;
import dev.shanuka.thesweetcupcakeshop.util.Helpers;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author Shanuka
 */
public class UserService {

    private static DataStore<User> dataStore = new DataStore<User>(DataFile.USERS, User.class);

    // User id will be generated automatically
    public static void addUser(UserRole role, String firstName, String lastName, String email, String password) throws ApplicationError {
        List<Integer> userIds = getAllUsers().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        for (User user : getAllUsers()) {
            userIds.add(user.getId());
        }

        User newUser = new User(Helpers.generateNextAvailableId(userIds), role, firstName, lastName, email, password);

        dataStore.add(newUser);
    }

    public static User findUser(String email) throws NotFoundError, ApplicationError {
        List<User> users = getAllUsers();

        for (User user : users) {
            // If a matching user is found
            if (user.getemail().matches(email)) {
                return user;
            }
        }

        // If no user is found, throw a NotFoundError
        throw new NotFoundError(String.format("A user with %s as their email cannot be found.", email));
    }

    /**
     * Removes a user from the data store based on their ID.
     *
     * @param userId The ID of the user to be removed
     * @return The deleted user's full name
     * @throws ApplicationError if the data operation fails
     * @throws NotFoundError if the user to remove is not found
     */
    public static String removeUser(Integer userId) throws ApplicationError, NotFoundError {
        // Retrieve the list of users from the data store
        List<User> users = dataStore.retrieveData();

        // Remove the matching user
        for (User user : users) {
            if (user.getId() == userId) {
                // Remove the matching user from the list
                users.remove(user);

                // Save updated user list
                dataStore.saveData(users);

                return user.getFirstName() + " " + user.getLastName();
            }
        }

        throw new NotFoundError("No user matches the given user id");
    }

    private static List<User> getAllUsers() throws ApplicationError {
        return dataStore.retrieveData();
    }
}
