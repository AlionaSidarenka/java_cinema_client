package cinema.connection;

import cinema.Model.Session;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class TCPConnection {
    Socket clientSocket;
    private Integer reconnectAttempts = 0;
    private final Integer MAX_RECONNECT_ATTEMPTS = 3;

    public void connect() throws IOException, ClassNotFoundException {
        List<Session> values = null;

        try {
            clientSocket = new Socket("127.0.0.1", 2525);
            APIService service = new APIService(clientSocket);
            System.out.println("connection established....");
        } catch ( IOException e) {
            System.out.println(e);

            if (reconnectAttempts > MAX_RECONNECT_ATTEMPTS) {
                this.disconnect();
            } else {
                this.reconnect();
            }
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
