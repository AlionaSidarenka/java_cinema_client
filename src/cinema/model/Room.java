package cinema.model;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Room implements Externalizable {
    @Getter
    @Setter
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

    public Room() {
        this.name = new SimpleStringProperty("");
    }

    public SimpleStringProperty nameProperty() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return this.name.get();
    }

/*    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeObject(getSeats());
    }*/

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String)in.readObject());
        int row = (int) in.readInt();
        Seat[][] seats = new Seat[row][];
        for (int i = 0; i < row; i++) {
            int col = (int) in.readInt();
            seats[i] = new Seat[col];
            for (int j = 0; j < col; j++) {
                seats[i][j] = (Seat) in.readObject();
            }
        }
        setSeats((Seat[][])seats);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        // out.writeObject(getSeats());

        Seat[][] row1 = getSeats();
        out.writeInt(row1.length);
        for (Seat[] row : row1) {
            out.writeInt(row.length);
            for (int j = 0; j < row.length; j++) {
                out.writeObject(row[j]);
            }
        }
    }
/*    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.write(name().longValue());
        s.writeUTF(aStringProperty().getValueSafe()); // can't be null so use getValueSafe that returns empty string if it's null
        s.writeUTF(anOtherStringPoperty().getValueSafe());
    }*/
}
