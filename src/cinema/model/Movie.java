package cinema.model;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Calendar;

@XmlRootElement
public class Movie implements java.io.Externalizable {
    private static int movieCount = 0;
    private SimpleStringProperty title;
    @Getter
    @Setter
    private int ageRestriction;
    @Getter
    @Setter
    private String[] countries;
    @Getter
    @Setter
    private String director;
    @Getter
    private int id;
    @Getter
    @Setter
    private Calendar length;
    @Getter
    @Setter
    private BigDecimal price;

    public Movie(String title, int ageRestriction, String director, Calendar length, BigDecimal price, String ... countries) {
        this.id = movieCount++;
        this.title = new SimpleStringProperty(title);
        this.ageRestriction = ageRestriction;
        this.countries = countries;
        this.director = director;
        this.length = length;
        this.price = price;
    }

    public Movie(String title, int ageRestriction, String director, Calendar length, double price, String ... countries) {
        this.id = movieCount++;
        this.title = new SimpleStringProperty(title);
        this.ageRestriction = ageRestriction;
        this.countries = countries;
        this.director = director;
        this.length = length;
        this.price = BigDecimal.valueOf(price);
    }

    public Movie(){
        this.title = new SimpleStringProperty("");
    }

    public SimpleStringProperty titleProprety() {
        return title;
    }


    @Override
    public String toString () {
        return title.toString();
    }

    @Override
    public void writeExternal(java.io.ObjectOutput out) throws java.io.IOException {
        out.writeObject(getTitle());
        out.writeInt(getAgeRestriction());
        out.writeUTF(getDirector());
        out.writeObject(getLength());
        out.writeObject(getPrice());
        out.writeObject(getCountries());
    }

    @Override
    public void readExternal(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException {
        setTitle((String)in.readObject());
        setAgeRestriction((Integer) in.readInt());
        setDirector((String) in.readUTF());
        setLength((Calendar) in.readObject());
        setPrice((BigDecimal) in.readObject());
        setCountries((String[]) in.readObject());
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}
