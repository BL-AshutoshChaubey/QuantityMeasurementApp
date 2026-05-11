package com.bridgelabz.quantitymeasurementApp.domain;

public enum MeasurementUnit {
    FEET(12.0); // 1 Foot = 12.0 base units (Inches)

    private final double baseConversionFactor;

    MeasurementUnit(double baseConversionFactor) {
        this.baseConversionFactor = baseConversionFactor;
    }

    public double getBaseConversionFactor() {
        return baseConversionFactor;
    }
}
