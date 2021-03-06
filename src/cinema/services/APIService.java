package cinema.services;

import cinema.connection.Request;
import cinema.connection.Response;
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

    public static <T> List<T> makeGetRequest(Request request, Class<T> content) {
        BufferedReader streamReader = null;
        BufferedWriter streamWriter = null;
        List<T> result = null;

        try {
            streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            streamWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String a = objectMapper.writeValueAsString(request);
            streamWriter.write(a);
            streamWriter.newLine();
            streamWriter.flush();

            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, content);
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

    public static <T> T makePutRequest(Request request, Class<T> content) {
        BufferedReader streamReader = null;
        BufferedWriter streamWriter = null;
        T result = null;

        try {
            streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            streamWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String a = objectMapper.writeValueAsString(request);
            streamWriter.write(a);
            streamWriter.newLine();
            streamWriter.flush();

            String responseStr = streamReader.readLine();
            Response<Object> response = objectMapper.readValue(responseStr, Response.class);
            result = objectMapper.readValue(objectMapper.writeValueAsString(response.getData()), content);
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
