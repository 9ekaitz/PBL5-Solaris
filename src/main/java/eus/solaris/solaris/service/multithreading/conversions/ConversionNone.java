package eus.solaris.solaris.service.multithreading.conversions;

public class ConversionNone implements IConversion {

    @Override
    public Double apply(Double value) {
        return value;
    }
}
