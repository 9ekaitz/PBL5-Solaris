package eus.solaris.solaris.service.multithreading;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eus.solaris.solaris.domain.SolarPanel;

class InitialBufferTest {

    InitialBuffer initialBuffer;
    List<SolarPanel> solarPanels;

    @BeforeEach
    void setUp() {
        initialBuffer = new InitialBuffer(5);
        solarPanels = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            SolarPanel p = new SolarPanel();
            p.setId(Long.valueOf(i));
            solarPanels.add(p);
        }
        insertDataSingleThread();

    }

    void insertDataSingleThread() {
        initialBuffer.add(LocalDate.of(2019, 1, 1), solarPanels);
        initialBuffer.add(LocalDate.of(2021, 1, 1), solarPanels);
        initialBuffer.add(LocalDate.of(2020, 1, 1), solarPanels);
        initialBuffer.add(LocalDate.of(2018, 1, 1), solarPanels);
        initialBuffer.add(LocalDate.of(2022, 1, 1), solarPanels);
    }

    @Test
    void testInitialBufferSingleThread() {
        Map<LocalDate, List<SolarPanel>> m1 = new HashMap<>();
        m1.put(LocalDate.of(2019, 1, 1), solarPanels);
        assertEquals(m1, initialBuffer.get());

        assertFalse(initialBuffer.isEmpty());

        m1.clear();
        m1.put(LocalDate.of(2021, 1, 1), solarPanels);
        assertEquals(m1, initialBuffer.get());
        m1.clear();
        m1.put(LocalDate.of(2020, 1, 1), solarPanels);
        assertEquals(m1, initialBuffer.get());
        m1.clear();
        m1.put(LocalDate.of(2018, 1, 1), solarPanels);
        assertEquals(m1, initialBuffer.get());
        m1.clear();
        m1.put(LocalDate.of(2022, 1, 1), solarPanels);
        assertEquals(m1, initialBuffer.get());

        assertNull(initialBuffer.get());

    }

}
