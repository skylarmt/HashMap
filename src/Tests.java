
import coffee.util.HashMap;
import coffee.util.MapIsFullException;

/**
 * Test the HashMap implementation
 *
 * @author Skylar Ittner
 */
public class Tests {

    public Tests() throws MapIsFullException {
        // Make sure assertions are working
        try {
            assert (1 == 2);
            System.err.println("Assertions are not enabled, exiting.");
            System.exit(1337);
        } catch (AssertionError ex) {
        }

        System.out.println("=====\nRunning tests...\n=====\n");
        HashMap map = new HashMap(11);
        assert (map.size() == 0);
        assert (map.maxSize() == 11);

        // Insert two items with same key
        map.putEntry("your mom", "is gay");
        assert (map.getValue("your mom").equals("is gay"));
        map.putEntry("your mom", "is super gay");
        assert (map.getValue("your mom").equals("is super gay"));

        // Insert some other items
        map.putEntry("my mom", "is not gay");
        map.putEntry("1", "2");
        assert (map.getValue("1").equals("2"));
        assert (map.size() == 3);

        // Put more items in
        map.putEntry("3", "4");
        map.putEntry("5", "6");

        // These keys all have the same hash code.
        map.putEntry("AaAa", "23");
        // Check that it doesn't get confused before the colliding key is inserted
        assert (map.getValue("BBBB") == null);
        map.putEntry("BBBB", "34");
        map.putEntry("AaBB", "34");
        map.putEntry("BBAa", "45");
        // Check that it's not overwriting if the hash codes are the same
        assert (map.getValue("AaAa").equals("34") == false);

        map.putEntry("7", "8");
        map.putEntry("9", "10");

        assert (map.size() == 11);
        
        // Make sure we get an error when the map is full
        try {
            map.putEntry("full", "12345");
            assert false;
        } catch (MapIsFullException ex) {
            
        }

        // Make sure removing values and requesting missing values works
        assert (map.removeValue("your mom").equals("is super gay"));
        assert (map.getValue("your mom") == null);
        assert (map.getValue("87v84twf") == null);
        
        map.putEntry("123", "456");
        assert (map.getValue("123").equals("456"));

        System.out.println("=====\nIf you see this, it means all tests have passed!\n=====\n\nFinal result:\n-----\n\n" + map);
    }

}
