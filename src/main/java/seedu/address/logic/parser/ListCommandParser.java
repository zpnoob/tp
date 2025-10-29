package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME_BRACKET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new ListCommand();
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PRIORITY, PREFIX_INCOME_BRACKET);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        boolean hasPriority = argMultimap.getValue(PREFIX_PRIORITY).isPresent();
        boolean hasIncomeBracket = argMultimap.getValue(PREFIX_INCOME_BRACKET).isPresent();

        if (hasPriority && hasIncomeBracket) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        if (!hasPriority && !hasIncomeBracket) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        if (hasPriority) {
            String order = argMultimap.getValue(PREFIX_PRIORITY).get().trim().toLowerCase();
            if (!order.equals("asc") && !order.equals("desc")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            return new ListCommand(ListCommand.SortField.PRIORITY, order.equals("asc"));
        } else {
            String order = argMultimap.getValue(PREFIX_INCOME_BRACKET).get().trim().toLowerCase();
            if (!order.equals("asc") && !order.equals("desc")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            return new ListCommand(ListCommand.SortField.INCOME_BRACKET, order.equals("asc"));
        }
    }
}
