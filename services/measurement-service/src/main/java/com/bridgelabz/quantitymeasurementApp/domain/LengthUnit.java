package com.bridgelabz.quantitymeasurementApp.domain;

public enum LengthUnit implements Unit {
    FEET(12.0), // 1 Foot = 12.0 base units (Inches)
    INCHES(1.0),
    YARD(36.0),    // 1 Yard = 3 Feet = 36 Inches
    MILE(63360.0); // 1 Mile = 1760 Yards = 5280 Feet = 63360 Inches
    private final double baseConversionFactor;

    LengthUnit(double baseConversionFactor) {
        this.baseConversionFactor = baseConversionFactor;
    }
    @Override
    public double getBaseConversionFactor() {
        return baseConversionFactor;
    }
}
