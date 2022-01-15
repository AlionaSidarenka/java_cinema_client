package cinema.services;

import cinema.connection.Request;
import cinema.connection.Response;

import java.io.*;
import java.net.Socket;

public class APIService {
    private static Socket clientSocket;

    public APIService(Socket clientSocket) {
        APIService.clientSocket = clientSocket;
    }

    public static Response makeGetRequest(Request request) {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Response response = null;

        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            objectOutputStream.writeObject(request);
            objectOutputStream.flush();


            response = (Response) objectInputStream.readObject();
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
