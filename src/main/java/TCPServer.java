import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPServer {

    private static int SERVER_PORT = 6789;

    public static void receive(int messageSize, int totalSize, boolean stopAndWait) throws IOException {
        ServerSocket listeningSocket = new ServerSocket(SERVER_PORT);

        while(true) {
            Socket connectionSocket = listeningSocket.accept();
            DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            int readBytes = 0;
            int numberOfMessages = 0;

            while(readBytes < totalSize) {
                byte[] message = new byte[messageSize];
                int readBytesPerMessage = inFromClient.read(message);

                if(readBytesPerMessage == -1) {
                    break;
                }
                readBytes += readBytesPerMessage;
                numberOfMessages++;

                if(stopAndWait) {
                    outToClient.write(0);
                }

            }

            StatisticsPrinter.printServer("TCP", stopAndWait, numberOfMessages, readBytes, messageSize);
        }
    }
}
