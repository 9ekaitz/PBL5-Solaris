package eus.solaris.solaris.service.multithreading;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataBufferTest {

    DataBuffer dataBuffer;

    @BeforeEach
    void setUp() {
        dataBuffer = new DataBuffer();
    }

    @Test
    void testBufferSingleThread() {
        insertDataSingleThread();
        Map<Instant, Double> actual = dataBuffer.getData();
        Map<Instant, Double> expected = getExpectedData();
        assertEquals(expected, actual);
    }

    void insertDataSingleThread() {
        dataBuffer.put(LocalDate.of(2019, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 256.2);
        dataBuffer.put(LocalDate.of(1975, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 256.2);
        dataBuffer.put(LocalDate.of(2256, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 334242.2);
        dataBuffer.put(LocalDate.of(2300, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 2253.211111);
        dataBuffer.put(LocalDate.of(2015, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 0.22231);
    }

    Map<Instant, Double> getExpectedData() {
        Map<Instant, Double> expected = new TreeMap<>();
        expected.put(LocalDate.of(2019, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 256.2);
        expected.put(LocalDate.of(1975, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 256.2);
        expected.put(LocalDate.of(2256, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 334242.2);
        expected.put(LocalDate.of(2300, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 2253.211111);
        expected.put(LocalDate.of(2015, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant(), 0.22231);
        return expected;
    }
}
