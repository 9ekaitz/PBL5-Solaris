package eus.solaris.solaris.service.multithreading.conversions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionNoneTest {
    @Test
    void test() {
        List<Double> targets = new ArrayList<>();
        targets.add(8347.0);
        targets.add(200.0);
        targets.add(0.0);
        targets.add(-1.0);

        List<Double> expected = new ArrayList<>();
        expected.add(8347.0);
        expected.add(200.0);
        expected.add(0.0);
        expected.add(-1.0);

        IConversion conversion = new ConversionNone();

        List<Double> actual = new ArrayList<>();
        for (Double target : targets) {
            actual.add(conversion.apply(target));
        }

        for (int i = 0; i < targets.size(); i++) {
            assertEquals(expected.get(i), actual.get(i), 0.001);
        }
    }
}
