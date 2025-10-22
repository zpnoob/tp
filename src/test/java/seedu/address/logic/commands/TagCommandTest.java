package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validInfexUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tag newTag = new Tag("friend");
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, newTag);
        Set<Tag> newTagsSet = Collections.singleton(newTag);
        Person editedPerson = new Person(
            personToEdit.getName(),
            personToEdit.getPhone(),
            personToEdit.getEmail(),
            personToEdit.getAddress(),
            personToEdit.getOccupation(),
            newTagsSet,
            personToEdit.getPriority());

        String tagsString = editedPerson.getTags().stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(", "));
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS,
                                        editedPerson.getName() + "; Phone: " + editedPerson.getPhone()
                                            + "; Occupation: " + editedPerson.getOccupation()
                                            + "; Priority: " + editedPerson.getPriority()
                                           + "; Email: " + editedPerson.getEmail()
                                           + "; Address: " + editedPerson.getAddress()
                                           + "; Tags: " + tagsString);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(tagCommand, expectedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        Tag newTag = new Tag("friend");
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, newTag);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Tag tagFriend = new Tag("friend");
        Tag tagColleague = new Tag("colleague");
        TagCommand tagFirstCommand = new TagCommand(INDEX_FIRST_PERSON, tagFriend);
        TagCommand tagSecondCommand = new TagCommand(INDEX_SECOND_PERSON, tagColleague);


        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        TagCommand tagFirstCommandCopy = new TagCommand(INDEX_FIRST_PERSON, tagFriend);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        assertFalse(tagFirstCommand.equals(1));

        assertFalse(tagFirstCommand.equals(null));

        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }

    @Test
    public void toString_test() {
        Tag tagFriend = new Tag("friend");
        TagCommand tagFirstCommand = new TagCommand(INDEX_FIRST_PERSON, tagFriend);
        String expectedString = "TagCommand{targetIndex=Index{value=1}, tag=Tag{tagName='friend'}}";
        assertEquals(expectedString, tagFirstCommand.toString());
    }
}
