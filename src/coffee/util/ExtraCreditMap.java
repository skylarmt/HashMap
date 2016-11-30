package coffee.util;

public interface ExtraCreditMap {

    /**
     * Return number of elements in map.
     *
     * @return the number of elements in this map
     */
    int size();

    /**
     * Adds/replaces value in map associated with key.
     *
     * @param key the key in the map
     * @param value the value associated to the key
     */
    void putEntry(String key, String value);

    /**
     * Fetches value associated with key.
     *
     * @param key the key in the map
     * @return the value associaed with key, or null if no key exists
     */
    String getValue(String key);

    /**
     * Fetches value associated with key in map, and removes mapping for key in
     * map.
     *
     * @param key the key in the map
     * @return the value associaed with key, or null if no key exists
     */
    String removeValue(String key);
}
