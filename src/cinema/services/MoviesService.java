package cinema.services;

import cinema.Model.Movie;
import cinema.connection.Request;
import cinema.connection.Response;

import java.util.List;

public class MoviesService {
    public static List<Movie> getAllMovies() {
        Response response = APIService.makeGetRequest(new Request("GET", "getMovies"));
        return (List<Movie>) response.getData();
    }
}
