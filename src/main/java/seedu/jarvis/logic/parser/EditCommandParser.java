package seedu.jarvis.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.commons.util.StringUtil.pad;
import static seedu.jarvis.logic.parser.CliSyntax.EDIT_LOGIN;
import static seedu.jarvis.logic.parser.CliSyntax.EDIT_STUDENT;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.Arrays;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.edit.EditCommand;
import seedu.jarvis.logic.commands.edit.EditLoginCommand;
import seedu.jarvis.logic.commands.edit.EditLoginCommand.EditLoginDescriptor;
import seedu.jarvis.logic.commands.edit.EditStudentCommand;
import seedu.jarvis.logic.commands.edit.EditStudentCommand.EditPersonDescriptor;
import seedu.jarvis.logic.commands.view.ViewCommand;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.flag.Flag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        // split the string trimmedArgs with regex of one or more whitespace characters.
        // result will be as such: {-s, Alex, Yeoh}
        String[] inputsAfterCommandType = trimmedArgs.split("\\s+");
        assert inputsAfterCommandType.length > 0 : "String array of the arguments is empty";

        Flag commandFlag;
        try {
            commandFlag = ParserUtil.parseFlag(inputsAfterCommandType[0]);
        } catch (ParseException ex) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        String editArgs = String.join(" ", Arrays.copyOfRange(inputsAfterCommandType, 1,
                inputsAfterCommandType.length));

        ArgumentMultimap argMultimap;

        switch (commandFlag.getFlag()) {
        case EDIT_STUDENT:
            argMultimap = ArgumentTokenizer.tokenize(editArgs, PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_EMAIL);

            Index index;

            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE), pe);
            }

            EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
                editPersonDescriptor.setTelegram(ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get()));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            }

            if (!editPersonDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditStudentCommand.MESSAGE_NOT_EDITED);
            }

            return new EditStudentCommand(index, editPersonDescriptor);

        case EDIT_LOGIN:
            argMultimap = ArgumentTokenizer.tokenize(pad(editArgs), PREFIX_USERNAME, PREFIX_PASSWORD);

            EditLoginDescriptor editLoginDescriptor = new EditLoginDescriptor();
            if (argMultimap.getValue(PREFIX_USERNAME).isPresent()) {
                editLoginDescriptor.setUsername(ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get()));
            }
            if (argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
                editLoginDescriptor.setPassword(argMultimap.getValue(PREFIX_PASSWORD).get());
            }

            if (!editLoginDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditLoginCommand.MESSAGE_NOT_EDITED);
            }

            return new EditLoginCommand(editLoginDescriptor);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE));
        }
    }
}
