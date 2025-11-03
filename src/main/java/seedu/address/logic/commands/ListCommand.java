package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 * Can optionally sort by priority or income bracket in ascending or descending order.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all persons in the InsuraBook.\n"
            + "Optionally sorts by priority or income bracket.\n"
            + "Parameters: [pr/ORDER] or [i/ORDER] where ORDER is 'asc' or 'desc'\n"
            + "Examples:\n"
            + COMMAND_WORD + " (lists all persons)\n"
            + COMMAND_WORD + " pr/asc (lists all persons sorted by priority in ascending order: LOW to HIGH)\n"
            + COMMAND_WORD + " pr/desc (lists all persons sorted by priority in descending order: HIGH to LOW)\n"
            + COMMAND_WORD + " i/asc (lists all persons sorted by income bracket in ascending order: LOW to HIGH)\n"
            + COMMAND_WORD + " i/desc (lists all persons sorted by income bracket in descending order: HIGH to LOW)";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_SUCCESS_SORTED_PRIORITY_ASC =
            "Listed all persons sorted by priority in ascending order (LOW to HIGH)";
    public static final String MESSAGE_SUCCESS_SORTED_PRIORITY_DESC =
            "Listed all persons sorted by priority in descending order (HIGH to LOW)";
    public static final String MESSAGE_SUCCESS_SORTED_INCOME_ASC =
            "Listed all persons sorted by income bracket in ascending order (LOW to HIGH)";
    public static final String MESSAGE_SUCCESS_SORTED_INCOME_DESC =
            "Listed all persons sorted by income bracket in descending order (HIGH to LOW)";

    /**
     * Enum representing the field to sort by.
     */
    public enum SortField {
        PRIORITY, INCOME_BRACKET
    }

    private final SortField sortField;
    private final boolean isAscending;

    /**
     * Creates a ListCommand to list all persons without sorting.
     */
    public ListCommand() {
        this.sortField = null;
        this.isAscending = true;
    }

    /**
     * Creates a ListCommand to list all persons sorted by the specified field and order.
     *
     * @param sortField The field to sort by (PRIORITY or INCOME_BRACKET).
     * @param isAscending True for ascending order, false for descending order.
     */
    public ListCommand(SortField sortField, boolean isAscending) {
        this.sortField = sortField;
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (sortField != null) {
            Comparator<Person> comparator = getComparator();
            model.updateSortedPersonList(comparator);
            return new CommandResult(getSuccessMessage());
        }

        model.updateSortedPersonList(null);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Returns the appropriate comparator based on the sort field and order.
     */
    private Comparator<Person> getComparator() {
        Comparator<Person> comparator;

        if (sortField == SortField.PRIORITY) {
            comparator = Comparator.comparing(person -> {
                if (person.getPriority().value == seedu.address.model.person.Priority.Level.NONE) {
                    return isAscending ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
                return person.getPriority().value.ordinal();
            });
        } else {
            comparator = Comparator.comparing(person -> {
                if (person.getIncomeBracket() == null) {
                    return isAscending ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
                return person.getIncomeBracket().value.ordinal();
            });
        }

        return isAscending ? comparator : comparator.reversed();
    }

    /**
     * Returns the success message based on the sort field and order.
     */
    private String getSuccessMessage() {
        if (sortField == SortField.PRIORITY) {
            return isAscending ? MESSAGE_SUCCESS_SORTED_PRIORITY_ASC : MESSAGE_SUCCESS_SORTED_PRIORITY_DESC;
        } else {
            return isAscending ? MESSAGE_SUCCESS_SORTED_INCOME_ASC : MESSAGE_SUCCESS_SORTED_INCOME_DESC;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherCommand = (ListCommand) other;
        return sortField == otherCommand.sortField
                && isAscending == otherCommand.isAscending;
    }
}
