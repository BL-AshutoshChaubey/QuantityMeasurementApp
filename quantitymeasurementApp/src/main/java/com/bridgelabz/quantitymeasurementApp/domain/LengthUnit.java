package com.bridgelabz.quantitymeasurementApp.domain;

public enum LengthUnit implements Unit {
    FEET(12.0), // 1 Foot = 12.0 base units (Inches)
    INCHES(1.0);
    private final double baseConversionFactor;

    LengthUnit(double baseConversionFactor) {
        this.baseConversionFactor = baseConversionFactor;
    }
    @Override
    public double getBaseConversionFactor() {
        return baseConversionFactor;
    }
}
