package cinema.connection;

public class Response<T> {
    private String status;
    private String message;
    private T data;

    public Response(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private Response() {}

    public T getData() {
        return data;
    }
}
