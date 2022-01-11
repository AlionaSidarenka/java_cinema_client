package cinema.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Session {
    static int sessionCount = 0;
    private Room room;
    private Movie movie;
    private int id;
    private ObjectProperty<LocalDateTime> startDateTime;

    public Session(Room room, Movie movie, LocalDateTime startDateTime) {
        sessionCount++;
        id = sessionCount;
        this.room = room;
        this.movie = movie;
        this.startDateTime = new SimpleObjectProperty<LocalDateTime>(startDateTime);
    }

    public ObjectProperty<LocalDateTime> startDateTimeProperty() {
        return this.startDateTime;
    }

    public Room getRoom() {
        return room;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime.get();
    }

    public BigDecimal getPrice() {
        BigDecimal ratio = getDateRatio(getStartDateTime());
        return movie.getPrice().multiply(ratio);
    }

    BigDecimal getDateRatio(LocalDateTime date) {
        if (date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek()== DayOfWeek.TUESDAY || date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
            return PriceRatio.LOW.getRatio();
        } else if (date.getDayOfWeek() == DayOfWeek.THURSDAY) {
            return PriceRatio.MEDIUM.getRatio();
        }

        return PriceRatio.HIGH.getRatio();
    }
}
