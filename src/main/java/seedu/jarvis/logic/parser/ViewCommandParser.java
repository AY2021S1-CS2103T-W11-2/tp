package seedu.jarvis.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.MISSION_DEADLINE;
import static seedu.jarvis.logic.parser.CliSyntax.QUEST_DEADLINE;
import static seedu.jarvis.logic.parser.CliSyntax.UNGRADED_MISSION;
import static seedu.jarvis.logic.parser.CliSyntax.UNGRADED_QUEST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_CONSULTATION_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_DEADLINE_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_EVENT_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_MASTERY_CHECK_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_PAST_CONSULTATION_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_PAST_MASTERY_CHECK_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_STUDENT;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_TASK_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_TODO_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_UPCOMING_CONSULTATION_LIST;
import static seedu.jarvis.logic.parser.CliSyntax.VIEW_UPCOMING_MASTERY_CHECK_LIST;

import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.logic.commands.view.ViewAllStudentsCommand;
import seedu.jarvis.logic.commands.view.ViewCommand;
import seedu.jarvis.logic.commands.view.ViewConsultationsCommand;
import seedu.jarvis.logic.commands.view.ViewMasteryChecksCommand;
import seedu.jarvis.logic.commands.view.ViewMissionDeadlineCommand;
import seedu.jarvis.logic.commands.view.ViewOneStudentCommand;
import seedu.jarvis.logic.commands.view.ViewPastConsultationsCommand;
import seedu.jarvis.logic.commands.view.ViewPastMasteryChecksCommand;
import seedu.jarvis.logic.commands.view.ViewQuestDeadlineCommand;
import seedu.jarvis.logic.commands.view.ViewTaskListCommand;
import seedu.jarvis.logic.commands.view.ViewUngradedMissionCommand;
import seedu.jarvis.logic.commands.view.ViewUngradedQuestCommand;
import seedu.jarvis.logic.commands.view.ViewUpcomingConsultationsCommand;
import seedu.jarvis.logic.commands.view.ViewUpcomingMasteryChecksCommand;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.flag.Flag;
import seedu.jarvis.model.student.Name;

public class ViewCommandParser implements Parser<ViewCommand> {

    private final Logger logger = LogsCenter.getLogger(ViewCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.M
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
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

        boolean argsHasAdditionalParams = inputsAfterCommandType.length > 1;
        Optional<Name> optionalStudentName = Optional.empty();

        if (argsHasAdditionalParams) {
            logger.info("extra input arguments");
            String[] nameComponents = Arrays.copyOfRange(inputsAfterCommandType, 1, inputsAfterCommandType.length);
            Name studentName = ParserUtil.parseName(nameComponents);
            logger.info("Student name: " + studentName.fullName);
            optionalStudentName = Optional.of(studentName);
        }

        // switch command to return the respective view commands
        switch (commandFlag.getFlag()) {
        case MISSION_DEADLINE:
            return new ViewMissionDeadlineCommand();

        case QUEST_DEADLINE:
            return new ViewQuestDeadlineCommand();

        case VIEW_STUDENT:
            if (optionalStudentName.isPresent()) {
                Name name = optionalStudentName.get();
                return new ViewOneStudentCommand(name);
            } else {
                return new ViewAllStudentsCommand();
            }

        case UNGRADED_MISSION:
            return new ViewUngradedMissionCommand();

        case UNGRADED_QUEST:
            return new ViewUngradedQuestCommand();

        case VIEW_TASK_LIST:
            return new ViewTaskListCommand(VIEW_TASK_LIST);

        case VIEW_TODO_LIST:
            return new ViewTaskListCommand(VIEW_TODO_LIST);

        case VIEW_EVENT_LIST:
            return new ViewTaskListCommand(VIEW_EVENT_LIST);

        case VIEW_DEADLINE_LIST:
            return new ViewTaskListCommand(VIEW_DEADLINE_LIST);

        case VIEW_CONSULTATION_LIST:
            return new ViewConsultationsCommand();

        case VIEW_UPCOMING_CONSULTATION_LIST:
            return new ViewUpcomingConsultationsCommand();

        case VIEW_PAST_CONSULTATION_LIST:
            return new ViewPastConsultationsCommand();

        case VIEW_MASTERY_CHECK_LIST:
            return new ViewMasteryChecksCommand();

        case VIEW_UPCOMING_MASTERY_CHECK_LIST:
            return new ViewUpcomingMasteryChecksCommand();

        case VIEW_PAST_MASTERY_CHECK_LIST:
            return new ViewPastMasteryChecksCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

    }

}
