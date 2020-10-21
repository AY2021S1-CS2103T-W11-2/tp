package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONSULTATIONS;

import java.util.logging.Logger;

import seedu.address.model.Model;

/**
 * View all consultations.
 */
public class ViewConsultationsCommand extends ViewCommand {
    public static final String MESSAGE_SUCCESS = "Listed all consultations: \n";
    private static final Logger LOG = Logger.getGlobal();

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        String result = model.getConsultations(PREDICATE_SHOW_ALL_CONSULTATIONS).toString();

        if (result == null) {
            throw new AssertionError();
        }

        LOG.info("result: " + result);


        if (result.equals("[]")) {
            result = "";
        }

        return new CommandResult(MESSAGE_SUCCESS + result);
    }
}
