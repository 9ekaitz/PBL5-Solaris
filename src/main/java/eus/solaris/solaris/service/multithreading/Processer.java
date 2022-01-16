package eus.solaris.solaris.service.multithreading;

import java.time.Instant;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import eus.solaris.solaris.service.multithreading.conversions.ConversionNone;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToCO2;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToEUR;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToGBP;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToNMInc;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToTempC;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToTempF;
import eus.solaris.solaris.service.multithreading.conversions.ConversionToUSD;
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
                result = applyConversion(data, new ConversionToEUR());
                break;
            case TO_DOLLAR:
                result = applyConversion(data, new ConversionToUSD());
                break;
            case TO_POUNDS:
                result = applyConversion(data, new ConversionToGBP());
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
                result = applyConversion(data, new ConversionToNMInc());
                break;
            case NONE:
                result = applyConversion(data, new ConversionNone());
                break;
            default:
                throw new IllegalArgumentException("Unknown conversion type: " + conversionT);
        }
        return result;
    }

    public static Map<Instant, Double> groupPanels(Map<Instant, Double> dataMap) {
        Map<Instant, Double> groupedDataMap = new TreeMap<>();

        for (Entry<Instant, Double> entry : dataMap.entrySet()) {
            Instant instant = entry.getKey();
            Double value = entry.getValue();

            if (groupedDataMap.containsKey(instant)) {
                groupedDataMap.put(instant, groupedDataMap.get(instant) + value);
            } else {
                groupedDataMap.put(instant, value);
            }
        }

        return groupedDataMap;
    }
}