package cinema.connection;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Request<T> implements Serializable {
    private String method;
    private String url;
    private Map<String, String> params = new HashMap<String, String>();
    private T data;

    public Request(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public Request(String method, String url, Map<String, String> params) {
        this.method = method;
        this.url = url;
        this.params = params;
    }

    public Request(String method, String url, T data) {
        this.method = method;
        this.url = url;
        this.data = data;
    }
}
