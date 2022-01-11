package cinema.Model;

public class Seat {
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
