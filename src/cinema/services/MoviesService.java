package cinema.services;

import cinema.model.Movie;
import cinema.model.Session;

import java.util.List;

public class MoviesService {
    public static List<Movie> getAllMovies(List<Session> sessions) {
        return sessions.stream().map((Session s) -> s.getMovie()).toList();
    }
}
