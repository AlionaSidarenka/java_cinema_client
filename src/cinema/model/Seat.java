package cinema.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seat implements java.io.Externalizable {
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

    public Seat(){}

    @Override
    public void writeExternal(java.io.ObjectOutput out) throws java.io.IOException {
        out.writeInt(getPlace());
        out.writeInt(getRow());
        out.writeBoolean(isSold());
        out.writeBoolean(isReserved());
    }

    @Override
    public void readExternal(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException {
        setPlace((Integer) in.readInt());
        setRow((Integer)in.readInt());
        setSold((Boolean)in.readBoolean());
        setReserved((Boolean)in.readBoolean());
    }
}
