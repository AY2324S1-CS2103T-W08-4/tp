package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.InfoObject;
import seedu.address.model.person.Person;


/**
 * Represents an Order in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order implements InfoObject {
    private final OrderNumber orderNumber;
    private final Person person;
    private final String medicineName;

    private final Status orderStatus;
    /**
     * Every field must be present and not null.
     */
    public Order(OrderNumber orderNumber, Person person, String medicineName, Status orderStatus) {
        requireAllNonNull(orderNumber, person, medicineName);
        this.orderNumber = orderNumber;
        this.person = person;
        this.medicineName = medicineName;
        this.orderStatus = orderStatus;
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public Person getPerson() {
        return person;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Status getStatus() {
        return orderStatus;
    }
    /**
     * Returns true if both orders have the same order number.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getOrderNumber().equals(getOrderNumber())
                && otherOrder.getStatus().equals(getStatus());
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return orderNumber.equals(otherOrder.orderNumber)
                && medicineName.equals(otherOrder.medicineName)
                && person.equals(otherOrder.person)
                && orderStatus.equals(otherOrder.orderStatus);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(orderNumber, person, medicineName, orderStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("orderNumber", orderNumber)
                .add("person", person)
                .add("medicineName", medicineName)
                .add("status", orderStatus)
                .toString();
    }
}
