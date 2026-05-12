package com.bridgelabz.quantitymeasurementApp.domain;

import java.util.Objects;

public class Quantity <T extends Unit> {
    private final double value;
    private final T unit;

    public Quantity(double value, T unit) {
        this.value = value;
        this.unit = unit;
    }
    public Quantity<T> convertTo(T targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target Unit cannot be null");
        }

        double baseValue = UnitConverter.convertToBaseValue(this.value, this.unit);
        double convertedValue = UnitConverter.convertFromBaseValue(baseValue, targetUnit);
        return new Quantity<>(convertedValue, targetUnit);
    }
    private Quantity<T> calculate(double operandBaseValue, T targetUnit, Operation operation) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target Unit cannot be null");
        }

        if (!this.unit.supportsArithmetic() || !targetUnit.supportsArithmetic()) {
            throw new UnsupportedOperationException("Arithmetic operations are not supported for this unit category (e.g., relative temperatures).");
        }

        double thisBaseValue = UnitConverter.convertToBaseValue(this.value, this.unit);
        // Execute the Lambda expression passed via the Enum
        double resultBaseValue = operation.apply(thisBaseValue, operandBaseValue);
        double finalValue = UnitConverter.convertFromBaseValue(resultBaseValue, targetUnit);
        return new Quantity<>(finalValue, targetUnit);
    }

    public Quantity<T> add(Quantity<T> other, T targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Quantity and Target Unit cannot be null");
        }
        double otherBase = UnitConverter.convertToBaseValue(other.value, other.unit);
        return this.calculate(otherBase, targetUnit, Operation.ADD);

    }
    public Quantity<T> add(Quantity<T> other) {

        return this.add(other, this.unit);
    }



    // --- Subtraction Operations ---

    public Quantity<T> subtract(Quantity<T> other, T targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Quantity and Target Unit cannot be null");
        }
        double otherBaseValue = UnitConverter.convertToBaseValue(other.value, other.unit);
        return this.calculate(otherBaseValue,targetUnit,Operation.SUBTRACT);
    }

    public Quantity<T> subtract(Quantity<T> other) {
        return this.subtract(other, this.unit);
    }

    // --- Division Operations ---

    public Quantity<T> divide(double divisor, T targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target Unit cannot be null");
        }
        return this.calculate(divisor,targetUnit,Operation.DIVIDE);
    }

    public Quantity<T> divide(double divisor) {
        return this.divide(divisor, this.unit);
    }
    // Overriding equals to satisfy UC1: Object Equality, Null, and Type Checking
    @Override
    public boolean equals(Object object) {
        // 1. Reference Check
        if (this == object) return true;
        // 2. Null and Type Check
        if (object == null || getClass() != object.getClass()) return false;

        // 4. Type Erasure and Wildcards (<?>): At runtime, generic types are erased.
        // We use a wildcard to safely cast the object.
        Quantity<?> quantity = (Quantity<?>) object;

        // ENFORCE TYPE SAFETY: If they are different categories, they cannot be equal!// checking at runtime
        if (!this.unit.getClass().equals(quantity.unit.getClass())) {
            return false;
        }
        // 3. Floating-point comparison using Double.compare
        double thisBase = UnitConverter.convertToBaseValue(this.value, this.unit);
        double otherBase = UnitConverter.convertToBaseValue(quantity.value, quantity.unit);

        return Double.compare(thisBase, otherBase) == 0;
    }
    public double getValue(){
        return this.value;
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
