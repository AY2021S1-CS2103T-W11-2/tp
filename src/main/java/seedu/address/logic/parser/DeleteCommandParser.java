package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.DELETE_TASK;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flag.Flag;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_DELETE_USAGE));
        }

        // split the string trimmedArgs with regex of one or more whitespace characters.
        String[] nameKeywords = trimmedArgs.split("\\s+");
        Flag commandFlag = ParserUtil.parseFlag(nameKeywords[0]);

        switch(commandFlag.getFlag()) {
        case DELETE_TASK:
            logger.info("DeleteCommandParser attempts to parse user's delete Task input");
            try {
                String taskId = TaskCommandParser.parseDeleteTask(nameKeywords);
                return new DeleteCommand(taskId);

            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_DELETE_TASK_USAGE), pe);
            }

        //Include other cases here

        default:
            logger.info("DeleteCommandParser attempts to parse user's delete student input by default");
            try {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);

            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_DELETE_USAGE), pe);
            }
        }
    }

}
