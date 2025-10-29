package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByPriorityAsc_showsSortedList() {
        ListCommand command = new ListCommand(ListCommand.SortField.PRIORITY, true);
        String expectedMessage = ListCommand.MESSAGE_SUCCESS_SORTED_PRIORITY_ASC;
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listSortedByPriorityDesc_showsSortedList() {
        ListCommand command = new ListCommand(ListCommand.SortField.PRIORITY, false);
        String expectedMessage = ListCommand.MESSAGE_SUCCESS_SORTED_PRIORITY_DESC;
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listSortedByIncomeBracketAsc_showsSortedList() {
        ListCommand command = new ListCommand(ListCommand.SortField.INCOME_BRACKET, true);
        String expectedMessage = ListCommand.MESSAGE_SUCCESS_SORTED_INCOME_ASC;
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listSortedByIncomeBracketDesc_showsSortedList() {
        ListCommand command = new ListCommand(ListCommand.SortField.INCOME_BRACKET, false);
        String expectedMessage = ListCommand.MESSAGE_SUCCESS_SORTED_INCOME_DESC;
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listCommand = new ListCommand();
        ListCommand listCommandPriorityAsc = new ListCommand(ListCommand.SortField.PRIORITY, true);
        ListCommand listCommandPriorityDesc = new ListCommand(ListCommand.SortField.PRIORITY, false);
        ListCommand listCommandIncomeAsc = new ListCommand(ListCommand.SortField.INCOME_BRACKET, true);

        // same object -> returns true
        assertTrue(listCommand.equals(listCommand));
        assertTrue(listCommandPriorityAsc.equals(listCommandPriorityAsc));

        // same values -> returns true
        ListCommand listCommandCopy = new ListCommand();
        assertTrue(listCommand.equals(listCommandCopy));

        ListCommand listCommandPriorityAscCopy = new ListCommand(ListCommand.SortField.PRIORITY, true);
        assertTrue(listCommandPriorityAsc.equals(listCommandPriorityAscCopy));

        // different types -> returns false
        assertFalse(listCommand.equals(1));

        // null -> returns false
        assertFalse(listCommand.equals(null));

        // different sort field -> returns false
        assertFalse(listCommandPriorityAsc.equals(listCommandIncomeAsc));

        // different sort order -> returns false
        assertFalse(listCommandPriorityAsc.equals(listCommandPriorityDesc));

        // no sorting vs with sorting -> returns false
        assertFalse(listCommand.equals(listCommandPriorityAsc));
    }
}
