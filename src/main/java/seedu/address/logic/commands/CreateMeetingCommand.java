package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Creates a meeting with a person in the address book
 */
public class CreateMeetingCommand extends Command {

    public static final String COMMAND_WORD = "meet";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Schedules a new meeting between you and another user.\n"
        + "Parameters: NAME OF PERSON YOU ARE MEETING (from address book)\n"
        + "Example: " + COMMAND_WORD + "Alex Yeoh";

    public static final String MESSAGE_CREATE_MEETING_SUCCESS = "Created meeting with: %1$s";

    private final String personToMeet;

    public CreateMeetingCommand(String personToMeet) {
        this.personToMeet = personToMeet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String[] personName = personToMeet.split("\\s+");
        NameContainsKeywordsPredicate personNameArray =
            new NameContainsKeywordsPredicate(Arrays.asList(personName));
        model.updateFilteredPersonList(personNameArray);
        ObservableList<Person> listOfPeople = model.getFilteredPersonList();
        if (listOfPeople.isEmpty()) {
            throw new RuntimeException(); // placeholder
        }
        Person otherPerson = listOfPeople.get(0);

        model.createNewMeeting(otherPerson);

        return new CommandResult(String.format(MESSAGE_CREATE_MEETING_SUCCESS, this.personToMeet));
    }

}