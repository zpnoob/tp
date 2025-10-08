package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "priority";

    public static final String MESSAGE_SUCCESS = "This is a priority command";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
