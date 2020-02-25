import java.io.IOException;

public class MainClient {

    public static void main(String[] args) {

        int messageSize = Integer.parseInt(args[0]);
        boolean useTcp = Boolean.parseBoolean(args[1]);
        boolean stopAndWait = Boolean.parseBoolean(args[2]);

        byte[] data = DataGenerator.generateData(DataGenerator.DATA_SIZE);

        if (useTcp) {
            try {
                TCPClient.send(data, stopAndWait, messageSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                UDPClient.send(data, stopAndWait, messageSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
