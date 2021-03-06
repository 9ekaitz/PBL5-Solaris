/*
 * This file was automatically generated by EvoSuite
 * Fri Jan 21 09:41:54 GMT 2022
 */

package eus.solaris.solaris.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class SolarPanelDataEntryTest {

    @Test
    @Timeout(4)
    void test00() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = 1534.492184;
        solarPanelDataEntry0.setVoltage(double0);
        Double double1 = solarPanelDataEntry0.getVoltage();
        assertEquals(1534.492184, (double) double1, 0.01);
    }

    @Test
    @Timeout(4)
    void test01() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = (-1515.7108);
        solarPanelDataEntry0.setVoltage(double0);
        Double double1 = solarPanelDataEntry0.getVoltage();
        assertEquals((-1515.7108), (double) double1, 0.01);
    }

    @Test
    @Timeout(4)
    void test02() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Instant instant0 = Instant.ofEpochSecond(1L);
        solarPanelDataEntry0.setTimestamp(instant0);
        Instant instant1 = solarPanelDataEntry0.getTimestamp();
        assertSame(instant1, instant0);
    }

    @Test
    @Timeout(4)
    void test03() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        SolarPanel solarPanel0 = new SolarPanel();
        solarPanelDataEntry0.setSolarPanel(solarPanel0);
        SolarPanel solarPanel1 = solarPanelDataEntry0.getSolarPanel();
        assertNull(solarPanel1.getVersion());
    }

    @Test
    @Timeout(4)
    void test04() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = 0.0;
        solarPanelDataEntry0.setPower(double0);
        Double double1 = solarPanelDataEntry0.getPower();
        assertEquals(0.0, (double) double1, 0.01);
    }

    @Test
    @Timeout(4)
    void test05() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = 1.0;
        solarPanelDataEntry0.setPower(double0);
        Double double1 = solarPanelDataEntry0.getPower();
        assertEquals(1.0, (double) double1, 0.01);
    }

    @Test
    @Timeout(4)
    void test06() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = (-1.0);
        solarPanelDataEntry0.setPower(double0);
        Double double1 = solarPanelDataEntry0.getPower();
        assertEquals((-1.0), (double) double1, 0.01);
    }

    @Test
    @Timeout(4)
    void test07() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Long long0 = 0L;
        solarPanelDataEntry0.setId(long0);
        Long long1 = solarPanelDataEntry0.getId();
        assertEquals(0L, (long) long1);
    }

    @Test
    @Timeout(4)
    void test08() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Long long0 = Long.valueOf(123L);
        solarPanelDataEntry0.setId(long0);
        Long long1 = solarPanelDataEntry0.getId();
        assertEquals(123L, (long) long1);
    }

    @Test
    @Timeout(4)
    void test09() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = 0.0;
        solarPanelDataEntry0.setCurrent(double0);
        Double double1 = solarPanelDataEntry0.getCurrent();
        assertEquals(0.0, (double) double1, 0.01);
    }

    @Test
    @Timeout(4)
    void test10() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = 123.0;
        solarPanelDataEntry0.setCurrent(double0);
        Double double1 = solarPanelDataEntry0.getCurrent();
        assertEquals(123.0, (double) double1, 0.01);
    }

    @Test
    @Timeout(4)
    void test11() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = (-1.0);
        solarPanelDataEntry0.setCurrent(double0);
        Double double1 = solarPanelDataEntry0.getCurrent();
        assertEquals((-1.0), (double) double1, 0.01);
    }

    @Test
    @Timeout(4)
    void test12() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = solarPanelDataEntry0.getPower();
        assertNull(double0);
    }

    @Test
    @Timeout(4)
    void test13() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        SolarPanel solarPanel0 = solarPanelDataEntry0.getSolarPanel();
        assertNull(solarPanel0);
    }

    @Test
    @Timeout(4)
    void test14() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Instant instant0 = solarPanelDataEntry0.getTimestamp();
        assertNull(instant0);
    }

    @Test
    @Timeout(4)
    void test15() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = solarPanelDataEntry0.getVoltage();
        assertNull(double0);
    }

    @Test
    @Timeout(4)
    void test16() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Long long0 = solarPanelDataEntry0.getId();
        assertNull(long0);
    }

    @Test
    @Timeout(4)
    void test17() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Long long0 = -2630L;
        solarPanelDataEntry0.setId(long0);
        Long long1 = solarPanelDataEntry0.getId();
        assertEquals((-2630L), (long) long1);
    }

    @Test
    @Timeout(4)
    void test18() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = solarPanelDataEntry0.getCurrent();
        assertNull(double0);
    }

    @Test
    @Timeout(4)
    void test19() throws Throwable {
        SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
        Double double0 = 0.0;
        solarPanelDataEntry0.setVoltage(double0);
        Double double1 = solarPanelDataEntry0.getVoltage();
        assertEquals(0.0, (double) double1, 0.01);
    }
}
