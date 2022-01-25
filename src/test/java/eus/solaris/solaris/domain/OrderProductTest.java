/*
 * This file was automatically generated by EvoSuite
 * Fri Jan 21 09:41:18 GMT 2022
 */

package eus.solaris.solaris.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class OrderProductTest {

    @Test
    @Timeout(4)
    void test00() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = 0;
        orderProduct0.setVersion(integer0);
        Integer integer1 = orderProduct0.getVersion();
        assertEquals(0, (int) integer1);
    }

    @Test
    @Timeout(4)
    void test01() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = 19;
        orderProduct0.setVersion(integer0);
        Integer integer1 = orderProduct0.getVersion();
        assertEquals(19, (int) integer1);
    }

    @Test
    @Timeout(4)
    void test02() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = -214;
        orderProduct0.setVersion(integer0);
        Integer integer1 = orderProduct0.getVersion();
        assertEquals((-214), (int) integer1);
    }

    @Test
    @Timeout(4)
    void test03() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Product product0 = new Product();
        orderProduct0.product = product0;
        Product product1 = orderProduct0.getProduct();
        assertEquals("Product(id=null, price=null, model=null, descriptions=null)", product1.toString());
    }

    @Test
    @Timeout(4)
    void test04() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Order order0 = new Order();
        orderProduct0.setOrder(order0);
        Order order1 = orderProduct0.getOrder();
        assertSame(order1, order0);
    }

    @Test
    @Timeout(4)
    void test05() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        OrderProductKey orderProductKey0 = new OrderProductKey();
        orderProduct0.id = orderProductKey0;
        OrderProductKey orderProductKey1 = orderProduct0.getId();
        assertSame(orderProductKey1, orderProductKey0);
    }

    @Test
    @Timeout(4)
    void test06() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = 0;
        orderProduct0.setAmount(integer0);
        Integer integer1 = orderProduct0.getAmount();
        assertEquals(0, (int) integer1);
    }

    @Test
    @Timeout(4)
    void test07() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = 43;
        orderProduct0.setAmount(integer0);
        Integer integer1 = orderProduct0.getAmount();
        assertEquals(43, (int) integer1);
    }

    @Test
    @Timeout(4)
    void test08() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = -4142;
        orderProduct0.setAmount(integer0);
        Integer integer1 = orderProduct0.getAmount();
        assertEquals((-4142), (int) integer1);
    }

    @Test
    @Timeout(4)
    void test09() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        boolean boolean0 = orderProduct0.canEqual(orderProduct0);
        assertTrue(boolean0);
    }

    @Test
    @Timeout(4)
    void test10() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        boolean boolean0 = orderProduct0.canEqual("|&G6ij`Q");
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test11() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = orderProduct0.getVersion();
        assertNull(integer0);
    }

    @Test
    @Timeout(4)
    void test12() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Order order0 = orderProduct0.getOrder();
        assertNull(order0);
    }

    @Test
    @Timeout(4)
    void test13() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Product product0 = orderProduct0.getProduct();
        assertNull(product0);
    }

    @Test
    @Timeout(4)
    void test14() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        OrderProductKey orderProductKey0 = orderProduct0.getId();
        assertNull(orderProductKey0);
    }

    @Test
    @Timeout(4)
    void test15() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = orderProduct0.getAmount();
        assertNull(integer0);
    }

    @Test
    @Timeout(4)
    void test16() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Product product0 = new Product();
        orderProduct0.product = product0;
        OrderProduct orderProduct1 = new OrderProduct();
        assertNotEquals(orderProduct1, (Object) orderProduct0);

        orderProduct1.product = orderProduct0.product;
        boolean boolean0 = orderProduct0.equals(orderProduct1);
        assertEquals(orderProduct1, (Object) orderProduct0);
        assertTrue(boolean0);
    }

    @Test
    @Timeout(4)
    void test17() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Product product0 = new Product();
        orderProduct0.product = product0;
        OrderProduct orderProduct1 = new OrderProduct();
        boolean boolean0 = orderProduct0.equals(orderProduct1);
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test18() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Order order0 = new Order();
        orderProduct0.setOrder(order0);
        OrderProduct orderProduct1 = new OrderProduct();
        assertNotEquals(orderProduct1, ((Object) orderProduct0));

        orderProduct1.order = order0;
        boolean boolean0 = orderProduct1.equals(orderProduct0);
        assertEquals(orderProduct1, ((Object) orderProduct0));
        assertTrue(boolean0);
    }

    @Test
    @Timeout(4)
    void test19() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        OrderProduct orderProduct1 = new OrderProduct();
        assertEquals(orderProduct1, (Object) orderProduct0);

        Order order0 = new Order();
        orderProduct1.order = order0;
        boolean boolean0 = orderProduct0.equals(orderProduct1);
        assertNotEquals(orderProduct1, ((Object) orderProduct0));
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test20() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Order order0 = new Order();
        orderProduct0.setOrder(order0);
        OrderProduct orderProduct1 = new OrderProduct();
        boolean boolean0 = orderProduct0.equals(orderProduct1);
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test21() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        OrderProduct orderProduct1 = new OrderProduct();
        OrderProductKey orderProductKey0 = new OrderProductKey();
        orderProduct0.setId(orderProductKey0);
        assertNotEquals(orderProduct0, (Object) orderProduct1);

        orderProduct1.id = orderProductKey0;
        boolean boolean0 = orderProduct1.equals(orderProduct0);
        assertEquals(orderProduct0, ((Object) orderProduct1));
        assertTrue(boolean0);
    }

    @Test
    @Timeout(4)
    void test22() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        OrderProduct orderProduct1 = new OrderProduct();
        assertEquals(orderProduct1, ((Object) orderProduct0));

        OrderProductKey orderProductKey0 = new OrderProductKey();
        orderProduct1.id = orderProductKey0;
        boolean boolean0 = orderProduct1.equals(orderProduct0);
        assertNotEquals(orderProduct1, ((Object) orderProduct0));
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test23() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = -675;
        orderProduct0.setAmount(integer0);
        OrderProduct orderProduct1 = new OrderProduct();
        assertNotEquals(orderProduct1, ((Object) orderProduct0));

        orderProduct1.setAmount(integer0);
        boolean boolean0 = orderProduct0.equals(orderProduct1);
        assertEquals(orderProduct1, (Object) orderProduct0);
        assertTrue(boolean0);
    }

    @Test
    @Timeout(4)
    void test24() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = -675;
        orderProduct0.setAmount(integer0);
        OrderProduct orderProduct1 = new OrderProduct();
        boolean boolean0 = orderProduct1.equals(orderProduct0);
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test25() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        boolean boolean0 = orderProduct0.equals("eus.solaris.solaris.domain.User");
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test26() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        boolean boolean0 = orderProduct0.equals(orderProduct0);
        assertTrue(boolean0);
    }

    @Test
    @Timeout(4)
    void test27() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer hash1 = orderProduct0.hashCode();
        Product product0 = new Product();
        orderProduct0.product = product0;
        Integer hash2 = orderProduct0.hashCode();
        assertNotEquals(hash1, hash2);
    }

    @Test
    @Timeout(4)
    void test28() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer hash1 = orderProduct0.hashCode();
        OrderProductKey orderProductKey0 = new OrderProductKey();
        orderProduct0.id = orderProductKey0;
        Integer hash2 = orderProduct0.hashCode();
        assertNotEquals(hash1, hash2);
    }

    @Test
    @Timeout(4)
    void test29() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer hash1 = orderProduct0.hashCode();
        Integer integer0 = 1;
        orderProduct0.setAmount(integer0);
        Integer hash2 = orderProduct0.hashCode();
        assertNotEquals(hash1, hash2);
    }

    @Test
    @Timeout(4)
    void test30() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer integer0 = -675;
        orderProduct0.setAmount(integer0);
        OrderProduct orderProduct1 = new OrderProduct();
        boolean boolean0 = orderProduct0.equals(orderProduct1);
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test31() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Integer hash1 = orderProduct0.hashCode();
        Order order0 = new Order();
        orderProduct0.setOrder(order0);
        orderProduct0.hashCode();
        Integer hash2 = orderProduct0.hashCode();
        assertNotEquals(hash1, hash2);
    }

    @Test
    @Timeout(4)
    void test32() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        Product product0 = new Product();
        orderProduct0.setProduct(product0);
        OrderProduct orderProduct1 = new OrderProduct();
        boolean boolean0 = orderProduct1.equals(orderProduct0);
        assertEquals(
            "OrderProduct(id=null, order=null, product=Product(id=null, price=null, material=null, brand=null, color=null, size=null, model=null, descriptions=null), amount=null, price=null, version=null)",
                orderProduct0.toString());
        assertFalse(boolean0);
    }

    @Test
    @Timeout(4)
    void test33() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        String string0 = orderProduct0.toString();
        assertEquals("OrderProduct(id=null, order=null, product=null, amount=null, price=null, version=null)", string0);
    }

    @Test
    @Timeout(4)
    void test34() throws Throwable {
        OrderProduct orderProduct0 = new OrderProduct();
        OrderProductKey orderProductKey0 = new OrderProductKey();
        orderProduct0.setId(orderProductKey0);
        OrderProduct orderProduct1 = new OrderProduct();
        boolean boolean0 = orderProduct1.equals(orderProduct0);
        assertNotEquals(orderProduct0, ((Object) orderProduct1));
        assertFalse(boolean0);
    }
}
