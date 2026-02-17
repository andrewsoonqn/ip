package arnold.inputhandling.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import arnold.chatbotexceptions.ChatbotArgumentException;

public class DateTimeParserTest {

    // parse: Full format (day/month/year time)
    @Test
    public void parse_fullFormat_success() {
        LocalDateTime result = DateTimeParser.parse("25/12/2026 1400");
        assertEquals(LocalDateTime.of(2026, 12, 25, 14, 0), result);
    }

    @Test
    public void parse_fullFormatSingleDigitDayAndMonth_success() {
        LocalDateTime result = DateTimeParser.parse("1/2/2026 0900");
        assertEquals(LocalDateTime.of(2026, 2, 1, 9, 0), result);
    }

    @Test
    public void parse_fullFormatDoubleDigitDayAndMonth_success() {
        LocalDateTime result = DateTimeParser.parse("15/11/2026 1830");
        assertEquals(LocalDateTime.of(2026, 11, 15, 18, 30), result);
    }

    @Test
    public void parse_fullFormatMidnight_success() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 0000");
        assertEquals(LocalDateTime.of(2027, 1, 1, 0, 0), result);
    }

    @Test
    public void parse_fullFormatEndOfDay_success() {
        LocalDateTime result = DateTimeParser.parse("31/12/2026 2359");
        assertEquals(LocalDateTime.of(2026, 12, 31, 23, 59), result);
    }

    // parse: Year omitted (day/month time)
    @Test
    public void parse_yearOmittedFutureDate_usesCurrentYear() {
        // Use a date far enough in the future that it won't pass during test runs
        LocalDateTime result = DateTimeParser.parse("31/12 1400");

        LocalDate today = LocalDate.now();
        LocalDate expectedDate = LocalDate.of(today.getYear(), 12, 31);
        if (!expectedDate.isAfter(today)) {
            expectedDate = LocalDate.of(today.getYear() + 1, 12, 31);
        }

        assertEquals(expectedDate.atTime(14, 0), result);
    }

    @Test
    public void parse_yearOmittedPastDate_usesNextYear() {
        // January 1 is almost always in the past (except on Jan 1 itself)
        LocalDateTime result = DateTimeParser.parse("1/1 1200");

        LocalDate today = LocalDate.now();
        int expectedYear = LocalDate.of(today.getYear(), 1, 1).isAfter(today)
            ? today.getYear()
            : today.getYear() + 1;

        assertEquals(LocalDateTime.of(expectedYear, 1, 1, 12, 0), result);
    }

    @Test
    public void parse_yearOmittedAlwaysFuture_neverReturnsPast() {
        LocalDateTime result = DateTimeParser.parse("15/6 0800");
        assertTrue(result.toLocalDate().isAfter(LocalDate.now()));
    }

    // parse: Time omitted (day/month/year)
    @Test
    public void parse_timeOmittedWithYear_defaultsTo2359() {
        LocalDateTime result = DateTimeParser.parse("25/12/2026");
        assertEquals(LocalDateTime.of(2026, 12, 25, 23, 59), result);
    }

    @Test
    public void parse_timeOmittedSingleDigitDay_defaultsTo2359() {
        LocalDateTime result = DateTimeParser.parse("5/3/2027");
        assertEquals(LocalDateTime.of(2027, 3, 5, 23, 59), result);
    }

    // parse: Both year and time omitted (day/month)
    @Test
    public void parse_yearAndTimeOmitted_futureWith2359() {
        LocalDateTime result = DateTimeParser.parse("25/12");

        LocalDate today = LocalDate.now();
        LocalDate expectedDate = LocalDate.of(today.getYear(), 12, 25);
        if (!expectedDate.isAfter(today)) {
            expectedDate = LocalDate.of(today.getYear() + 1, 12, 25);
        }

        assertEquals(expectedDate.atTime(23, 59), result);
    }

    @Test
    public void parse_yearAndTimeOmittedSingleDigits_futureWith2359() {
        LocalDateTime result = DateTimeParser.parse("1/1");

        LocalDate today = LocalDate.now();
        int expectedYear = LocalDate.of(today.getYear(), 1, 1).isAfter(today)
            ? today.getYear()
            : today.getYear() + 1;

        assertEquals(LocalDateTime.of(expectedYear, 1, 1, 23, 59), result);
    }

    // parse: 2-digit year (sliding window)
    @Test
    public void parse_twoDigitYearLowRange_maps2000s() {
        LocalDateTime result = DateTimeParser.parse("1/1/26 1200");
        assertEquals(LocalDateTime.of(2026, 1, 1, 12, 0), result);
    }

    @Test
    public void parse_twoDigitYearZero_maps2000() {
        LocalDateTime result = DateTimeParser.parse("1/1/00 1200");
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), result);
    }

    @Test
    public void parse_twoDigitYear49_maps2049() {
        LocalDateTime result = DateTimeParser.parse("1/1/49 1200");
        assertEquals(LocalDateTime.of(2049, 1, 1, 12, 0), result);
    }

    @Test
    public void parse_twoDigitYear50_maps1950() {
        LocalDateTime result = DateTimeParser.parse("1/1/50 1200");
        assertEquals(LocalDateTime.of(1950, 1, 1, 12, 0), result);
    }

    @Test
    public void parse_twoDigitYear99_maps1999() {
        LocalDateTime result = DateTimeParser.parse("1/1/99 1200");
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), result);
    }

    // parse: Flexible time formats (1-4 digits)
    @Test
    public void parse_timeSingleDigit_treatedAsHour() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 9");
        assertEquals(LocalDateTime.of(2027, 1, 1, 9, 0), result);
    }

    @Test
    public void parse_timeTwoDigits_treatedAsHour() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 14");
        assertEquals(LocalDateTime.of(2027, 1, 1, 14, 0), result);
    }

    @Test
    public void parse_timeThreeDigits_firstDigitHourLastTwoMinutes() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 930");
        assertEquals(LocalDateTime.of(2027, 1, 1, 9, 30), result);
    }

    @Test
    public void parse_timeFourDigits_hhmm() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 1430");
        assertEquals(LocalDateTime.of(2027, 1, 1, 14, 30), result);
    }

    @Test
    public void parse_timeZero_midnight() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 0");
        assertEquals(LocalDateTime.of(2027, 1, 1, 0, 0), result);
    }

    @Test
    public void parse_timeDoubleZero_midnight() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 00");
        assertEquals(LocalDateTime.of(2027, 1, 1, 0, 0), result);
    }

    @Test
    public void parse_timeFourZeros_midnight() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 0000");
        assertEquals(LocalDateTime.of(2027, 1, 1, 0, 0), result);
    }

    @Test
    public void parse_time23_elevenPm() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 23");
        assertEquals(LocalDateTime.of(2027, 1, 1, 23, 0), result);
    }

    // parse: Whitespace handling
    @Test
    public void parse_leadingAndTrailingSpaces_trimmed() {
        LocalDateTime result = DateTimeParser.parse("  25/12/2026 1400  ");
        assertEquals(LocalDateTime.of(2026, 12, 25, 14, 0), result);
    }

    @Test
    public void parse_multipleSpacesBetweenDateAndTime_success() {
        LocalDateTime result = DateTimeParser.parse("25/12/2026   1400");
        assertEquals(LocalDateTime.of(2026, 12, 25, 14, 0), result);
    }

    // parse: Invalid inputs
    @Test
    public void parse_emptyString_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse(""));
    }

    @Test
    public void parse_blankString_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("   "));
    }

    @Test
    public void parse_noSlashes_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25122026 1400"));
    }

    @Test
    public void parse_singleSlash_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25 1400"));
    }

    @Test
    public void parse_tooManySlashes_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25/12/20/26 1400"));
    }

    @Test
    public void parse_nonNumericDay_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("abc/12/2026 1400"));
    }

    @Test
    public void parse_nonNumericMonth_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25/Jan/2026 1400"));
    }

    @Test
    public void parse_nonNumericYear_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25/12/abcd 1400"));
    }

    @Test
    public void parse_invalidDay_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("32/1/2026 1400"));
    }

    @Test
    public void parse_invalidMonth_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("1/13/2026 1400"));
    }

    @Test
    public void parse_invalidMonthZero_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("1/0/2026 1400"));
    }

    @Test
    public void parse_feb29NonLeapYear_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("29/2/2027 1400"));
    }

    @Test
    public void parse_feb29LeapYear_success() {
        LocalDateTime result = DateTimeParser.parse("29/2/2028 1400");
        assertEquals(LocalDateTime.of(2028, 2, 29, 14, 0), result);
    }

    @Test
    public void parse_threeDigitYear_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25/12/202 1400"));
    }

    @Test
    public void parse_singleDigitYear_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25/12/6 1400"));
    }

    @Test
    public void parse_dayZero_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("0/1/2026 1400"));
    }

    @Test
    public void parse_dayZeroYearOmitted_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("0/1 1400"));
    }

    @Test
    public void parse_timeFiveDigits_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25/12/2026 14000"));
    }

    @Test
    public void parse_timeHour24_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("25/12/2026 24"));
    }

    @Test
    public void parse_timeHour25_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("25/12/2026 2500"));
    }

    @Test
    public void parse_time2400_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("25/12/2026 2400"));
    }

    @Test
    public void parse_timeMinute60_throwsException() {
        assertThrows(Exception.class, () -> DateTimeParser.parse("25/12/2026 1460"));
    }

    @Test
    public void parse_timeThreeDigitsMinute60_throwsException() {
        // "060" = hour 0, minute 60 -- invalid
        assertThrows(Exception.class, () -> DateTimeParser.parse("25/12/2026 060"));
    }

    @Test
    public void parse_timeThreeDigitsMinute59_success() {
        // "059" = hour 0, minute 59 -- valid boundary
        LocalDateTime result = DateTimeParser.parse("1/1/2027 059");
        assertEquals(LocalDateTime.of(2027, 1, 1, 0, 59), result);
    }

    @Test
    public void parse_time2359_validBoundary() {
        LocalDateTime result = DateTimeParser.parse("1/1/2027 2359");
        assertEquals(LocalDateTime.of(2027, 1, 1, 23, 59), result);
    }

    @Test
    public void parse_nonNumericTime_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25/12/2026 abcd"));
    }

    @Test
    public void parse_timeWithColon_throwsException() {
        assertThrows(DateTimeParseException.class, () -> DateTimeParser.parse("25/12/2026 14:00"));
    }

    // parseWithErrorMessage
    @Test
    public void parseWithErrorMessage_validInput_success() {
        LocalDateTime result = DateTimeParser.parseWithErrorMessage("25/12/2026 1400", "error");
        assertEquals(LocalDateTime.of(2026, 12, 25, 14, 0), result);
    }

    @Test
    public void parseWithErrorMessage_validNaturalInput_success() {
        LocalDateTime result = DateTimeParser.parseWithErrorMessage("25/12", "error");
        assertTrue(result.toLocalDate().isAfter(LocalDate.now()));
        assertEquals(23, result.getHour());
        assertEquals(59, result.getMinute());
    }

    @Test
    public void parseWithErrorMessage_invalidInput_throwsChatbotArgumentException() {
        ChatbotArgumentException exception = assertThrows(
            ChatbotArgumentException.class, () ->
                DateTimeParser.parseWithErrorMessage("invalid", "Custom error message"));
        assertEquals("Custom error message", exception.getMessage());
    }

    @Test
    public void parseWithErrorMessage_emptyInput_throwsChatbotArgumentException() {
        assertThrows(ChatbotArgumentException.class, () ->
            DateTimeParser.parseWithErrorMessage("", "Custom error message"));
    }

    @Test
    public void parseWithErrorMessage_invalidDate_throwsChatbotArgumentException() {
        assertThrows(ChatbotArgumentException.class, () ->
            DateTimeParser.parseWithErrorMessage("32/13/2026 1400", "Bad date"));
    }

    @Test
    public void parseWithErrorMessage_invalidTime_throwsChatbotArgumentException() {
        assertThrows(ChatbotArgumentException.class, () ->
            DateTimeParser.parseWithErrorMessage("1/1/2026 2500", "Bad time"));
    }

    // formatDateTime
    @Test
    public void formatDateTime_standardDateTime_fullFormat() {
        String result = DateTimeParser.formatDateTime(LocalDateTime.of(2026, 12, 25, 14, 0));
        assertEquals("25/12/2026 1400", result);
    }

    @Test
    public void formatDateTime_singleDigitDayAndMonth_noLeadingZeros() {
        String result = DateTimeParser.formatDateTime(LocalDateTime.of(2026, 1, 5, 9, 30));
        assertEquals("5/1/2026 0930", result);
    }

    @Test
    public void formatDateTime_midnight_formatsAs0000() {
        String result = DateTimeParser.formatDateTime(LocalDateTime.of(2027, 1, 1, 0, 0));
        assertEquals("1/1/2027 0000", result);
    }

    @Test
    public void formatDateTime_endOfDay_formatsAs2359() {
        String result = DateTimeParser.formatDateTime(LocalDateTime.of(2026, 12, 31, 23, 59));
        assertEquals("31/12/2026 2359", result);
    }

    @Test
    public void formatDateTime_leapDay_success() {
        String result = DateTimeParser.formatDateTime(LocalDateTime.of(2028, 2, 29, 12, 0));
        assertEquals("29/2/2028 1200", result);
    }

    // Round-trip integration tests: parse then format
    @Test
    public void roundTrip_fullFormat_preservesValue() {
        String input = "25/12/2026 1400";
        LocalDateTime parsed = DateTimeParser.parse(input);
        String formatted = DateTimeParser.formatDateTime(parsed);
        assertEquals(input, formatted);
    }

    @Test
    public void roundTrip_formatThenParse_preservesValue() {
        LocalDateTime original = LocalDateTime.of(2026, 6, 15, 8, 30);
        String formatted = DateTimeParser.formatDateTime(original);
        LocalDateTime parsed = DateTimeParser.parse(formatted);
        assertEquals(original, parsed);
    }
}
