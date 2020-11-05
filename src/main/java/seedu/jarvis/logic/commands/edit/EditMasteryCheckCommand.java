package seedu.jarvis.logic.commands.edit;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.CollectionUtil;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.CommandTargetFeature;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.masteryCheck.MasteryCheck;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.Model.PREDICATE_SHOW_ALL_MASTERY_CHECKS;

public class EditMasteryCheckCommand extends EditCommand {

    public static final String MESSAGE_EDIT_MASTERY_CHECK_SUCCESS = "Edited Mastery Check: %1$s";
    public static final String MESSAGE_DUPLICATE_MASTERY_CHECK = "There may not be any duplicates.";


    private final Index index;
    private final EditMasteryCheckCommand.EditMasteryCheckDescriptor editMasteryCheckDescriptor;

    /**
     * @param index of the mastery check in the filtered mastery check list to edit
     * @param editMasteryCheckDescriptor details to edit the mastery check with
     */
    public EditMasteryCheckCommand(Index index, EditMasteryCheckCommand.EditMasteryCheckDescriptor
            editMasteryCheckDescriptor) {
        requireNonNull(index);
        requireNonNull(editMasteryCheckDescriptor);

        this.index = index;
        this.editMasteryCheckDescriptor =
                new EditMasteryCheckCommand.EditMasteryCheckDescriptor(editMasteryCheckDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<MasteryCheck> lastShownList = model.getFilteredMasteryChecksList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MASTERY_CHECK_DISPLAYED_INDEX);
        }

        MasteryCheck masteryCheckToEdit = lastShownList.get(index.getZeroBased());
        MasteryCheck editedMasteryCheck = createEditedMasteryCheck(masteryCheckToEdit, editMasteryCheckDescriptor);

        if (!masteryCheckToEdit.isSameConsultation(editedMasteryCheck) && model.hasMasteryCheck(editedMasteryCheck)) {
            throw new CommandException(MESSAGE_DUPLICATE_MASTERY_CHECK);
        }

        model.setMasteryCheck(masteryCheckToEdit, editedMasteryCheck);
        model.updateFilteredMasteryCheckList(PREDICATE_SHOW_ALL_MASTERY_CHECKS);
        return new CommandResult(String.format(MESSAGE_EDIT_MASTERY_CHECK_SUCCESS, editedMasteryCheck),
                CommandTargetFeature.Students);
    }

    /**
     * Creates and returns a {@code MasteryCheck} with the details of {@code masteryCheckToEdit}
     * edited with {@code editMasteryCheckDescriptor}.
     */
    private static MasteryCheck createEditedMasteryCheck(MasteryCheck masteryCheckToEdit,
        EditMasteryCheckCommand.EditMasteryCheckDescriptor editMasteryCheckDescriptor) {
        assert masteryCheckToEdit != null;

        Boolean passed = editMasteryCheckDescriptor.isPassed().orElse(masteryCheckToEdit.isPassed());

        if (passed) {
            return MasteryCheck.createFullMarkMC(masteryCheckToEdit.getStudentName(),
                    masteryCheckToEdit.getDateAndTime());
        } else {
            return MasteryCheck.createZeroMarkMC(masteryCheckToEdit.getStudentName(),
                    masteryCheckToEdit.getDateAndTime());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMasteryCheckCommand)) {
            return false;
        }

        // state check
        EditMasteryCheckCommand e = (EditMasteryCheckCommand) other;
        return index.equals(e.index)
                && editMasteryCheckDescriptor.equals(e.editMasteryCheckDescriptor);
    }

    /**
     * Stores the details to edit the mastery check with. Each non-empty field value will replace the
     * corresponding field value of the mastery check.
     */
    public static class EditMasteryCheckDescriptor {
        private boolean passed;

        public EditMasteryCheckDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code boolean} is used internally.
         */
        public EditMasteryCheckDescriptor(EditMasteryCheckDescriptor toCopy) {
            setPassed(toCopy.passed);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(passed);
        }

        public void setPassed(Boolean passed) {
            this.passed = passed;
        }

        public Optional<Boolean> isPassed() {
            return Optional.ofNullable(passed);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMasteryCheckCommand.EditMasteryCheckDescriptor)) {
                return false;
            }

            // state check
            EditMasteryCheckCommand.EditMasteryCheckDescriptor e = (EditMasteryCheckCommand.EditMasteryCheckDescriptor)
                    other;

            return isPassed() == e.isPassed();
        }

    }
}