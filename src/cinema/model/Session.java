package cinema.model;

import cinema.util.DateUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Session implements Serializable {
    private static final long SERIAL_VERSION_UID = 7966972753466200798L;
    private static int sessionCount = 0;
    @Getter
    @Setter
    private Room room;
    @Getter
    @Setter
    private Movie movie;
    @Getter
    private int id;
    private ObjectProperty<LocalDateTime> startDateTime;

    public Session(Room room, Movie movie, LocalDateTime startDateTime) {
        id = sessionCount++;
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

    public LocalDateTime getStartDateTime() {
        return this.startDateTime.get();
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
