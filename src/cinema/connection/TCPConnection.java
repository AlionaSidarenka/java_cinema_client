package cinema.connection;

import cinema.services.APIService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class TCPConnection {
    Socket clientSocket;
    private Integer reconnectAttempts = 0;
    private final Integer MAX_RECONNECT_ATTEMPTS = 3;

    public void connect() throws IOException, ClassNotFoundException {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            try {
                clientSocket = new Socket(
                        prop.getProperty("socket.host"),
                        Integer.parseInt(prop.getProperty("socket.port")));
                APIService service = new APIService(clientSocket);
                System.out.println("connection established....");
            } catch ( IOException e) {
                e.printStackTrace();
                if (reconnectAttempts > MAX_RECONNECT_ATTEMPTS) {
                    this.disconnect();
                } else {
                    this.reconnect();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void disconnect() throws IOException {
        clientSocket.close();
    }

    public void reconnect() throws IOException, ClassNotFoundException {
        System.out.println("Reconnecting");
        this.reconnectAttempts++;
        this.connect();
    }
}
