package cinema.services;

import cinema.Model.Movie;
import cinema.connection.Request;

import java.util.List;

public class MoviesService {
    public static List<Movie> getAllMovies() {
        return APIService.<Movie>makeRequest(new Request("GET", "getMovies"), Movie.class);
    }
}
