package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;


/**
 * Parser for remark commands.
 */
public class RemarkCommandParser implements Parser {

    /**
     * Parses string into a RemarkCommand
     */
    public RemarkCommand parse(String args) throws ParseException {
        final Prefix PrefixRemark = new Prefix("/r");
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PrefixRemark);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(RemarkCommand.MESSAGE_NOT_IMPLEMENTED_YET,
                    RemarkCommand.MESSAGE_USAGE), ive);
        }

        Remark remark = new Remark(argMultimap.getValue(PrefixRemark).orElse(""));

        return new RemarkCommand(index, remark);
    }
}
