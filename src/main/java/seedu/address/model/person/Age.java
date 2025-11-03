package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's age in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age {

    public static final String MESSAGE_CONSTRAINTS =
            "Age should be between 10 and 120 (inclusive).";
    public static final String VALIDATION_REGEX = "\\d{1,3}";
    public static final int MIN_AGE = 10;
    public static final int MAX_AGE = 120;
    public final String value;

    /**
     * Constructs an {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(String age) {
        requireNonNull(age);

        if (age.isEmpty()) {
            value = age;
        } else {
            // Remove leading zeros by parsing as integer and converting back to string
            String normalizedAge = String.valueOf(Integer.parseInt(age));
            checkArgument(isValidAge(normalizedAge), MESSAGE_CONSTRAINTS);
            value = normalizedAge;
        }
    }

    /**
     * Returns true if a given string is a valid age.
     * Valid age must be empty or a number between 10 and 120 (inclusive).
     */
    public static boolean isValidAge(String test) {
        if (test.isEmpty()) {
            return true;
        }

        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        try {
            int ageValue = Integer.parseInt(test);
            return ageValue >= MIN_AGE && ageValue <= MAX_AGE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Age)) {
            return false;
        }

        Age otherAge = (Age) other;
        return value.equals(otherAge.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

