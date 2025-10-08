package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's priority level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_CONSTRAINTS =
            "Priority should be one of: NONE, LOW, MEDIUM, HIGH (case-insensitive)";

    /**
     * Enum representing the priority levels.
     */
    public enum Level {
        NONE, LOW, MEDIUM, HIGH
    }

    public final Level value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority level.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        value = Level.valueOf(priority.toUpperCase());
    }

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority level.
     */
    public Priority(Level priority) {
        requireNonNull(priority);
        value = priority;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        requireNonNull(test);
        try {
            Level.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the priority level as a string.
     */
    public String getValue() {
        return value.toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Priority)) {
            return false;
        }

        Priority otherPriority = (Priority) other;
        return value.equals(otherPriority.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
