package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.DncTag;

/**
 * Marks a person as "Do Not Call" in the address book.
 */
public class DncCommand extends Command {

    public static final String COMMAND_WORD = "dnc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the person identified "
            + "by the index number used in the last person listing as Do Not Call.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DNC_SUCCESS = "Marked person as Do Not Call: %s";
    public static final String MESSAGE_ALREADY_DNC = "This contact is already marked as Do Not Call.";

    private final Index targetIndex;
    private final DncTag tag;

    /**
     * Creates a DncCommand to mark the person at the specified index as Do Not Call.
     */
    public DncCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.tag = new DncTag();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        assert personToEdit != null : "Person retrieved from FilteredPersonList should not be null";

        if (personToEdit.isDncTagged()) {
            throw new CommandException(MESSAGE_ALREADY_DNC);
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getOccupation(),
                Collections.singleton(tag),
                personToEdit.getPriority(),
                personToEdit.getAge(),
                personToEdit.getIncomeBracket(),
                personToEdit.getLastContactedDate()
        );

        assert !editedPerson.getTags().isEmpty() : "Edited person should have at least the DNC tag";
        assert editedPerson.isDncTagged() : "Edited person should be marked as DNC after adding DNC tag";

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DNC_SUCCESS,
                Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DncCommand)) {
            return false;
        }

        DncCommand e = (DncCommand) other;
        return targetIndex.equals(e.targetIndex)
                && tag.equals(e.tag);
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode() + tag.hashCode();
    }

    @Override
    public String toString() {
        return "DncCommand{targetIndex=Index{value=" + (targetIndex.getOneBased()) + "}}";
    }
}
