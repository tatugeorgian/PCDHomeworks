import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class UDPServer {

    private static int SERVER_PORT = 6788;

    public static void receive(int messageSize, int totalSize, boolean stopAndWait) throws IOException {

        DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT);
        serverSocket.setSoTimeout(15 * 1000);

        byte[] receiveData = new byte[messageSize];

        while (true) {

            int readBytes = 0;
            int numberOfMessages = 0;

            while (readBytes < totalSize) {
                numberOfMessages++;
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    serverSocket.receive(receivePacket);
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket timeout");
                    StatisticsPrinter.printServer("UDP", stopAndWait, numberOfMessages, readBytes, messageSize);
                    return;
                }
                readBytes += receivePacket.getLength();

                if (stopAndWait) {
                    byte[] sendData = new byte[1];
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
                            receivePacket.getPort());
                    serverSocket.send(sendPacket);
                }
            }

            StatisticsPrinter.printServer("UDP", stopAndWait, numberOfMessages, readBytes, messageSize);
        }
    }
}
