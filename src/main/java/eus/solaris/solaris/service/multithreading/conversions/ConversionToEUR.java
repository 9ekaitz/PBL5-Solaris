package eus.solaris.solaris.service.multithreading.conversions;

public class ConversionToEUR implements IConversion {

    private static final Double AVERAGE_FACTOR = 0.2533 / 100;

    @Override
    public Double apply(Double t) {
        if (t > 0)
            return t * AVERAGE_FACTOR;
        else
            return 0.0;
    }
}