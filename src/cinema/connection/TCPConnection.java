package cinema.connection;

import cinema.Model.Session;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class TCPConnection {
    BufferedReader streamReader;
    BufferedWriter streamWriter;

    public List<Session> connect() throws IOException, ClassNotFoundException {
        List<Session> values = null;
        try (Socket clientSocket = new Socket("127.0.0.1", 2525)) {
            System.out.println("connection established....");

/*            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            OutputStreamWriter coos = new OutputStreamWriter(clientSocket.getOutputStream());
            InputStreamReader cois = new InputStreamReader(clientSocket.getInputStream());*/

            streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            streamWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            streamWriter.write("get");
            streamWriter.newLine();
            streamWriter.flush();
            String str = streamReader.readLine();
            System.out.println(1 + str);
            values = objectMapper.readValue(str, new TypeReference<List<Session>>(){});
            streamWriter.close();
            streamReader.close();
        } catch ( IOException e) {
            System.out.println(e);
        }
        return values;
    }

    public void disconnect() {

    }
}
