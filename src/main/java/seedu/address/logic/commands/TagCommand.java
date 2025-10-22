package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Changes the tag of an existing person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the tag of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing tag will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "interested.";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Changed tag of Person: %s";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Tag command not implemented yet";

    private final Index targetIndex;
    private final Tag tag;

    /**
     * Creates a TagCommand to add a tag to the person at the specified index.
     */
    public TagCommand(Index targetIndex, Tag tag) {
        requireNonNull(targetIndex);
        requireNonNull(tag);
        this.targetIndex = targetIndex;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Set<Tag> newTagsSet = Collections.singleton(tag);
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                newTagsSet,
                personToEdit.getPriority(),
                personToEdit.getAge(),
                personToEdit.getIncomeBracket()
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
                && tag.equals(e.tag);
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode() + tag.hashCode();
    }

    @Override
    public String toString() {
        return "TagCommand{targetIndex=Index{value=" + (targetIndex.getOneBased())
            + "}, tag=Tag{tagName='" + tag.tagName + "'}}";
    }
}
