package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noArgs_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand());
        assertParseSuccess(parser, "   ", new ListCommand());
    }

    @Test
    public void parse_priorityAsc_returnsListCommand() {
        assertParseSuccess(parser, " pr/asc",
                new ListCommand(ListCommand.SortField.PRIORITY, true));
    }

    @Test
    public void parse_priorityDesc_returnsListCommand() {
        assertParseSuccess(parser, " pr/desc",
                new ListCommand(ListCommand.SortField.PRIORITY, false));
    }

    @Test
    public void parse_incomeBracketAsc_returnsListCommand() {
        assertParseSuccess(parser, " i/asc",
                new ListCommand(ListCommand.SortField.INCOME_BRACKET, true));
    }

    @Test
    public void parse_incomeBracketDesc_returnsListCommand() {
        assertParseSuccess(parser, " i/desc",
                new ListCommand(ListCommand.SortField.INCOME_BRACKET, false));
    }

    @Test
    public void parse_priorityAscCaseInsensitive_returnsListCommand() {
        assertParseSuccess(parser, " pr/ASC",
                new ListCommand(ListCommand.SortField.PRIORITY, true));
        assertParseSuccess(parser, " pr/AsC",
                new ListCommand(ListCommand.SortField.PRIORITY, true));
    }

    @Test
    public void parse_invalidOrder_throwsParseException() {
        assertParseFailure(parser, " pr/invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " i/up",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_bothPrefixes_throwsParseException() {
        assertParseFailure(parser, " pr/asc i/desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_preamble_throwsParseException() {
        assertParseFailure(parser, "some text pr/asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingOrder_throwsParseException() {
        assertParseFailure(parser, " pr/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " i/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
