package cinema.model;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Movie implements Serializable {
    private static final long SERIAL_VERSION_UID = 6529685098267757690L;
    private static int movieCount = 0;
    @Setter
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

    Movie(){
        this.title = new SimpleStringProperty("");
    }

    public SimpleStringProperty titleProprety() {
        return title;
    }


    @Override
    public String toString () {
        return title.toString();
    }

    public String getTitle() {
        return title.get();
    }
}
