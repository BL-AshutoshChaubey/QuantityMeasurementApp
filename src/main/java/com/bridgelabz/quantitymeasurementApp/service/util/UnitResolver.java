package com.bridgelabz.quantitymeasurementApp.service.util;

import com.bridgelabz.quantitymeasurementApp.domain.*;

public class UnitResolver {

    /**
     * Resolves a string name to its corresponding Unit implementation.
     * Scales easily without modifying existing logic when new Unit enums are added.
     */
    public static Unit resolveUnit(String unitName) {
        if (unitName == null || unitName.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit name cannot be empty");
        }

        String upperUnit = unitName.toUpperCase();

        // Check Length
        for (LengthUnit unit : LengthUnit.values()) {
            if (unit.name().equals(upperUnit)) return unit;
        }
        // Check Weight
        for (WeightUnit unit : WeightUnit.values()) {
            if (unit.name().equals(upperUnit)) return unit;
        }
        // Check Volume
        for (VolumeUnit unit : VolumeUnit.values()) {
            if (unit.name().equals(upperUnit)) return unit;
        }
        // Check Temperature
        for (TemperatureUnit unit : TemperatureUnit.values()) {
            if (unit.name().equals(upperUnit)) return unit;
        }

        throw new IllegalArgumentException("Unsupported or invalid unit: " + unitName);
    }
}
