package cinema.connection;

import java.io.Serializable;

public class Request<T> implements Serializable {
    private String method;
    private String url;
    private T data;

    public Request(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public Request(String method, String url, T data) {
        this.method = method;
        this.url = url;
        this.data = data;
    }

    public String getMethod() {
        return this.method;
    }

    public String getUrl() {
        return this.url;
    }

    public T getData() {
        return this.data;
    }
}
