package cinema.services;

import cinema.connection.Request;
import cinema.connection.Response;
import cinema.model.Session;
import cinema.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionsService {
    public static List<Session> getAllSessions(LocalDate date) {
        Map<String, String> params = new HashMap();
        params.put("date", DateUtil.format(date));

        Response response = APIService.makeRequest(new Request("GET", "getSessions", params));
        return (List<Session>) response.getData();
    }

    public static Response updateSession(Session session, Session delete) {
        List<Session> data = new ArrayList<>();
        data.add(session);
        data.add(delete);

        Response response = APIService.<Session>makeRequest(new Request("PUT", "updateSession", data));

        return response;
    }

    public static Response updateSession(Session session) {
        List<Session> data = new ArrayList<>();
        data.add(session);
        Response response = APIService.<Session>makeRequest(new Request("PUT", "updateSession", data));

        return response;
    }

    public static Response deleteSession(Session data) {
        Response response = APIService.<Session>makeRequest(new Request("DELETE", "deleteSession", data));

        return response;
    }

    public static Response createSession(Session data) {
        Response response = APIService.<Session>makeRequest(new Request("PUT", "addSession", data));

        return response;
    }
}
