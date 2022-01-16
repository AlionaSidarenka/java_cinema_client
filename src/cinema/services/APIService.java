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
}
