
/**
 * Forces assertions to be enabled regardless of -ea VM flag, and runs the tests
 * in Tests
 *
 * @author http://stackoverflow.com/a/5558769/2534036
 * @author Skylar Ittner
 */
public class TestHashMap {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        loader.setDefaultAssertionStatus(true);
        Class<?> c = loader.loadClass("Tests");
        Tests t = (Tests) c.newInstance();
    }

}
