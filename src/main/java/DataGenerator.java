import java.util.Random;

public class DataGenerator {

    public static final int DATA_SIZE = 500 * 1_000_000;

    public static byte[] generateData(int size) {
        byte[] data = new byte[size];
        new Random().nextBytes(data);
        return data;
    }
}
