import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class UDPClient {

    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 6788;

    public static void send(byte[] data, boolean stopAndWait, int messageSize) throws IOException {

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(SERVER_HOST);

        int sentBytes = 0;
        long startTime = System.nanoTime();
        int numberOfMessages = 0;

        while(sentBytes < data.length) {
            numberOfMessages++;
            int higherIndex = Integer.min(sentBytes + messageSize, data.length);
            byte[] messageContent = Arrays.copyOfRange(data, sentBytes, higherIndex);

            DatagramPacket message = new DatagramPacket(messageContent, messageContent.length, IPAddress, SERVER_PORT);
            clientSocket.send(message);

            if(stopAndWait) {
                byte[] responseContent = new byte[1];
                DatagramPacket response = new DatagramPacket(responseContent, responseContent.length);
                if(response.getData()[0] != 0) {
                    throw new RuntimeException("Wrong response from server");
                }
            }
            sentBytes = higherIndex;
        }

        long transmissionTime = System.nanoTime() - startTime;
        clientSocket.close();

        StatisticsPrinter.printClient("UDP", stopAndWait, numberOfMessages, sentBytes, transmissionTime, messageSize);
    }
}
