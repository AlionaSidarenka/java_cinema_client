package cinema.Model;

public class Room {
    Seat[][] seats;

    public Room(int[] places) {
        seats = new Seat[places.length][];
        for (int i = 0; i < places.length; i++) {
            seats[i] = new Seat[places[i]];
            for (int j = 0; j < places[i]; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }
}
