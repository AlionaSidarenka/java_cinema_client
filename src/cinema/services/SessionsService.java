package cinema.services;

import cinema.Model.Session;
import cinema.connection.Request;

import java.util.List;

public class SessionsService {
    public static List<Session> getAllSessions() {
        return APIService.<Session>makeRequest(new Request("GET", "getSessions"), Session.class);
    }
}
