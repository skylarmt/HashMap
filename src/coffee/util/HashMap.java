package coffee.util; // java == coffee

/**
 * A reinvention of the HashMap wheel.
 * 
 * @author Skylar Ittner
 */
public class HashMap implements Map {

    private String[] keys; // The keys
    private int[] hashes; // The hashes of the keys
    private String[] vals; // The values

    /**
     * Create a new HashMap
     * @param maxSize Number of "slots" for elements
     */
    public HashMap(int maxSize) {
        keys = new String[maxSize];
        hashes = new int[maxSize];
        vals = new String[maxSize];
    }

    /**
     * Create a new HashMap with a maximum of 32 elements
     */
    public HashMap() {
        this(32);
    }

    /**
     * Return number of elements in map.
     *
     * @return the number of elements in this map
     */
    public int size() {
        // If you need comments in this one, go back to school
        int size = 0;
        for (Object k : keys) {
            if (k != null) {
                size++;
            }
        }
        return size;
    }
    
    /**
     * Get the maximum size of this map.
     */
    public int maxSize() {
        return keys.length;
    }

    /**
     * Adds/replaces value in map associated with key.
     *
     * @param key the key in the map
     * @param value the value associated to the key
     * @throws coffee.util.MapIsFullException if the map is full.
     */
    public void putEntry(String key, String value) throws MapIsFullException {
        int[] hashMatches = getPossibleHashMatches(key);
        int index = -1;
        switch (hashMatches.length) {
            case 0: // There are no matches, let's get an empty slot for it
                index = getFirstEmptyIndex();
                break;
            default: // We have some matches
                try {
                    // Get the actual index in case of hash collisions
                    index = getIndexFromPossibleMatches(key, hashMatches);
                } catch (ElementDoesNotExistException ex) {
                    // There aren't any real matches, so we need a new slot
                    index = getFirstEmptyIndex();
                }
        }
        
        if (index != -1) {
            // We found a suitable index for saving
            vals[index] = value;
            hashes[index] = key.hashCode();
            keys[index] = key;
        } else {
            // The arrays must be full, because there wasn't a match
            // and we couldn't find an empty slot
            throw new MapIsFullException();
        }
    }

    /**
     * Fetches value associated with key.
     *
     * @param key the key in the map
     * @return the value associated with key, or null if no key exists
     */
    public String getValue(String key) {
        // Get the possible matches
        int[] hashMatches = getPossibleHashMatches(key);
        switch (hashMatches.length) {
            case 0: // No match
                return null;
            default: { // Time to look closer
                try {
                    return vals[getIndexFromPossibleMatches(key, hashMatches)];
                } catch (ElementDoesNotExistException ex) {
                    // No match after all, just collisions
                    return null;
                }
            }
        }
    }

    /**
     * Fetches value associated with key in map, and removes mapping for key in
     * map.
     *
     * @param key the key in the map
     * @return the value associated with key, or null if no key exists
     */
    public String removeValue(String key) {
        // Get the possible matches
        int[] hashMatches = getPossibleHashMatches(key);
        String val; // The value of the item
        int index; // The index of the item
        try {
            // Get the index
            index = getIndexFromPossibleMatches(key, hashMatches);
        } catch (ElementDoesNotExistException ex) {
            // It doesn't exist, derp
            return null;
        }

        val = vals[index]; // Save the value so we can return it
        removeElementAt(index); // Nuke it out of the arrays
        return val; // Return the now-deleted value
    }

    /**
     * Get the index of the first empty element in the internal array.
     *
     * @return an unused index for storing a new value
     * @throws MapIsFullException if there are no empty "slots"
     */
    private int getFirstEmptyIndex() throws MapIsFullException {
        int index = -1;
        // Find the first empty element
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            // We couldn't find any empty slots!
            throw new MapIsFullException();
        }
        return index;
    }

    /**
     * Remove the nth element from the internal arrays, and mark the "slot"
     * unused.
     *
     * @param index the index to remove
     */
    private void removeElementAt(int index) {
        // Just nuke everything
        vals[index] = null;
        hashes[index] = 0;
        keys[index] = null;
    }

    /**
     * Get the actual index of the key
     *
     * @param key The key to search for
     * @param hashMatches An array of hash matches to look through
     * @return The index of the element
     * @throws ElementDoesNotExistException If the key doesn't exist
     */
    private int getIndexFromPossibleMatches(String key, int[] hashMatches) throws ElementDoesNotExistException {
        // Search for a key match
        for (int match : hashMatches) {
            if (keys[match].equals(key)) {
                return match;
            }
        }
        // If this function hasn't returned yet, there are no real matches
        throw new ElementDoesNotExistException();
    }

    /**
     * Get an array of the indexes that might be a match for the key. Uses the
     * hashes to compare.
     *
     * @param key
     * @return array of possible match indexes
     */
    private int[] getPossibleHashMatches(String key) {
        String matches = ""; // String of patches
        int keyHash = key.hashCode(); // Hash code of the key
        for (int i = 0; i < hashes.length; i++) {
            // If we have a match, add it to the string
            if (hashes[i] == keyHash) {
                matches += i + " ";
            }
        }

        // Trim off any trailing space
        matches = matches.trim();

        // No matches, return a zero-length array
        if (matches.equals("")) {
            return new int[0];
        }

        // Split up the matches
        String[] mStrArr = matches.split(" ");
        int[] indexes = new int[mStrArr.length]; // The possible indexes

        // Convert String[] to int[]
        for (int i = 0; i < mStrArr.length; i++) {
            indexes[i] = Integer.parseInt(mStrArr[i]);
        }

        return indexes;
    }

    /**
     * Get a String representation of the internal state of this map. Excludes
     * null (empty) items.
     *
     * @return A human-readable dump.
     */
    @Override
    public String toString() {
        String out = "";
        int length = 0;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) { // There's something here, not nothing
                length++;
                out += keys[i] + " [" + hashes[i] + "]\t=>\t" + vals[i] + "\n";
            }
        }
        return "Length: " + length + "\n" + out;
    }
}
