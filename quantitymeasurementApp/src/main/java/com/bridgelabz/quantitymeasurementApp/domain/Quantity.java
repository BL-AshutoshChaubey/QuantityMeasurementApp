package com.bridgelabz.quantitymeasurementApp.domain;

import java.util.Objects;

public class Quantity {
    private final double value;
    private final MeasurementUnit unit;

    public Quantity(double value, MeasurementUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    // Overriding equals to satisfy UC1: Object Equality, Null, and Type Checking
    @Override
    public boolean equals(Object o) {
        // 1. Reference Check
        if (this == o) return true;
        // 2. Null and Type Check
        if (o == null || getClass() != o.getClass()) return false;

        Quantity quantity = (Quantity) o;

        // 3. Floating-point comparison using Double.compare
        double thisBaseValue = this.value * this.unit.getBaseConversionFactor();
        double otherBaseValue = quantity.value * quantity.unit.getBaseConversionFactor();

        return Double.compare(thisBaseValue, otherBaseValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }
}
