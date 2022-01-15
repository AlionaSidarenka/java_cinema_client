package cinema.connection;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Response<T> implements Serializable {
    private String status;
    private String message;
    private T data;

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
