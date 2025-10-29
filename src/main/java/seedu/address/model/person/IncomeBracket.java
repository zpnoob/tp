package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's income bracket in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIncomeBracket(String)}
 */
public class IncomeBracket {

    public static final String MESSAGE_CONSTRAINTS =
            "Income bracket should be one of: low, middle, high (case-insensitive)";

    /**
     * Enum representing the income bracket levels.
     */
    public enum Level {
        NONE(""),
        LOW("Low Income"),
        MIDDLE("Middle Income"),
        HIGH("High Income");

        private final String displayValue;

        Level(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        @Override
        public String toString() {
            return displayValue;
        }
    }

    public final Level value;

    /**
     * Constructs an {@code IncomeBracket}.
     *
     * @param incomeBracket A valid income bracket level.
     */
    public IncomeBracket(String incomeBracket) {
        requireNonNull(incomeBracket);
        checkArgument(isValidIncomeBracket(incomeBracket), MESSAGE_CONSTRAINTS);
        value = parseIncomeBracket(incomeBracket.toLowerCase().trim());
    }



    /**
     * Constructs an {@code IncomeBracket}.
     *
     * @param incomeBracket A valid income bracket level.
     */
    public IncomeBracket(Level incomeBracket) {
        requireNonNull(incomeBracket);
        value = incomeBracket;
    }

    /**
     * Returns true if a given string is a valid income bracket.
     */
    public static boolean isValidIncomeBracket(String test) {
        requireNonNull(test);
        String normalized = test.toLowerCase().trim();
        return normalized.equals("low") || normalized.equals("middle") || normalized.equals("high");
    }

    /**
     * Parses the income bracket string to the corresponding Level enum.
     */
    private static Level parseIncomeBracket(String incomeBracket) {
        switch (incomeBracket) {
        case "low":
            return Level.LOW;
        case "middle":
            return Level.MIDDLE;
        case "high":
            return Level.HIGH;
        default:
            throw new IllegalArgumentException("Invalid income bracket: " + incomeBracket);
        }
    }

    /**
     * Creates an IncomeBracket from a string representation.
     *
     * @param incomeBracket A valid string representation of income bracket.
     * @return An IncomeBracket object.
     * @throws IllegalArgumentException if the string is not a valid income bracket.
     */
    public static IncomeBracket fromString(String incomeBracket) {
        if (!isValidIncomeBracket(incomeBracket)) {
            throw new IllegalArgumentException("Invalid income bracket: " + incomeBracket);
        }
        return new IncomeBracket(parseIncomeBracket(incomeBracket.toLowerCase().trim()));
    }

    /**
     * Returns the income bracket level as a string.
     */
    public String getValue() {
        return value.toString();
    }

    @Override
    public String toString() {
        return value.name();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IncomeBracket)) {
            return false;
        }

        IncomeBracket otherIncomeBracket = (IncomeBracket) other;
        return value.equals(otherIncomeBracket.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
