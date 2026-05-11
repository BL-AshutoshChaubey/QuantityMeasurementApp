package com.bridgelabz.quantitymeasurementApp.domain;

import java.util.Objects;

public class Quantity {
    private final double value;
    private final Unit unit;

    public Quantity(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }
    public Quantity convertTo(Unit targetUnit) {
        // Calculate the new value: (Current Base Value) / (Target Unit Base Factor)
        double convertedValue = this.getBaseValue() / targetUnit.getBaseConversionFactor();

        // Return a NEW object to maintain Immutability
        return new Quantity(convertedValue, targetUnit);
    }

    private double getBaseValue() {
        return this.value * this.unit.getBaseConversionFactor();
    }
    public Quantity add(Quantity other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot add a null quantity");
        }

        // Normalize both to the base value (e.g., Inches) and add them
        double sumInBaseUnits = this.getBaseValue() + other.getBaseValue();

        // Convert the total base value back into the unit of the calling object
        double finalValue = sumInBaseUnits / this.unit.getBaseConversionFactor();

        // Return a brand new object to maintain immutability
        return new Quantity(finalValue, this.unit);
    }

    // Overriding equals to satisfy UC1: Object Equality, Null, and Type Checking
    @Override
    public boolean equals(Object object) {
        // 1. Reference Check
        if (this == object) return true;
        // 2. Null and Type Check
        if (object == null || getClass() != object.getClass()) return false;

        Quantity quantity = (Quantity) object;

        // 3. Floating-point comparison using Double.compare
        return Double.compare(this.getBaseValue(),quantity.getBaseValue()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
    @Override
    public String toString() {
        return value + " " + unit;
    }
}
