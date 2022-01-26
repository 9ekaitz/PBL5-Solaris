package eus.solaris.solaris.service.multithreading;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import eus.solaris.solaris.service.multithreading.modes.Kind;

class FormatterJSONTest {

    String getOGJSON() {
        String OGJSON = "{\"data\":{\"datasets\":[{\"backgroundColor\":\"rgba(249, 170, 49, 0.7)\",\"data\":[1,2,3],\"label\":\"Test\"}],\"labels\":[\"1970-01-01 01:00\",\"1970-01-01 01:00\",\"1970-01-01 01:00\"]},\"type\":\"bar\"}";
        return OGJSON;
    }

    @Test
    void testJSONFormatterSimple() {
        Map<Instant, Double> data = new TreeMap<>();
        data.put(Instant.ofEpochMilli(1500), 1.0);
        data.put(Instant.ofEpochMilli(2000), 2.0);
        data.put(Instant.ofEpochMilli(30000), 3.0);

        FormatterJSON formatter = new FormatterJSON(data);
        formatter.setKind(Kind.BAR);
        formatter.setLabel("Test");

        String actual = formatter.getJSON().toString();
        String expected = getOGJSON();

        assertEquals(expected, actual);

    }

}
