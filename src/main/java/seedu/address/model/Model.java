package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Medicine> PREDICATE_SHOW_ALL_MEDICINES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Clears the address book data
     */
    void clearAddressBook();

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);


    /**
     * Returns true if an order with the same identity as {@code order} exists in the address book.
     */
    boolean hasOrder(Order order);


    /**
     * Returns true if a medicine with the same identity as {@code medicine} exists in the address book.
     */
    boolean hasMedicine(Medicine medicine);


    /**
     * Deletes the given person and the person's orders.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Adds the given order.
     */
    void addOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in the address book.
     */
    void deleteOrder(Order target);

    /**
     * Gets the order with the specified orderNumber.
     * @param orderNumber
     * @return Order with the specified orderNumber.
     */
    Optional<Order> getOrder(String orderNumber);
    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the address book.
     * The Order identity of {@code editedOrder} must not be the same as another existing Order in the address book.
     */
    void setOrder(Order target, Order editedOrder);

    /**
     * Adds the given medicine.
     */
    void addMedicine(Medicine medicine);

    /**
     * Deletes the given medicine.
     * The medicine must exist in the address book.
     */
    void deleteMedicine(Medicine medicine);

    /**
     * Gets the medicine that has the identity as the given oven.
     * @param medicine
     * @return medicine with the same identity.
     */
    Optional<Medicine> getMedicine(Medicine medicine);

    /**
     * Replaces the given medicine {@code target} with {@code editedMedicine}.
     * {@code target} must exist in the address book.
     * The Medicine identity of {@code editedMedicine} must not be the same as another existing Medicine in the
     * address book.
     */
    void setMedicine(Medicine target, Medicine editedMedicine);

    boolean canUndo();

    void undo();

    boolean canRedo();

    void redo();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Returns an unmodifiable view of the filtered order list */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);

    /** Returns an unmodifiable view of the filtered medicine list */
    ObservableList<Medicine> getFilteredMedicineList();

    /**
     * Updates the filter of the filtered medicine list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMedicineList(Predicate<Medicine> predicate);


}
