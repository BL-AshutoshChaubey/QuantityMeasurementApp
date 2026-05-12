package com.bridgelabz.quantitymeasurementApp.domain;

public interface Unit {
    double getBaseConversionFactor();
    // default methode for linear conversion.
    default double convertToBase(double value) {
        return value * getBaseConversionFactor();
    }

    default double convertFromBase(double baseValue) {
        return baseValue / getBaseConversionFactor();
    }

    // Capability-Based Design: Determines if the unit allows arithmetic
    default boolean supportsArithmetic() {
        return true;
    }
}
