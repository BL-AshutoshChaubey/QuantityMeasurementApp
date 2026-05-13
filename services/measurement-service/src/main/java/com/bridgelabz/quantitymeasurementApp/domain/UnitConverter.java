package com.bridgelabz.quantitymeasurementApp.domain;

public class UnitConverter {
    //Converts a given value and unit into its base unit value.
    public static double convertToBaseValue(double value, Unit unit) {
        return unit.convertToBase(value);
    }
    //Converts a base value into the target unit's value.
    public static double convertFromBaseValue(double baseValue, Unit targetUnit) {
        return targetUnit.convertFromBase(baseValue);
    }
}
