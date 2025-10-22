package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;

/**
 * Changes the priority of an existing person in the address book.
 * This is an alias for the edit command with priority parameter.
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "priority";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the priority of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) PRIORITY (must be NONE, LOW, MEDIUM, or HIGH)\n"
            + "Example: " + COMMAND_WORD + " 1 HIGH";

    public static final String MESSAGE_PRIORITY_PERSON_SUCCESS = "Changed priority of Person: %1$s to %2$s";

    private final Index targetIndex;
    private final Priority newPriority;

    /**
     * Creates a PriorityCommand to change the priority of the specified person
     */
    public PriorityCommand(Index targetIndex, Priority newPriority) {
        requireNonNull(targetIndex);
        requireNonNull(newPriority);
        this.targetIndex = targetIndex;
        this.newPriority = newPriority;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                newPriority,
                personToEdit.getAge()
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_PRIORITY_PERSON_SUCCESS,
                Messages.format(editedPerson), newPriority.getValue()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PriorityCommand)) {
            return false;
        }

        PriorityCommand otherPriorityCommand = (PriorityCommand) other;
        return targetIndex.equals(otherPriorityCommand.targetIndex)
                && newPriority.equals(otherPriorityCommand.newPriority);
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode() + newPriority.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("newPriority", newPriority)
                .toString();
    }
}
