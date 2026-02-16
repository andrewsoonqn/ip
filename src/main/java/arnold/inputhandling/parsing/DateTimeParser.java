package arnold.inputhandling.parsing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing and formatting dates and times.
 */
public class DateTimeParser {
    private static final String DATE_TIME_PATTERN = "d/M/yyyy HHmm";

    /**
     * Parses a date and time string.
     *
     * @param dateTimeString The string to parse.
     * @return The parsed LocalDateTime.
     * @throws DateTimeParseException If the string cannot be parsed.
     */
    public static LocalDateTime parse(String dateTimeString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        return LocalDateTime.parse(dateTimeString, formatter);
    }


    /**
     * Formats a LocalDateTime as a string.
     *
     * @param dateTime The LocalDateTime to format.
     * @return The formatted date and time string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}
