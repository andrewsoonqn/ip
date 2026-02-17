package arnold.inputhandling.parsing;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import arnold.chatbotexceptions.ChatbotArgumentException;

/**
 * Utility class for parsing and formatting dates and times.
 *
 * <p>Supports natural date formats where year and time are optional:
 * <ul>
 *   <li>{@code day/month/year time} - full format (e.g., {@code 25/12/2026 1400})</li>
 *   <li>{@code day/month time} - year omitted, uses next future occurrence</li>
 *   <li>{@code day/month/year} - time omitted, defaults to 2359</li>
 *   <li>{@code day/month} - both omitted</li>
 * </ul>
 *
 * <p>Time can be 1-4 digits: 1-2 digits are treated as hours (e.g., {@code 9} = 0900),
 * 3-4 digits as HHMM (e.g., {@code 930} = 0930).
 */
public class DateTimeParser {
    private static final String DATE_TIME_PATTERN = "d/M/yyyy HHmm";
    private static final int DEFAULT_HOUR = 23;
    private static final int DEFAULT_MINUTE = 59;

    /**
     * Parses a date and time string using the strict full format {@code d/M/yyyy HHmm}.
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
     * Parses a date and time string using the strict full format, wrapping any parsing error
     * into a ChatbotArgumentException with a custom error message.
     *
     * @param dateTimeString The string to parse.
     * @param errorMessage The error message to use if parsing fails.
     * @return The parsed LocalDateTime.
     * @throws ChatbotArgumentException If the string cannot be parsed.
     */
    public static LocalDateTime parseWithErrorMessage(String dateTimeString, String errorMessage) {
        try {
            return parse(dateTimeString);
        } catch (DateTimeParseException e) {
            throw new ChatbotArgumentException(errorMessage);
        }
    }

    /**
     * Parses the time portion of the input string.
     * If time is null (omitted), defaults to 23:59.
     * Supports 1-4 digit formats: 1-2 digits are treated as hours (minutes = 0),
     * 3-4 digits are treated as HHMM.
     *
     * @param timePart The time string, or null if time was omitted.
     * @param originalInput The original full input string (for error reporting).
     * @return The parsed LocalTime.
     * @throws DateTimeParseException If the time cannot be parsed.
     */
    private static LocalTime parseTime(String timePart, String originalInput) {
        if (timePart == null || timePart.isEmpty()) {
            return LocalTime.of(DEFAULT_HOUR, DEFAULT_MINUTE);
        }

        int hour;
        int minute;

        try {
            if (timePart.length() <= 2) {
                // 1-2 digits: treat as hours only
                hour = Integer.parseInt(timePart);
                minute = 0;
            } else if (timePart.length() == 3) {
                // 3 digits: first digit is hour, last 2 are minutes (e.g., 930 -> 09:30)
                hour = Integer.parseInt(timePart.substring(0, 1));
                minute = Integer.parseInt(timePart.substring(1));
            } else if (timePart.length() == 4) {
                // 4 digits: HHMM (e.g., 1400 -> 14:00)
                hour = Integer.parseInt(timePart.substring(0, 2));
                minute = Integer.parseInt(timePart.substring(2));
            } else {
                throw new DateTimeParseException("Time must be 1-4 digits", originalInput, 0);
            }
        } catch (NumberFormatException e) {
            throw new DateTimeParseException("Invalid time value", originalInput, 0);
        }

        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new DateTimeParseException("Time out of range (hour: 0-23, minute: 0-59)",
                    originalInput, 0);
        }

        return LocalTime.of(hour, minute);
    }

    /**
     * Formats a LocalDateTime as a string.
     * Formats a LocalDateTime as a string using the full format {@code d/M/yyyy HHmm}.
     *
     * @param dateTime The LocalDateTime to format.
     * @return The formatted date and time string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}
