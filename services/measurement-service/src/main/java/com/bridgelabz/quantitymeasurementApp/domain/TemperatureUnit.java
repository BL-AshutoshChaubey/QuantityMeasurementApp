package com.bridgelabz.quantitymeasurementApp.domain;

import java.util.function.Function;

public enum TemperatureUnit implements Unit {
    // Functional Interface & Lambda Expressions for Non-Linear Conversions
    CELSIUS(val -> val, val -> val),
    FAHRENHEIT(val -> (val - 32) * 5.0 / 9.0, val -> (val * 9.0 / 5.0) + 32);

    private final Function<Double, Double> toBase;
    private final Function<Double, Double> fromBase;

    TemperatureUnit(Function<Double, Double> toBase, Function<Double, Double> fromBase) {
        this.toBase = toBase;
        this.fromBase = fromBase;
    }

    @Override
    public double getBaseConversionFactor() {
        return 1.0; // Unused for temperature, but required by the interface
    }

    // Method Overriding for Behavioural Customisation
    @Override
    public double convertToBase(double value) {
        return toBase.apply(value);
    }

    @Override
    public double convertFromBase(double baseValue) {
        return fromBase.apply(baseValue);
    }

    // Absolute vs. Relative Temperatures constraint
    @Override
    public boolean supportsArithmetic() {
        return false;
    }
}
