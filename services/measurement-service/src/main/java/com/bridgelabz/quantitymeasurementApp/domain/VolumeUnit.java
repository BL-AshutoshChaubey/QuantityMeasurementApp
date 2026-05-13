package com.bridgelabz.quantitymeasurementApp.domain;

public enum VolumeUnit implements Unit {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78);

    private final double baseConversionFactor;

    VolumeUnit(double baseConversionFactor) {
        this.baseConversionFactor = baseConversionFactor;
    }

    @Override
    public double getBaseConversionFactor() {
        return baseConversionFactor;
    }
}
