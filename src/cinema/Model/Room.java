package cinema.Model;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Room {
    ArrayList<ArrayList<Seat>> seats;
    private SimpleStringProperty name;

    public Room(int[] places, String name) {
        this.name = new SimpleStringProperty(name);
        seats = new ArrayList();
        for (int i = 0; i < places.length; i++) {
            seats.add(new ArrayList());
            for (int j = 0; j < places[i]; j++) {
                seats.get(i).add(new Seat(i, j));
            }
        }
    }

    Room(){}

    public String getName() {
        return this.name.get();
    }

    public ArrayList<ArrayList<Seat>> getSeats() {
        return this.seats;
    }

    public SimpleStringProperty nameProperty() {
        return this.name;
    }
}
