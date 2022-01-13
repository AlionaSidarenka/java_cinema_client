package cinema.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.beans.property.SimpleStringProperty;

@JsonDeserialize(as = Room.class)
public class Room {
    @JsonProperty("seats")
    Seat[][] seats;

    @JsonProperty("name")
    private SimpleStringProperty name;

    public Room(int[] places, String name) {
        this.name = new SimpleStringProperty(name);
        seats = new Seat[places.length][];
        for (int i = 0; i < places.length; i++) {
            seats[i] = new Seat[places[i]];
            for (int j = 0; j < places[i]; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    Room(){}

    public String getName() {
        return this.name.get();
    }

    public SimpleStringProperty nameProperty() {
        return this.name;
    }
}
