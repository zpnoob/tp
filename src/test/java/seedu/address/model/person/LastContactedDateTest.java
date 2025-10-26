package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class LastContactedDateTest {

    private static final ZoneId SINGAPORE_ZONE = ZoneId.of("Asia/Singapore");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastContactedDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        // Invalid date format
        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate("2024/01/01"));
        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate("01-01-2024"));
        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate("2024-13-01")); // invalid month
        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate("2024-01-32")); // invalid day
    }

    @Test
    public void constructor_futureDate_throwsIllegalArgumentException() {
        // Future dates should be rejected
        LocalDate tomorrow = LocalDate.now(SINGAPORE_ZONE).plusDays(1);
        LocalDate nextWeek = LocalDate.now(SINGAPORE_ZONE).plusWeeks(1);
        LocalDate nextMonth = LocalDate.now(SINGAPORE_ZONE).plusMonths(1);
        LocalDate nextYear = LocalDate.now(SINGAPORE_ZONE).plusYears(1);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate(tomorrow.format(formatter)));
        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate(nextWeek.format(formatter)));
        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate(nextMonth.format(formatter)));
        assertThrows(IllegalArgumentException.class, () -> new LastContactedDate(nextYear.format(formatter)));
    }

    @Test
    public void isValidLastContactedDate() {
        // null date
        assertThrows(NullPointerException.class, () -> LastContactedDate.isValidLastContactedDate(null));

        // empty string - valid for optional field
        assertTrue(LastContactedDate.isValidLastContactedDate(""));

        // invalid date formats
        assertFalse(LastContactedDate.isValidLastContactedDate("invalid"));
        assertFalse(LastContactedDate.isValidLastContactedDate("2024/01/01")); // wrong separator
        assertFalse(LastContactedDate.isValidLastContactedDate("01-01-2024")); // wrong order
        assertFalse(LastContactedDate.isValidLastContactedDate("2024-1-1")); // missing leading zeros
        assertFalse(LastContactedDate.isValidLastContactedDate("24-01-01")); // two-digit year

        // invalid calendar dates
        assertFalse(LastContactedDate.isValidLastContactedDate("2024-13-01")); // invalid month
        assertFalse(LastContactedDate.isValidLastContactedDate("2024-01-32")); // invalid day
        assertFalse(LastContactedDate.isValidLastContactedDate("2024-02-30")); // February 30th
        assertFalse(LastContactedDate.isValidLastContactedDate("2023-02-29")); // Feb 29 in non-leap year

        // future dates - should all be invalid
        LocalDate tomorrow = LocalDate.now(SINGAPORE_ZONE).plusDays(1);
        LocalDate nextWeek = LocalDate.now(SINGAPORE_ZONE).plusWeeks(1);
        LocalDate nextMonth = LocalDate.now(SINGAPORE_ZONE).plusMonths(1);
        LocalDate nextYear = LocalDate.now(SINGAPORE_ZONE).plusYears(1);
        LocalDate farFuture = LocalDate.now(SINGAPORE_ZONE).plusYears(10);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        assertFalse(LastContactedDate.isValidLastContactedDate(tomorrow.format(formatter)));
        assertFalse(LastContactedDate.isValidLastContactedDate(nextWeek.format(formatter)));
        assertFalse(LastContactedDate.isValidLastContactedDate(nextMonth.format(formatter)));
        assertFalse(LastContactedDate.isValidLastContactedDate(nextYear.format(formatter)));
        assertFalse(LastContactedDate.isValidLastContactedDate(farFuture.format(formatter)));

        // valid dates - today and past dates
        LocalDate today = LocalDate.now(SINGAPORE_ZONE);
        LocalDate yesterday = LocalDate.now(SINGAPORE_ZONE).minusDays(1);
        LocalDate lastWeek = LocalDate.now(SINGAPORE_ZONE).minusWeeks(1);
        LocalDate lastMonth = LocalDate.now(SINGAPORE_ZONE).minusMonths(1);
        LocalDate lastYear = LocalDate.now(SINGAPORE_ZONE).minusYears(1);

        assertTrue(LastContactedDate.isValidLastContactedDate(today.format(formatter))); // today is valid
        assertTrue(LastContactedDate.isValidLastContactedDate(yesterday.format(formatter)));
        assertTrue(LastContactedDate.isValidLastContactedDate(lastWeek.format(formatter)));
        assertTrue(LastContactedDate.isValidLastContactedDate(lastMonth.format(formatter)));
        assertTrue(LastContactedDate.isValidLastContactedDate(lastYear.format(formatter)));
        assertTrue(LastContactedDate.isValidLastContactedDate("2020-01-01")); // past date
        assertTrue(LastContactedDate.isValidLastContactedDate("2000-12-31")); // past date
        assertTrue(LastContactedDate.isValidLastContactedDate("1990-06-15")); // past date
    }

    @Test
    public void toDisplayString() {
        // empty date
        LastContactedDate emptyDate = new LastContactedDate("");
        assertTrue(emptyDate.toDisplayString().equals("N/A"));

        // valid date
        LastContactedDate validDate = new LastContactedDate("2024-01-15");
        assertTrue(validDate.toDisplayString().equals("2024-01-15"));
    }

    @Test
    public void equals() {
        LastContactedDate date = new LastContactedDate("2024-01-15");

        // same values -> returns true
        assertTrue(date.equals(new LastContactedDate("2024-01-15")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new LastContactedDate("2024-01-16")));

        // empty dates
        LastContactedDate emptyDate1 = new LastContactedDate("");
        LastContactedDate emptyDate2 = new LastContactedDate("");
        assertTrue(emptyDate1.equals(emptyDate2));
    }
}
