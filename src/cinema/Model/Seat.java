package cinema.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {
    @JsonProperty("place")
    private int place;

    @JsonProperty("row")
    private int row;

    @JsonProperty("sold")
    private boolean sold;

    @JsonProperty("reserved")
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
