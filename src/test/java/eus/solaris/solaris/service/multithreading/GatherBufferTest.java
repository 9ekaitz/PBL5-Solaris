package eus.solaris.solaris.service.multithreading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class GatherBufferTest {
    GatherBuffer gatherBuffer;
    Map<Instant, Double> valMap;

    @BeforeEach
    void setUp() {
        gatherBuffer = new GatherBuffer(5,
                new ThreadController(6, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 1)));
        valMap = new HashMap<>();
        valMap.put(Instant.ofEpochMilli(556677), 1.0);
        valMap.put(Instant.ofEpochMilli(556678), 2.0);
        valMap.put(Instant.ofEpochMilli(556679), 3.0);
        valMap.put(Instant.ofEpochMilli(556680), 4.0);
        insertDataSingleThread();

    }

    void insertDataSingleThread() {
        Map<LocalDate, Map<Instant, Double>> m1 = new HashMap<>();
        m1.put(LocalDate.of(2019, 1, 1), valMap);
        gatherBuffer.add(m1);

        m1 = new HashMap<>();
        m1.put(LocalDate.of(2013, 8, 2), valMap);
        gatherBuffer.add(m1);

        m1 = new HashMap<>();
        m1.put(LocalDate.of(2202, 1, 1), valMap);
        gatherBuffer.add(m1);

        m1 = new HashMap<>();
        m1.put(LocalDate.of(2001, 6, 1), valMap);
        gatherBuffer.add(m1);

        m1 = new HashMap<>();
        m1.put(LocalDate.of(2020, 1, 31), valMap);
        gatherBuffer.add(m1);
    }

    @Test
    @Timeout(value = 3)
    void testGatherBufferSingleThread() {
        Map<LocalDate, Map<Instant, Double>> m1 = new HashMap<>();
        m1.put(LocalDate.of(2019, 1, 1), valMap);
        assertEquals(m1, gatherBuffer.get());
        m1.clear();

        m1.put(LocalDate.of(2013, 8, 2), valMap);
        assertEquals(m1, gatherBuffer.get());
        m1.clear();

        assertFalse(gatherBuffer.isEmpty());

        m1.put(LocalDate.of(2202, 1, 1), valMap);
        assertEquals(m1, gatherBuffer.get());
        m1.clear();

        m1.put(LocalDate.of(2001, 6, 1), valMap);
        assertEquals(m1, gatherBuffer.get());
        m1.clear();

        m1.put(LocalDate.of(2020, 1, 31), valMap);
        assertEquals(m1, gatherBuffer.get());
        m1.clear();

        assertNull(gatherBuffer.get());

    }
}
