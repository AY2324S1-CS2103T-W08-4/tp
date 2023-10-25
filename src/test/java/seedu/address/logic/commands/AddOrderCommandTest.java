package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


class AddOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final int orderNumber = 1;

    private final String medicineName = "panadol";


    @Test
    public void execute_unfilteredList_success() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, orderNumber, medicineName, false);
        Order order = new Order(orderNumber, model.getFilteredPersonList().get(0), medicineName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addOrder(order);

        String expectedMessage = AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS;

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, orderNumber, medicineName, false);

        String expectedMessage = AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS;

        Order order = new Order(orderNumber, personInFilteredList, medicineName);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addOrder(order);

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_allergicToMedicineAndIgnoreAllergy_success() {
        Person person = new PersonBuilder().withAllergies(medicineName).build();
        model.addPerson(person);
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, orderNumber, medicineName, true);

        Order order = new Order(orderNumber, model.getFilteredPersonList().get(0), medicineName);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addOrder(order);

        String expectedMessage = AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS;

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Person person = new PersonBuilder().build();

        AddOrderCommand addOrderCommand = new AddOrderCommand(outOfBoundIndex, orderNumber, medicineName, false);

        Order order = new Order(orderNumber, person, medicineName);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addOrder(order);

        assertCommandFailure(addOrderCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void execute_allergicToMedicineAndNotIgnoreAllergy_failure() {
        Person person = new PersonBuilder().withAllergies(medicineName).build();
        model.addPerson(person);
        AddOrderCommand addOrderCommand = new AddOrderCommand(Index.fromOneBased(model.getFilteredPersonList().size()),
                orderNumber, medicineName,
                false);

        Order order = new Order(orderNumber, model.getFilteredPersonList().get(0), medicineName);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addOrder(order);

        assertCommandFailure(addOrderCommand, model, Messages.MESSAGE_ALLERGIC_TO_MEDICINE);
    }

    @Test
    public void equals() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, orderNumber, medicineName, false);
        assertTrue(addOrderCommand.equals(addOrderCommand));
        assertFalse(addOrderCommand.equals(null));
    }

}
