package dev.shanuka.thesweetcupcakeshop.util;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * A utility class providing common static helper functions for the application
 *
 * @author Shanuka
 */
public final class Helpers {
    private Helpers() { }

    /**
     * Generates the first available integer ID from a collection of used IDs
     * 
     * If used IDs are [1, 2, 4], returns 3
     * If IDs are continuous (e.g. [1, 2, 3]), returns the next id (4)
     * If no IDs are provided or the collection is null, returns 1
     * If IDs are [2, 3, 4], returns 1
     *
     * @param usedIds A Collection of integer IDs that are already in use
     * @return The first available (smallest) positive integer ID
     */
    public static int generateNextAvailableId(Collection<Integer> usedIds) {
        // If the provided collection is null or empty, the first ID is 1
        if (usedIds == null || usedIds.isEmpty()) {
            return 1;
        }

        // Add all unique, positive IDs into a sorted set
        Set<Integer> sortedIds = new TreeSet<>();
        for (Integer id : usedIds) {
            if (id != null && id > 0) {
                sortedIds.add(id);
            }
        }

        int expectedId = 1;

        // Find the first missing id by iterating through the sorted set
        for (Integer id : sortedIds) {
            if (id > expectedId) {
                
                // A gap was found. The current expectedId is the first available.
                break;
            }

            if (id.equals(expectedId)) {
                // This ID exists, so increment to check for the next one
                expectedId++;
            }
        }

        // If no gap was found, expectedId will be max id + 1
        return expectedId;
    }
}
