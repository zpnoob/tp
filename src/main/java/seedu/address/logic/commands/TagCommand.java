package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.DncTag;
import seedu.address.model.tag.Tag;

/**
 * Changes the tags of an existing person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the tags of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing tags will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG [" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "interested "
            + PREFIX_TAG + "follow up";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Changed tags of Person: %s";
    public static final String MESSAGE_DNC_CANNOT_MODIFY = "Cannot modify tags of a Do Not Call contact.";
    public static final String MESSAGE_CANNOT_ADD_DNC_TAG =
            "Cannot add Do Not Call tag via tag command. Use the 'dnc' command instead.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Tag command not implemented yet";

    private final Index targetIndex;
    private final Set<Tag> tags;

    /**
     * Creates a TagCommand to add tags to the person at the specified index.
     */
    public TagCommand(Index targetIndex, Set<Tag> tags) {
        requireNonNull(targetIndex);
        requireNonNull(tags);
        this.targetIndex = targetIndex;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (personToEdit.isDncTagged()) {
            throw new CommandException(MESSAGE_DNC_CANNOT_MODIFY);
        }

        if (tags.stream().anyMatch(tag -> tag instanceof DncTag)) {
            throw new CommandException(MESSAGE_CANNOT_ADD_DNC_TAG);
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getOccupation(),
                tags,
                personToEdit.getPriority(),
                personToEdit.getAge(),
                personToEdit.getIncomeBracket(),
                personToEdit.getLastContactedDate()
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS,
                Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand e = (TagCommand) other;
        return targetIndex.equals(e.targetIndex)
                && tags.equals(e.tags);
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode() + tags.hashCode();
    }

    @Override
    public String toString() {
        return "TagCommand{targetIndex=Index{value=" + (targetIndex.getOneBased())
            + "}, tags=" + tags + "}";
    }
}
