package cinema.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Seat implements Serializable {
    private static final long SERIAL_VERSION_UID = 7414052249517525297L;
    private int place;
    private int row;
    private boolean sold;
    private boolean reserved;

    public Seat(int place, int row) {
        this.place = place;
        this.row = row;
        sold = false;
        reserved = false;
    }

}
