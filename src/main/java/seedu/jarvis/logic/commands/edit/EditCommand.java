package seedu.jarvis.logic.commands.edit;

import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;

public abstract class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits information stored in Jarvis according to the " + "command inputted.\n"
            + "edit -s [index number used in student list] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TELEGRAM + "TELEGRAM] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TELEGRAM + "example132 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "edit -l "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_PASSWORD + "PASSWORD]\n"
            + "Example: " + COMMAND_WORD + PREFIX_USERNAME
            + "nusstu\\e1234567 " + PREFIX_PASSWORD + "password";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
