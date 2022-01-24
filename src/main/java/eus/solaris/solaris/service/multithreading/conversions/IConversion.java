package eus.solaris.solaris.service.multithreading.conversions;

import java.util.function.Function;

public interface IConversion extends Function<Double, Double> {
    @Override
    public Double apply(Double t);
}
