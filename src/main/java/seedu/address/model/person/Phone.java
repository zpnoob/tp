package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be between 4 to 17 digits long";
    public static final String VALIDATION_REGEX = "[\\d\\s]{4,}";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        String trimmedPhone = phone.replaceAll("\\s+", "");
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = trimmedPhone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        String digitsOnly = test.replaceAll("\\s+", "");
        int digitCount = digitsOnly.length();
        return digitCount >= 4 && digitCount <= 17;
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
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
