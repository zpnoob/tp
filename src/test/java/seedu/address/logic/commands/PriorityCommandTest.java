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

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.DncTag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code PriorityCommand}.
 */
public class PriorityCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Priority newPriority = new Priority(Priority.Level.HIGH);
        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON, newPriority);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                newPriority);

        String expectedMessage = String.format(PriorityCommand.MESSAGE_PRIORITY_PERSON_SUCCESS,
                Messages.format(editedPerson), newPriority.getValue());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(priorityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Priority newPriority = new Priority(Priority.Level.HIGH);
        PriorityCommand priorityCommand = new PriorityCommand(outOfBoundIndex, newPriority);

        assertCommandFailure(priorityCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Priority newPriority = new Priority(Priority.Level.MEDIUM);
        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON, newPriority);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                newPriority);

        String expectedMessage = String.format(PriorityCommand.MESSAGE_PRIORITY_PERSON_SUCCESS,
                Messages.format(editedPerson), newPriority.getValue());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(priorityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Priority newPriority = new Priority(Priority.Level.LOW);
        PriorityCommand priorityCommand = new PriorityCommand(outOfBoundIndex, newPriority);

        assertCommandFailure(priorityCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_dncContactPriority_throwsCommandException() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person dncPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                Collections.singleton(new DncTag()),
                personToEdit.getPriority()
        );
        model.setPerson(personToEdit, dncPerson);

        Priority newPriority = new Priority(Priority.Level.HIGH);
        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON, newPriority);

        assertCommandFailure(priorityCommand, model, PriorityCommand.MESSAGE_DNC_CANNOT_MODIFY);
    }

    @Test
    public void equals() {
        Priority priorityHigh = new Priority(Priority.Level.HIGH);
        Priority priorityLow = new Priority(Priority.Level.LOW);
        PriorityCommand priorityFirstCommand = new PriorityCommand(INDEX_FIRST_PERSON, priorityHigh);
        PriorityCommand prioritySecondCommand = new PriorityCommand(INDEX_SECOND_PERSON, priorityLow);

        // same object -> returns true
        assertTrue(priorityFirstCommand.equals(priorityFirstCommand));

        // same values -> returns true
        PriorityCommand priorityFirstCommandCopy = new PriorityCommand(INDEX_FIRST_PERSON, priorityHigh);
        assertTrue(priorityFirstCommand.equals(priorityFirstCommandCopy));

        // different types -> returns false
        assertFalse(priorityFirstCommand.equals(1));

        // null -> returns false
        assertFalse(priorityFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(priorityFirstCommand.equals(prioritySecondCommand));

        // different priority -> returns false
        PriorityCommand priorityFirstCommandDifferentPriority = new PriorityCommand(INDEX_FIRST_PERSON, priorityLow);
        assertFalse(priorityFirstCommand.equals(priorityFirstCommandDifferentPriority));
    }

    @Test
    public void toString_test() {
        Priority priority = new Priority(Priority.Level.HIGH);
        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON, priority);
        String expected = PriorityCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_PERSON
                + ", newPriority=" + priority + "}";
        assertEquals(expected, priorityCommand.toString());
    }
}
