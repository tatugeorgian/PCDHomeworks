import java.io.IOException;

public class MainServer {

    public static void main(String[] args) {

        int messageSize = Integer.parseInt(args[0]);
        boolean useTcp = Boolean.parseBoolean(args[1]);
        boolean stopAndWait = Boolean.parseBoolean(args[2]);

        if (useTcp) {
            try {
                TCPServer.receive(messageSize, DataGenerator.DATA_SIZE, stopAndWait);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                UDPServer.receive(messageSize, DataGenerator.DATA_SIZE, stopAndWait);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
