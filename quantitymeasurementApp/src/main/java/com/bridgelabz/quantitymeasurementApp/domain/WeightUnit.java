package com.bridgelabz.quantitymeasurementApp.domain;

public enum WeightUnit implements Unit{
    GRAM(1.0),
    KILOGRAM(1000.0),
    TONNE(1000000.0);

    private final double baseConversionFactor;

    WeightUnit(double baseConversionFactor) {
        this.baseConversionFactor = baseConversionFactor;
    }

    @Override
    public double getBaseConversionFactor() {
        return baseConversionFactor;
    }
}
