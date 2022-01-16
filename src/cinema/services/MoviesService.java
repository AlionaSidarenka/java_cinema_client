package cinema.services;

import cinema.connection.Request;
import cinema.connection.Response;
import cinema.model.Movie;
import cinema.model.Session;
import java.util.List;


public class MoviesService {
    public static List<Movie> getAllMovies() {

        Response response = APIService.makeRequest(new Request("GET", "getMovies", null));
        return (List<Movie>) response.getData();
    }
}
