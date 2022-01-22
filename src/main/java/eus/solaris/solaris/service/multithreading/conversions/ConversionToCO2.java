package eus.solaris.solaris.service.multithreading.conversions;

public class ConversionToCO2 implements IConversion {

    private static final Double SPAIN_KWH_CO2_TON_FACTOR = 0.66738;

    @Override
    public Double apply(Double t) {
        if (t > 0)
            return t * SPAIN_KWH_CO2_TON_FACTOR;
        else
            return 0.0;
    }

}
