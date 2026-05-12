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
        // ENFORCE TYPE SAFETY: Cannot convert Length to Weight
        if (!this.unit.getClass().equals(targetUnit.getClass())) {
            throw new IllegalArgumentException("Cannot convert across different measurement categories");
        }


        double baseValue = UnitConverter.convertToBaseValue(this.value, this.unit);
        double convertedValue = UnitConverter.convertFromBaseValue(baseValue, targetUnit);
        return new Quantity(convertedValue, targetUnit);
    }


    public Quantity add(Quantity other, Unit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Quantity and Target Unit cannot be null");
        }
        // ENFORCE TYPE SAFETY: Cannot add Length to Weight
        if (!this.unit.getClass().equals(other.unit.getClass()) || !this.unit.getClass().equals(targetUnit.getClass())) {
            throw new IllegalArgumentException("Cannot perform arithmetic across different measurement categories");
        }
        double thisBaseValue = UnitConverter.convertToBaseValue(this.value, this.unit);
        double otherBaseValue = UnitConverter.convertToBaseValue(other.value, other.unit);

        double sumInBaseUnits = thisBaseValue + otherBaseValue;

        // Delegating target conversion
        double finalValue = UnitConverter.convertFromBaseValue(sumInBaseUnits, targetUnit);


        return new Quantity(finalValue, targetUnit);
    }
    public Quantity add(Quantity other) {

        return this.add(other, this.unit);
    }

    // Overriding equals to satisfy UC1: Object Equality, Null, and Type Checking
    @Override
    public boolean equals(Object object) {
        // 1. Reference Check
        if (this == object) return true;
        // 2. Null and Type Check
        if (object == null || getClass() != object.getClass()) return false;

        Quantity quantity = (Quantity) object;
        // ENFORCE TYPE SAFETY: If they are different categories, they cannot be equal!
        if (!this.unit.getClass().equals(quantity.unit.getClass())) {
            return false;
        }
        // 3. Floating-point comparison using Double.compare
        double thisBase = UnitConverter.convertToBaseValue(this.value, this.unit);
        double otherBase = UnitConverter.convertToBaseValue(quantity.value, quantity.unit);

        return Double.compare(thisBase, otherBase) == 0;
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
