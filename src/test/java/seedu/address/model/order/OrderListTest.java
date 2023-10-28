package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.CARL_PANADOL_ORDER;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.order.exceptions.DuplicateOrderException;


class OrderListTest {
    private List<Order> list;
    private OrderList orderList;

    private Order order;
    @BeforeEach
    public void init() {
        order = new Order(new OrderNumber("1"), CARL, "panadol");
        list = new ArrayList<>();
        list.add(order);
        orderList = new OrderList();
        orderList.add(order);
    }

    @Test
    public void setOrders() {
        OrderList newOrderList = new OrderList();
        newOrderList.setOrders(orderList);
        assertEquals(orderList, newOrderList);
    }

    @Test
    public void equals() {
        assertTrue(orderList.equals(orderList));

        assertFalse(orderList.equals(order));
    }

    @Test
    public void add_duplicateOrder_throwsDuplicateOrderException() {

        assertThrows(DuplicateOrderException.class, () -> orderList.add(CARL_PANADOL_ORDER));
    }

    @Test
    public void contains_orderInList_returnsTrue() {
        assertTrue(orderList.contains(CARL_PANADOL_ORDER));
    }

    @Test
    public void contains_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderList.contains(null));
    }


}
