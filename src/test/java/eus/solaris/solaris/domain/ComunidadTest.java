/*
 * This file was automatically generated by EvoSuite
 * Fri Jan 21 09:42:21 GMT 2022
 */

package eus.solaris.solaris.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class ComunidadTest {

    @Test()
    @Timeout(4)
    void test0() throws Throwable {
        Comunidad comunidad0 = new Comunidad();
        Long long0 = 0L;
        comunidad0.setId(long0);
        Long long1 = comunidad0.getId();
        assertEquals(0L, (long) long1);
    }

    @Test()
    @Timeout(4)
    void test1() throws Throwable {
        Comunidad comunidad0 = new Comunidad();
        Long long0 = 944L;
        comunidad0.setId(long0);
        Long long1 = comunidad0.getId();
        assertEquals(944L, (long) long1);
    }

    @Test()
    @Timeout(4)
    void test2() throws Throwable {
        Comunidad comunidad0 = new Comunidad();
        comunidad0.setComunity("Wd\"_aDyD/tY");
        String string0 = comunidad0.getComunity();
        assertEquals("Wd\"_aDyD/tY", string0);
    }

    @Test()
    @Timeout(4)
    void test3() throws Throwable {
        Comunidad comunidad0 = new Comunidad();
        comunidad0.setComunity("");
        String string0 = comunidad0.getComunity();
        assertEquals("", string0);
    }

    @Test()
    @Timeout(4)
    void test4() throws Throwable {
        Comunidad comunidad0 = new Comunidad();
        String string0 = comunidad0.getComunity();
        assertNull(string0);
    }

    @Test()
    @Timeout(4)
    void test5() throws Throwable {
        Comunidad comunidad0 = new Comunidad();
        Long long0 = -2330L;
        comunidad0.setId(long0);
        Long long1 = comunidad0.getId();
        assertEquals((-2330L), (long) long1);
    }

    @Test()
    @Timeout(4)
    void test6() throws Throwable {
        Comunidad comunidad0 = new Comunidad();
        Long long0 = comunidad0.getId();
        assertNull(long0);
    }
}
