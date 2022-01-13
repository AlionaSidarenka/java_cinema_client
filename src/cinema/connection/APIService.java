package cinema.connection;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class APIService {
    private static Socket clientSocket;

    public APIService(Socket clientSocket) {
        APIService.clientSocket = clientSocket;
    }

    public static <T> List<T> makeRequest(String msg, Class<T> content) {
        BufferedReader streamReader = null;
        BufferedWriter streamWriter = null;
        List<T> result = null;

        try {
            streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            streamWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, content);

            streamWriter.write(msg);
            streamWriter.newLine();
            streamWriter.flush();

            String str = streamReader.readLine();
            result = objectMapper.readValue(str, type);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                streamReader.close();
                streamWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return result;
    }
}
