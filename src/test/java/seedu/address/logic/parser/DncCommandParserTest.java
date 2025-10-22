package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DncCommand;

public class DncCommandParserTest {

    private final DncCommandParser parser = new DncCommandParser();

    @Test
    public void parse_validArgs_returnsDncCommand() {
        assertParseSuccess(parser, "1", new DncCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DncCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DncCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DncCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DncCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndexWithText_throwsParseException() {
        assertParseFailure(parser, "1 extra text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DncCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsExercisesAssertion_returnsDncCommand() {
        assertParseSuccess(parser, "1", new DncCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "5", new DncCommand(seedu.address.commons.core.index.Index.fromOneBased(5)));
    }
}
