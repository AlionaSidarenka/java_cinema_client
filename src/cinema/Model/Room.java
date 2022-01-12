package cinema.Model;

import javafx.beans.property.SimpleStringProperty;

public class Room {
    Seat[][] seats;
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

    public String getName() {
        return this.name.get();
    }

    public SimpleStringProperty nameProperty() {
        return this.name;
    }
}
