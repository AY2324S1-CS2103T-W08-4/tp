package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;

import seedu.address.model.Model;

/**
 * Lists all employees in the TaskHub to the user.
 */
public class ListEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "listE";

    public static final String MESSAGE_SUCCESS = "Listed all employees";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
