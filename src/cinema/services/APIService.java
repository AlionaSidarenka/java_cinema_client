package cinema.services;

import cinema.connection.Request;
import cinema.connection.Response;

import java.io.*;
import java.net.Socket;

public class APIService {
    private static Socket clientSocket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    public APIService(Socket clientSocket) throws IOException {
        APIService.clientSocket = clientSocket;
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());;
    }

    public static Response makeRequest(Request request) {
        Response response = null;

        try {
            objectOutputStream.writeObject(request);
            response = (Response) objectInputStream.readObject();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                objectInputStream.close();
                objectOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

//    public static <T> T makePutRequest(Request request, Class<T> content) {
//        BufferedReader streamReader = null;
//        BufferedWriter streamWriter = null;
//        T result = null;
//
//        try {
//            streamReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            streamWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//
//            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//            String a = objectMapper.writeValueAsString(request);
//            streamWriter.write(a);
//            streamWriter.newLine();
//            streamWriter.flush();
//
//            String responseStr = streamReader.readLine();
//            Response<Object> response = objectMapper.readValue(responseStr, Response.class);
//            result = objectMapper.readValue(objectMapper.writeValueAsString(response.getData()), content);
//        } catch (IOException e) {
//            e.printStackTrace();
//            try {
//                streamReader.close();
//                streamWriter.close();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        }
//
//        return result;
//    }
}
