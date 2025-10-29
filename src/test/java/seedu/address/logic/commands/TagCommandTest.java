package seedu.address.logic.commands;

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
import seedu.address.model.tag.DncTag;
import seedu.address.model.tag.Tag;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tag newTag = new Tag("friend");
        Set<Tag> newTagsSet = Collections.singleton(newTag);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, newTagsSet);
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getOccupation(),
                newTagsSet,
                personToEdit.getPriority(),
                personToEdit.getAge(),
                personToEdit.getIncomeBracket(),
                personToEdit.getLastContactedDate());

        String tagsString = editedPerson.getTags().stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(", "));
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS,
                                        editedPerson.getName() + "\nPhone: " + editedPerson.getPhone()
                                           + "\nEmail: " + editedPerson.getEmail()
                                           + "\nAddress: " + editedPerson.getAddress()
                                           + "\nOccupation: " + editedPerson.getOccupation()
                                           + "\nAge: " + editedPerson.getAge()
                                           + "\nPriority: " + editedPerson.getPriority()
                                           + "\nIncome: " + editedPerson.getIncomeBracket()
                                           + "\nLast Contacted: " + editedPerson.getLastContactedDate()
                                                        .toDisplayString()
                                           + "\nTags: " + tagsString);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(tagCommand, expectedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        Tag newTag = new Tag("friend");
        Set<Tag> newTagsSet = Collections.singleton(newTag);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, newTagsSet);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_dncContactTag_throwsCommandException() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person dncPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getOccupation(),
                Collections.singleton(new DncTag()),
                personToEdit.getPriority(),
                personToEdit.getAge(),
                personToEdit.getIncomeBracket(),
                personToEdit.getLastContactedDate()
        );
        model.setPerson(personToEdit, dncPerson);

        Tag newTag = new Tag("friend");
        Set<Tag> newTagsSet = Collections.singleton(newTag);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, newTagsSet);

        assertCommandFailure(tagCommand, model, TagCommand.MESSAGE_DNC_CANNOT_MODIFY);
    }

    @Test
    public void equals() {
        Tag tagFriend = new Tag("friend");
        Tag tagColleague = new Tag("colleague");
        Set<Tag> tagFriendSet = Collections.singleton(tagFriend);
        Set<Tag> tagColleagueSet = Collections.singleton(tagColleague);
        TagCommand tagFirstCommand = new TagCommand(INDEX_FIRST_PERSON, tagFriendSet);
        TagCommand tagSecondCommand = new TagCommand(INDEX_SECOND_PERSON, tagColleagueSet);


        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        TagCommand tagFirstCommandCopy = new TagCommand(INDEX_FIRST_PERSON, tagFriendSet);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        assertFalse(tagFirstCommand.equals(1));

        assertFalse(tagFirstCommand.equals(null));

        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }

    @Test
    public void toString_test() {
        Tag tagFriend = new Tag("friend");
        Set<Tag> tagFriendSet = Collections.singleton(tagFriend);
        TagCommand tagFirstCommand = new TagCommand(INDEX_FIRST_PERSON, tagFriendSet);
        String result = tagFirstCommand.toString();
        // The tags set can be printed in different formats depending on the implementation
        // Just verify the key components are present
        assertTrue(result.contains("TagCommand"));
        assertTrue(result.contains("targetIndex=Index{value=1}"));
        assertTrue(result.contains("tags="));
    }
}
