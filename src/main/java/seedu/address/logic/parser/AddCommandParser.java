package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME_BRACKET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_CONTACTED_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCUPATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.IncomeBracket;
import seedu.address.model.person.LastContactedDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Occupation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_OCCUPATION, PREFIX_TAG, PREFIX_PRIORITY,
                PREFIX_AGE, PREFIX_LAST_CONTACTED_DATE, PREFIX_INCOME_BRACKET);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_PRIORITY, PREFIX_AGE, PREFIX_LAST_CONTACTED_DATE, PREFIX_INCOME_BRACKET);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        // make email and address optional fields for add command
        Email email = argMultimap.getValue(PREFIX_EMAIL).isPresent()
                ? ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get())
                : new Email("");
        Address address = argMultimap.getValue(PREFIX_ADDRESS).isPresent()
                ? ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get())
                : new Address("");
        Age age = argMultimap.getValue(PREFIX_AGE).isPresent()
                ? ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get())
                : new Age("");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Priority priority = ParserUtil.parsePriority(
                argMultimap.getValue(PREFIX_PRIORITY).orElse(Priority.Level.NONE.toString()));
        Occupation occupation = argMultimap.getValue(PREFIX_OCCUPATION).isPresent()
                ? ParserUtil.parseOccupation(argMultimap.getValue(PREFIX_OCCUPATION).get())
                : new Occupation("");
        IncomeBracket incomeBracket = null;
        LastContactedDate lastContactedDate = argMultimap.getValue(PREFIX_LAST_CONTACTED_DATE).isPresent()
                ? ParserUtil.parseLastContactedDate(argMultimap.getValue(PREFIX_LAST_CONTACTED_DATE).get())
                : new LastContactedDate("");

        Person person = new Person(name, phone, email, address, occupation, tagList, priority,
                age, incomeBracket, lastContactedDate);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
