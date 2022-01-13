package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

import eus.solaris.solaris.service.multithreading.conversions.ConversionNone;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToCO2;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToDollar;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToEur;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToMMInc;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToPounds;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToTempC;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToTempF;
import eus.solaris.solaris.service.multithreading.conversions.ConversionType;
import eus.solaris.solaris.service.multithreading.conversions.IConversion;

public class Processer {

    private static Map<Instant, Double> applyConversion(Map<Instant, Double> data, IConversion convsersion) {
        Map<Instant, Double> result = new TreeMap<>();
        data.forEach((k, v) -> {
            result.put(k, convsersion.apply(v));
        });
        return result;
    }

    // Better -> use Map<ConversionType, IConversion>
    public static Map<Instant, Double> process(Map<Instant, Double> data, ConversionType conversionT) {
        Map<Instant, Double> result;
        switch (conversionT) {
            case TO_EUR:
                result = applyConversion(data, new ConversionToEur());
                break;
            case TO_DOLLAR:
                result = applyConversion(data, new ConversionToDollar());
                break;
            case TO_POUNDS:
                result = applyConversion(data, new ConversionToPounds());
                break;
            case TO_AVOIDED_CO2:
                result = applyConversion(data, new ConversionToCO2());
                break;
            case TO_AVOIDED_TEMP_C:
                result = applyConversion(data, new ConversionToTempC());
                break;
            case TO_AVOIDED_TEMP_F:
                result = applyConversion(data, new ConversionToTempF());
                break;
            case TO_AVOIDED_MM_INCREASE:
                result = applyConversion(data, new ConversionToMMInc());
                break;
            case NONE:
                result = applyConversion(data, new ConversionNone());
            default:
                throw new IllegalArgumentException("Unknown conversion type: " + conversionT);
        }
        return result;
    }
}