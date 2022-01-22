package eus.solaris.solaris.service.multithreading.conversions;

public class ConversionToTempC implements IConversion {

    private static final Double KWH_TO_AVOIDED_NANO_CENTIGRADE_FACTOR = 0.0000000144;

    @Override
    public Double apply(Double t) {
        if (t > 0)
            return t * KWH_TO_AVOIDED_NANO_CENTIGRADE_FACTOR;
        else
            return 0.0;
    }

}
