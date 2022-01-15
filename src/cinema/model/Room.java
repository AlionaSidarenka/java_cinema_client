package cinema.model;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;

public class Room implements Externalizable {
    @Getter
    @Setter
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String)in.readObject());
    }

/*    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String)in.readObject());
    }*/
/*    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.write(name().longValue());
        s.writeUTF(aStringProperty().getValueSafe()); // can't be null so use getValueSafe that returns empty string if it's null
        s.writeUTF(anOtherStringPoperty().getValueSafe());
    }*/
}
