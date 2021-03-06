/*
 * This file was automatically generated by EvoSuite
 * Fri Jan 21 09:43:38 GMT 2022
 */

package eus.solaris.solaris.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class OrderTest {

    @Test
    @Timeout(4)
    void test00() throws Throwable {
        Order order0 = new Order();
        Integer integer0 = 0;
        order0.setVersion(integer0);
        Integer integer1 = order0.getVersion();
        assertEquals(0, (int) integer1);
    }

    @Test
    @Timeout(4)
    void test01() throws Throwable {
        Order order0 = new Order();
        Integer integer0 = 3551;
        order0.setVersion(integer0);
        Integer integer1 = order0.getVersion();
        assertEquals(3551, (int) integer1);
    }

    @Test
    @Timeout(4)
    void test02() throws Throwable {
        Order order0 = new Order();
        Integer integer0 = -979;
        order0.setVersion(integer0);
        Integer integer1 = order0.getVersion();
        assertEquals((-979), (int) integer1);
    }

    @Test
    @Timeout(4)
    void test03() throws Throwable {
        Order order0 = new Order();
        LinkedHashSet<OrderProduct> linkedHashSet0 = new LinkedHashSet<OrderProduct>();
        order0.products = (Set<OrderProduct>) linkedHashSet0;
        Set<OrderProduct> set0 = order0.getProducts();
        assertTrue(set0.isEmpty());
    }

    @Test
    @Timeout(4)
    void test04() throws Throwable {
        Order order0 = new Order();
        LinkedHashSet<OrderProduct> linkedHashSet0 = new LinkedHashSet<OrderProduct>();
        linkedHashSet0.add((OrderProduct) null);
        order0.products = (Set<OrderProduct>) linkedHashSet0;
        Set<OrderProduct> set0 = order0.getProducts();
        assertFalse(set0.isEmpty());
    }

    @Test
    @Timeout(4)
    void test05() throws Throwable {
        Order order0 = new Order();
        Boolean boolean0 = Boolean.valueOf("nqd>X2;= y~yb7Zv8[o");
        LinkedList<Task> linkedList0 = new LinkedList<Task>();
        Installation installation0 = new Installation((Long) null, "nqd>X2;= y~yb7Zv8[o", "GV:_f2j] ?S.M", boolean0,
                (Order) null, (User) null, linkedList0, null, (Integer) null);
        order0.setInstallation(installation0);
        Installation installation1 = order0.getInstallation();
        assertEquals(
                "Installation(id=null, name=nqd>X2;= y~yb7Zv8[o, description=GV:_f2j] ?S.M, completed=false, order=null, installer=null, tasks=[], signature=null, version=null)",
                installation1.toString());
    }

    @Test
    @Timeout(4)
    void test06() throws Throwable {
        Order order0 = new Order();
        Long long0 = Long.getLong("leE", 6L);
        order0.setId(long0);
        Long long1 = order0.getId();
        assertEquals(6L, (long) long1);
    }

    @Test
    @Timeout(4)
    void test07() throws Throwable {
        Order order0 = new Order();
        Long long0 = Long.getLong("leE", (-1L));
        order0.setId(long0);
        Long long1 = order0.getId();
        assertEquals((-1L), (long) long1);
    }

    @Test
    @Timeout(4)
    void test08() throws Throwable {
        Order order0 = new Order();
        LocalDate mockDate0 = LocalDate.of(1, 1, 1);
        order0.setCreationTime(Date.from(mockDate0.atStartOfDay(ZoneId.of("Europe/Madrid")).toInstant()));
        Date date0 = order0.getCreationTime();
        assertEquals("Mon Jan 03 00:14:44 UTC 1", date0.toString());
    }

    @Test
    @Timeout(4)
    void test09() throws Throwable {
        Order order0 = new Order();
        Country country0 = new Country();
        Province province0 = new Province();
        Boolean boolean0 = Boolean.valueOf(true);
        Integer integer0 = 0;
        Address address0 = new Address((Long) null, country0, province0, "", "KCQvG=gg*^+MJ!!X\"",
                "eus.solaris.solaris.domain.OrderProductKey", "", (User) null, false, boolean0, integer0);
        order0.setAddress(address0);
        Address address1 = order0.getAddress();
        assertEquals("KCQvG=gg*^+MJ!!X\"", address1.getPostcode());
    }

    @Test
    @Timeout(4)
    void test10() throws Throwable {
        Order order0 = new Order();
        Installation installation0 = order0.getInstallation();
        assertNull(installation0);
    }

    @Test
    @Timeout(4)
    void test11() throws Throwable {
        Order order0 = new Order();
        Long long0 = Long.valueOf(0L);
        order0.setId(long0);
        Long long1 = order0.getId();
        assertEquals(0L, (long) long1);
    }

    @Test
    @Timeout(4)
    void test12() throws Throwable {
        Order order0 = new Order();
        Date date0 = order0.getCreationTime();
        assertNull(date0);
    }

    @Test
    @Timeout(4)
    void test13() throws Throwable {
        Order order0 = new Order();
        Long long0 = order0.getId();
        assertNull(long0);
    }

    @Test
    @Timeout(4)
    void test14() throws Throwable {
        Order order0 = new Order();
        Set<OrderProduct> set0 = order0.getProducts();
        assertNull(set0);
    }

    @Test
    @Timeout(4)
    void test15() throws Throwable {
        Order order0 = new Order();
        User user0 = order0.getOwner();
        assertNull(user0);
    }

    @Test
    @Timeout(4)
    void test16() throws Throwable {
        Order order0 = new Order();
        LinkedHashSet<OrderProduct> linkedHashSet0 = new LinkedHashSet<OrderProduct>();
        order0.setProducts(linkedHashSet0);
        assertEquals(0, linkedHashSet0.size());
    }

    @Test
    @Timeout(4)
    void test17() throws Throwable {
        Order order0 = new Order();
        Address address0 = order0.getAddress();
        assertNull(address0);
    }

    @Test
    @Timeout(4)
    void test18() throws Throwable {
        Order order0 = new Order();
        Address address0 = new Address();
        order0.setAddress(address0);
        Address address1 = order0.getAddress();
        assertNull(address1.getId());
    }

    @Test
    @Timeout(4)
    void test19() throws Throwable {
        Order order0 = new Order();
        Integer integer0 = order0.getVersion();
        assertNull(integer0);
    }

    @Test
    @Timeout(4)
    void test20() throws Throwable {
        Order order0 = new Order();
        User user0 = new User();
        order0.setOwner(user0);
        User user1 = order0.getOwner();
        assertNull(user1.getName());
    }
}
