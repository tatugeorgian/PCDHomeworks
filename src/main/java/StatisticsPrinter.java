import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StatisticsPrinter {

    public static void printClient(String protocol, boolean stopAndWait, int numberOfMessages, int numberOfBytes, long transmissionTime, int messageSize) {

        StringBuilder sb = new StringBuilder();
        addCommonInformation(protocol, stopAndWait, sb);

        sb.append("Message size : " + messageSize + "\n");
        sb.append("Number of sent messages : " + numberOfMessages + "\n");
        sb.append("Number of sent bytes : " + numberOfBytes + "\n");
        sb.append("Transmission time : " + transmissionTime + " nanoseconds" + "\n");
        sb.append("End of log\n\n\n");

        print(sb.toString(), false);
    }

    private static void addCommonInformation(String protocol, boolean stopAndWait, StringBuilder sb) {
        sb.append("Used protocol : " + protocol + "\n");
        if (stopAndWait) {
            sb.append("Used mechanism : stop and wait" + "\n");
        } else {
            sb.append("Used mechanism : streaming" + "\n");
        }
    }

    private static void print(String str, boolean server) {
        String filename = server ? "server_stats.txt" : "client_stats.txt";
        Path path = Paths.get(System.getProperty("user.dir"),filename);
        try {
            Files.write(path, str.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printServer(String protocol, boolean stopAndWait, int numberOfMessages, int numberOfBytes, int messageSize) {

        StringBuilder sb = new StringBuilder();
        addCommonInformation(protocol, stopAndWait, sb);

        sb.append("Message size : " + messageSize + "\n");
        sb.append("Number of received messages : " + numberOfMessages + "\n");
        sb.append("Number of received bytes : " + numberOfBytes + "\n");
        sb.append("End of log\n\n\n");
        print(sb.toString(), true);
    }
}
