package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.order.Order;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {
    public static final int ORDER_NUMBER_FIRST_ORDER = 1;

    public static final int ORDER_NUMBER_SECOND_ORDER = 1234;

    public static final Order ALICE_PANADOL_ORDER = new Order(ORDER_NUMBER_FIRST_ORDER,
            TypicalPersons.ALICE, "Panadol");

    public static final Order BENSON_PANADOL_ORDER = new Order(ORDER_NUMBER_SECOND_ORDER,
            TypicalPersons.BENSON, "Panadol");

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ALICE_PANADOL_ORDER, BENSON_PANADOL_ORDER));
    }
}
