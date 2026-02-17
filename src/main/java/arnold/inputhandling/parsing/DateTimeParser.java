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

    // Public API

    /**
     * Parses a date and time string with natural format support.
     *
     * <p>Accepts flexible date/time input where year and time are optional:
     * <ul>
     *   <li>Year omitted: uses next future occurrence of the given day/month</li>
     *   <li>Time omitted: defaults to 2359</li>
     *   <li>Time format: 1-2 digits treated as hours, 3-4 digits as HHMM</li>
     *   <li>2-digit years: interpreted using a sliding 100-year window</li>
     * </ul>
     *
     * @param dateTimeString The string to parse.
     * @return The parsed LocalDateTime.
     * @throws DateTimeParseException If the string cannot be parsed.
     */
    public static LocalDateTime parse(String dateTimeString) throws DateTimeParseException {
        String trimmed = dateTimeString.trim();
        if (trimmed.isEmpty()) {
            throw new DateTimeParseException("Empty date/time string", dateTimeString, 0);
        }

        String datePart;
        String timePart;

        // Split by space to separate date and time
        int spaceIndex = trimmed.indexOf(' ');
        assert spaceIndex != 0 : "Trimmed string cannot start with a space";

        if (spaceIndex > 0) {
            datePart = trimmed.substring(0, spaceIndex).trim();
            timePart = trimmed.substring(spaceIndex + 1).trim();
        } else {
            datePart = trimmed;
            timePart = null;
        }

        LocalDate date = parseDate(datePart, dateTimeString);
        LocalTime time = parseTime(timePart, dateTimeString);

        return LocalDateTime.of(date, time);
    }

    /**
     * Parses a date and time string with natural format support, wrapping any parsing error
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
        } catch (DateTimeException e) {
            throw new ChatbotArgumentException(errorMessage);
        }
    }

    /**
     * Formats a LocalDateTime as a string using the full format {@code d/M/yyyy HHmm}.
     *
     * @param dateTime The LocalDateTime to format.
     * @return The formatted date and time string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    // Date parsing helpers

    /**
     * Parses the date portion of the input string.
     *
     * @param datePart The date string in {@code day/month} or {@code day/month/year} format.
     * @param originalInput The original full input string (for error reporting).
     * @return The parsed LocalDate.
     * @throws DateTimeParseException If the date cannot be parsed.
     */
    private static LocalDate parseDate(String datePart, String originalInput) {
        String[] dateSegments = datePart.split("/");

        if (dateSegments.length < 2 || dateSegments.length > 3) {
            throw new DateTimeParseException(
                "Invalid date format, expected day/month or day/month/year", originalInput, 0);
        }

        int day;
        int month;

        try {
            day = Integer.parseInt(dateSegments[0]);
            month = Integer.parseInt(dateSegments[1]);
        } catch (NumberFormatException e) {
            throw new DateTimeParseException("Invalid day or month value", originalInput, 0);
        }

        if (dateSegments.length == 3) {
            int year = parseYear(dateSegments[2], originalInput);
            return LocalDate.of(year, month, day);
        }

        // Year omitted: use next future occurrence
        return resolveNextFutureDate(day, month);
    }

    /**
     * Parses a year string, supporting both 2-digit and 4-digit years.
     *
     * <p>2-digit years use a sliding 100-year window:
     * 0-49 map to 2000-2049, 50-99 map to 1950-1999.
     *
     * @param yearStr The year string to parse.
     * @param originalInput The original full input string (for error reporting).
     * @return The resolved 4-digit year.
     * @throws DateTimeParseException If the year cannot be parsed.
     */
    private static int parseYear(String yearStr, String originalInput) {
        if (yearStr.length() != 2 && yearStr.length() != 4) {
            throw new DateTimeParseException(
                "Invalid year format, expected 2 or 4 digits", originalInput, 0);
        }

        int year;

        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            throw new DateTimeParseException("Invalid year value", originalInput, 0);
        }

        // Apply sliding window for 2-digit years
        if (yearStr.length() == 2) {
            if (year <= 49) {
                year += 2000;
            } else {
                year += 1900;
            }
        }

        return year;
    }

    /**
     * Resolves the next future occurrence of a given day and month.
     * If the date has already passed this year (or is today), uses next year.
     *
     * @param day The day of month.
     * @param month The month.
     * @return The next future LocalDate with the given day and month.
     */
    private static LocalDate resolveNextFutureDate(int day, int month) {
        LocalDate today = LocalDate.now();
        LocalDate candidate = LocalDate.of(today.getYear(), month, day);

        if (!candidate.isAfter(today)) {
            candidate = LocalDate.of(today.getYear() + 1, month, day);
        }

        return candidate;
    }

    // Time parsing helpers

    /**
     * Parses the time portion of the input string.
     *
     * <p>If time is null (omitted), defaults to 23:59.
     *
     * <p>Supports 1-4 digit formats:
     * <ul>
     *   <li>1-2 digits: treated as hours, minutes default to 0 (e.g., {@code 9} = 09:00)</li>
     *   <li>3 digits: first digit is hour, last 2 are minutes (e.g., {@code 930} = 09:30)</li>
     *   <li>4 digits: HHMM (e.g., {@code 1400} = 14:00)</li>
     * </ul>
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
                hour = Integer.parseInt(timePart);
                minute = 0;
            } else if (timePart.length() == 3) {
                hour = Integer.parseInt(timePart.substring(0, 1));
                minute = Integer.parseInt(timePart.substring(1));
            } else if (timePart.length() == 4) {
                hour = Integer.parseInt(timePart.substring(0, 2));
                minute = Integer.parseInt(timePart.substring(2));
            } else {
                throw new DateTimeParseException("Time must be 1-4 digits", originalInput, 0);
            }
        } catch (NumberFormatException e) {
            throw new DateTimeParseException("Invalid time value", originalInput, 0);
        }

        return LocalTime.of(hour, minute);
    }
}
