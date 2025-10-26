package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Person's last contacted date in the address book.
 */
public class LastContactedDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Last contacted date should be in YYYY-MM-DD format (e.g., 2025-09-20), "
            + "should be a valid calendar date, and cannot be a future date.";

    // Using ISO standard YYYY-MM-DD
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    // Null or empty string to represent no date set as its optional at the start
    public final String value;

    /**
     * Constructs a {@code LastContactedDate}.
     *
     * @param dateString A valid date string in YYYY-MM-DD format, or an empty string.
     */
    public LastContactedDate(String dateString) {
        requireNonNull(dateString);
        assert dateString != null : "Date string must not be null after requireNonNull check.";
        checkArgument(isValidLastContactedDate(dateString), MESSAGE_CONSTRAINTS);
        assert isValidLastContactedDate(dateString) : MESSAGE_CONSTRAINTS;
        this.value = dateString;
        assert this.value != null;
    }

    /**
     * Returns true if a given string is a valid date in YYYY-MM-DD format, or an empty string.
     * The date must not be in the future.
     */
    public static boolean isValidLastContactedDate(String test) {
        if (test.isEmpty()) {
            return true; // Empty string is valid for optional fields
        }
        try {
            LocalDate parsed = LocalDate.parse(test, FORMATTER);
            assert parsed != null;
            assert parsed.toString().equals(test) : "Parsed date normalized does not match input";

            // Check that the date is not in the future
            LocalDate today = LocalDate.now();
            if (parsed.isAfter(today)) {
                return false;
            }

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the date formatted as a String, or an empty string if not set.
     */
    public String toString() {
        return value;
    }

    /**
     * Returns a display friendly string for the date, or "N/A" if not set.
     */
    public String toDisplayString() {
        return value.isEmpty() ? "N/A" : value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LastContactedDate)) {
            return false;
        }

        LastContactedDate otherDate = (LastContactedDate) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


}
