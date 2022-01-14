package cinema.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String format(LocalDateTime date) {
        if (date == null) return null;

        return DATE_TIME_FORMATTER.format(date);
    }

    public static String format(LocalDate date) {
        if (date == null) return null;

        return DATE_FORMATTER.format(date);
    }

    public static LocalDateTime parse(String dateString) {
        try {
            return DATE_TIME_FORMATTER.parse(dateString, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
}