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
     * Formats a LocalDateTime as a string.
     *
     * @param dateTime The LocalDateTime to format.
     * @return The formatted date and time string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}
