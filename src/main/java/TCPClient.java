import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class TCPClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 6789;

    public static void send(byte[] data, boolean stopAndWait, int messageSize) throws  IOException {

        Socket clientSocket = new Socket(SERVER_HOST, SERVER_PORT);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        int sentBytes = 0;

        long startTime = System.nanoTime();
        int numberOfMessages = 0;
        while(sentBytes < data.length) {
            numberOfMessages++;
            int higherIndex = Integer.min(sentBytes + messageSize, data.length);
            outToServer.write(Arrays.copyOfRange(data, sentBytes, higherIndex));
            if(stopAndWait) {
                byte response = inFromServer.readByte();
                if(response != 0) {
                    throw new RuntimeException("Wrong response from server " + response);
                }
            }
            sentBytes = higherIndex;
        }

        long transmissionTime = System.nanoTime() - startTime;
        clientSocket.close();

        StatisticsPrinter.printClient("TCP", stopAndWait, numberOfMessages, sentBytes, transmissionTime, messageSize);
    }
}
