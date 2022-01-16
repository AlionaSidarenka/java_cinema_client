package cinema.model;

import cinema.util.DateUtil;
import cinema.util.LocalDateTimeAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Externalizable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Session implements Externalizable {
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
        this.startDateTime = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
    }
    public ObjectProperty<LocalDateTime> startDateTimeProperty() {
        return this.startDateTime;
    }

    public void setStartDateTime(LocalDate date, Integer hours, Integer minutes) {
        this.startDateTime.set(LocalDateTime.of(date, LocalTime.of(hours, minutes, 0)));
    }

    public void setStartDateTime(LocalDateTime date) {
        this.startDateTime.set(date);
    }

    public void setStartDateTime(String str) {
        this.startDateTime.set(DateUtil.parse(str));
    }

    @XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
    public LocalDateTime getStartDateTime() {
        return this.startDateTime.get();
    }

    @Override
    public void writeExternal(java.io.ObjectOutput out) throws java.io.IOException {
        out.writeObject(getStartDateTime());
        out.writeObject(getMovie());
        out.writeObject(getRoom());
    }

    @Override
    public void readExternal(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException {
        setStartDateTime((LocalDateTime) in.readObject());
        setMovie((Movie) in.readObject());
        setRoom((Room) in.readObject());
    }
}
