package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's occupation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOccupation(String)}
 */
public class Occupation {

    public static final String MESSAGE_CONSTRAINTS =
            "Occupation should only contain alphabetic characters and spaces between words. "
            + "It should not be blank and should not have leading/trailing spaces or consecutive spaces.";

    /*
     * Only accepts alphabetic characters with single spaces between words.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z]+( [a-zA-Z]+)*$";

    public final String value;

    /**
     * Constructs an {@code Occupation}.
     *
     * @param occupation A valid occupation.
     */
    public Occupation(String occupation) {
        requireNonNull(occupation);
        String trimmed = occupation.trim();

        if (trimmed.isEmpty()) {
            value = "";
            return;
        }
        checkArgument(isValidOccupation(trimmed), MESSAGE_CONSTRAINTS);
        value = trimmed;
    }

    /**
     * Returns true if a given string is a valid occupation.
     */
    public static boolean isValidOccupation(String test) {
        return test != null && !test.trim().isEmpty() && test.matches(VALIDATION_REGEX);
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

        if (!(other instanceof Occupation)) {
            return false;
        }

        Occupation otherOccupation = (Occupation) other;
        return value.equals(otherOccupation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
