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

    Seat(){}

    public int getPlace() {
        return this.place;
    }

    public int getRow() {
        return this.row;
    }

    public boolean getSold() {
        return this.sold;
    }

    public boolean getReserved() {
        return this.reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
