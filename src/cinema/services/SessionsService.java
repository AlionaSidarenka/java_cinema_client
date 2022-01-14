package cinema.services;

import cinema.Model.Session;
import cinema.connection.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionsService {
    public static List<Session> getAllSessions() {
        return APIService.<Session>makeGetRequest(new Request("GET", "getSessions"), Session.class);
    }

    public static Session updateSession(Integer id, Session data) {
        Map<String, Integer> params = new HashMap();
        params.put("id", id);
        return APIService.<Session>makePutRequest(new Request("PUT", "updateSession", params, data), Session.class);
    }
}
