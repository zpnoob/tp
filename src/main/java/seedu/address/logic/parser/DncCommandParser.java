package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DncCommand;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DncCommand object
 */
public class DncCommandParser implements Parser<DncCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DncCommand
     * and returns a DncCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DncCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DncCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DncCommand.MESSAGE_USAGE), pe);
        }
    }
}
