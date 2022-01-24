package eus.solaris.solaris.service.multithreading.conversions;

public class ConversionToTempF implements IConversion {
    private static final Double KWH_TO_AVOIDED_NANO_FAHRENHEIT_FACTOR = 0.00000002592;

    @Override
    public Double apply(Double t) {
        if (t > 0)
            return t * KWH_TO_AVOIDED_NANO_FAHRENHEIT_FACTOR;
        else
            return 0.0;
    }
}
