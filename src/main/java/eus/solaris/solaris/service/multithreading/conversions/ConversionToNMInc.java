package eus.solaris.solaris.service.multithreading.conversions;

public class ConversionToNMInc implements IConversion {

    private static final Double KWH_TO_NM_INC_FACTOR = 0.0000000288;

    @Override
    public Double apply(Double t) {
        if (t > 0)
            return t * KWH_TO_NM_INC_FACTOR;
        else
            return 0.0;
    }

}
