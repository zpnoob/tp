package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PriorityCommand;
import seedu.address.model.person.Priority;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the PriorityCommand code. For example, inputs "1 HIGH" and "1 high" take the
 * same path through the PriorityCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class PriorityCommandParserTest {

    private PriorityCommandParser parser = new PriorityCommandParser();

    @Test
    public void parse_validArgs_returnsPriorityCommand() {
        Priority expectedPriority = new Priority(Priority.Level.HIGH);
        PriorityCommand expectedCommand = new PriorityCommand(INDEX_FIRST_PERSON, expectedPriority);

        // Test uppercase
        assertParseSuccess(parser, "1 HIGH", expectedCommand);
        // Test lowercase (case insensitive)
        assertParseSuccess(parser, "1 high", expectedCommand);
        // Test other priority levels
        Priority lowPriority = new Priority(Priority.Level.LOW);
        PriorityCommand lowCommand = new PriorityCommand(INDEX_FIRST_PERSON, lowPriority);
        assertParseSuccess(parser, "1 LOW", lowCommand);
        Priority mediumPriority = new Priority(Priority.Level.MEDIUM);
        PriorityCommand mediumCommand = new PriorityCommand(INDEX_FIRST_PERSON, mediumPriority);
        assertParseSuccess(parser, "1 MEDIUM", mediumCommand);
        Priority nonePriority = new Priority(Priority.Level.NONE);
        PriorityCommand noneCommand = new PriorityCommand(INDEX_FIRST_PERSON, nonePriority);
        assertParseSuccess(parser, "1 NONE", noneCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing arguments
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        // Missing priority
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        // Missing index
        assertParseFailure(parser, "HIGH", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        // Invalid index
        assertParseFailure(parser, "a HIGH", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        // Invalid priority
        assertParseFailure(parser, "1 INVALID", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        // Too many arguments
        assertParseFailure(parser, "1 HIGH EXTRA", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        // Negative index
        assertParseFailure(parser, "-1 HIGH", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
        // Zero index
        assertParseFailure(parser, "0 HIGH", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceArgs_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PriorityCommand.MESSAGE_USAGE));
    }
}
