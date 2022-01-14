package cinema.Model;

import cinema.util.DateUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    public Session() {
        super();
        // todo make request for room to be set by default
        this.startDateTime = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
    }
    public ObjectProperty<LocalDateTime> startDateTimeProperty() {
        return this.startDateTime;
    }

    public void setStartDateTime(LocalDate date, Integer hours, Integer minutes) {
        this.startDateTime.set(LocalDateTime.of(date, LocalTime.of(hours, minutes)));
    }

    public void setStartDateTime(String str) {
        this.startDateTime.set(DateUtil.parse(str));
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime.get();
    }

    /*public BigDecimal getPrice() {
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
    }*/
}
