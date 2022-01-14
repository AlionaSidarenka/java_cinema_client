package cinema.services;

import cinema.connection.Request;
import cinema.connection.Response;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class APIService {
    private static Socket clientSocket;

    public APIService(Socket clientSocket) {
        APIService.clientSocket = clientSocket;
    }

    public static Response makeGetRequest(Request request) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Response response = null;

        try {
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();

            Marshaller marshaller = JAXBContext.newInstance(Request.class).createMarshaller();
            marshaller.marshal(request, outputStream); // sends request

            clientSocket.shutdownOutput();

            JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            response = (Response) unmarshaller.unmarshal(inputStream);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
