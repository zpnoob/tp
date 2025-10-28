package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
        "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        if (duplicatePrefixes.length == 1 && duplicatePrefixes[0].toString().equals("o/")) {
            return "Only one o/ (occupation) input is allowed. Remove the extra occurrences and try again.";
        }

        Set<String> duplicateFields = Stream.of(duplicatePrefixes)
                .map(prefix -> {
                    String p = prefix.toString();
                    String friendly;
                    switch (p) {
                    case "n/":
                        friendly = "name";
                        break;
                    case "p/":
                        friendly = "phone";
                        break;
                    case "e/":
                        friendly = "email";
                        break;
                    case "a/":
                        friendly = "address";
                        break;
                    case "o/":
                        friendly = "occupation";
                        break;
                    case "pr/":
                        friendly = "priority";
                        break;
                    case "age/":
                        friendly = "age";
                        break;
                    case "i/":
                        friendly = "income bracket";
                        break;
                    case "lc/":
                        friendly = "last contacted date";
                        break;
                    default:
                        friendly = p;
                    }
                    return String.format("%s (%s)", p, friendly);
                })
                .collect(Collectors.toSet());

        String joined = String.join(", ", duplicateFields);
        return String.format("Please specify each of the following fields at most once:"
        + "%s. Remove duplicate entries and try again.",
                joined);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
            .append("\nPhone: ")
            .append(person.getPhone());
        if (!person.getEmail().toString().isEmpty()) {
            builder.append("\nEmail: ").append(person.getEmail());
        }
        if (!person.getAddress().toString().isEmpty()) {
            builder.append("\nAddress: ").append(person.getAddress());
        }
        if (!person.getOccupation().toString().isEmpty()) {
            builder.append("\nOccupation: ").append(person.getOccupation());
        }
        if (!person.getAge().toString().isEmpty()) {
            builder.append("\nAge: ").append(person.getAge());
        }
        builder.append("\nPriority: ")
            .append(person.getPriority());
        if (person.getIncomeBracket() != null) {
            builder.append("\nIncome: ").append(person.getIncomeBracket());
        }
        if (!person.getLastContactedDate().toDisplayString().isEmpty()
                && !person.getLastContactedDate().toDisplayString().equals("N/A")) {
            builder.append("\nLast Contacted: ").append(person.getLastContactedDate().toDisplayString());
        }
        if (!person.getTags().isEmpty()) {
            String tagsString = person.getTags().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            builder.append("\nTags: ").append(tagsString);
        }
        return builder.toString();
    }
}
