package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.DncTag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DncCommand}.
 */
public class DncCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DncCommand dncCommand = new DncCommand(INDEX_FIRST_PERSON);

        Person markedPerson = createDncPerson(personToMark);
        String expectedMessage = String.format(DncCommand.MESSAGE_DNC_SUCCESS, Messages.format(markedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), markedPerson);

        assertCommandSuccess(dncCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DncCommand dncCommand = new DncCommand(outOfBoundIndex);

        assertCommandFailure(dncCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DncCommand dncCommand = new DncCommand(INDEX_FIRST_PERSON);

        Person markedPerson = createDncPerson(personToMark);
        String expectedMessage = String.format(DncCommand.MESSAGE_DNC_SUCCESS, Messages.format(markedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), markedPerson);

        assertCommandSuccess(dncCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DncCommand dncCommand = new DncCommand(outOfBoundIndex);

        assertCommandFailure(dncCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyMarkedAsDnc_throwsCommandException() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person dncPerson = createDncPerson(personToMark);
        model.setPerson(personToMark, dncPerson);

        DncCommand dncCommand = new DncCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(dncCommand, model, DncCommand.MESSAGE_ALREADY_DNC);
    }

    @Test
    public void execute_markPersonAsDnc_personHasDncTag() {
        DncCommand dncCommand = new DncCommand(INDEX_FIRST_PERSON);

        try {
            dncCommand.execute(model);
            Person markedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            assertTrue(markedPerson.isDncTagged());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void equals() {
        DncCommand dncFirstCommand = new DncCommand(INDEX_FIRST_PERSON);
        DncCommand dncSecondCommand = new DncCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(dncFirstCommand.equals(dncFirstCommand));

        // same values -> returns true
        DncCommand dncFirstCommandCopy = new DncCommand(INDEX_FIRST_PERSON);
        assertTrue(dncFirstCommand.equals(dncFirstCommandCopy));

        // different types -> returns false
        assertFalse(dncFirstCommand.equals(1));

        // null -> returns false
        assertFalse(dncFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(dncFirstCommand.equals(dncSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DncCommand dncCommand = new DncCommand(targetIndex);
        String expected = "DncCommand{targetIndex=Index{value=1}}";
        assertEquals(expected, dncCommand.toString());
    }

    @Test
    public void hashCode_sameCommand_returnsSameHashCode() {
        DncCommand dncFirstCommand = new DncCommand(INDEX_FIRST_PERSON);
        DncCommand dncFirstCommandCopy = new DncCommand(INDEX_FIRST_PERSON);
        assertEquals(dncFirstCommand.hashCode(), dncFirstCommandCopy.hashCode());
    }

    @Test
    public void hashCode_differentCommand_returnsDifferentHashCode() {
        DncCommand dncFirstCommand = new DncCommand(INDEX_FIRST_PERSON);
        DncCommand dncSecondCommand = new DncCommand(INDEX_SECOND_PERSON);
        assertFalse(dncFirstCommand.hashCode() == dncSecondCommand.hashCode());
    }

    @Test
    public void execute_validIndexLargeList_success() {
        Model largeModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index lastIndex = Index.fromOneBased(largeModel.getFilteredPersonList().size());
        Person personToMark = largeModel.getFilteredPersonList().get(lastIndex.getZeroBased());
        DncCommand dncCommand = new DncCommand(lastIndex);

        Person markedPerson = createDncPerson(personToMark);
        String expectedMessage = String.format(DncCommand.MESSAGE_DNC_SUCCESS, Messages.format(markedPerson));

        Model expectedModel = new ModelManager(new AddressBook(largeModel.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToMark, markedPerson);

        assertCommandSuccess(dncCommand, largeModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assertionsEnabled_success() {
        DncCommand dncCommand = new DncCommand(INDEX_FIRST_PERSON);

        try {
            CommandResult result = dncCommand.execute(model);
            assertNotNull(result);
            Person markedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            assertTrue(markedPerson.isDncTagged());
            assertFalse(markedPerson.getTags().isEmpty());
        } catch (Exception e) {
            throw new AssertionError("Execution should succeed with all assertions passing", e);
        }
    }

    @Test
    public void execute_validExecution_allAssertionsPass() {
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DncCommand dncCommand = new DncCommand(INDEX_FIRST_PERSON);

        try {
            CommandResult result = dncCommand.execute(testModel);
            Person resultPerson = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

            assertNotNull(resultPerson);
            assertFalse(resultPerson.getTags().isEmpty());
            assertTrue(resultPerson.isDncTagged());
            assertNotNull(result);
        } catch (Exception e) {
            throw new AssertionError("All assertions should pass during normal execution", e);
        }
    }

    /**
     * Creates a copy of {@code person} with the DNC tag added.
     */
    private Person createDncPerson(Person person) {
        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getOccupation(),
                Collections.singleton(new DncTag()),
                person.getPriority(),
                person.getAge(),
                person.getIncomeBracket(),
                person.getLastContactedDate()
        );
    }
}
