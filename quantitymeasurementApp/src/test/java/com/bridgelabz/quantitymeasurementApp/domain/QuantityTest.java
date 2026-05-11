package com.bridgelabz.quantitymeasurementApp.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class QuantityTest {
    @Test
    void given0FeetAnd0Feet_ShouldReturnEqual() {
        Quantity feet1 = new Quantity(0.0, LengthUnit.FEET);
        Quantity feet2 = new Quantity(0.0, LengthUnit.FEET);
        assertEquals(feet1, feet2);
    }

    @Test
    void given0FeetAnd1Foot_ShouldReturnNotEqual() {
        Quantity feet1 = new Quantity(0.0, LengthUnit.FEET);
        Quantity feet2 = new Quantity(1.0, LengthUnit.FEET);
        assertNotEquals(feet1, feet2);
    }

    @Test
    void givenNullQuantity_ShouldReturnNotEqual() {
        Quantity feet1 = new Quantity(0.0, LengthUnit.FEET);
        assertNotEquals(feet1, null);
    }

    @Test
    void givenReferenceEquality_ShouldReturnEqual() {
        Quantity feet1 = new Quantity(0.0, LengthUnit.FEET);
        assertEquals(feet1, feet1);
    }

    @Test
    void givenDifferentType_ShouldReturnNotEqual() {
        Quantity feet1 = new Quantity(0.0, LengthUnit.FEET);
        Object otherObject = new Object();
        assertNotEquals(feet1, otherObject);
    }
    // --- UC2 Tests (Feet and Inches Equality) ---
    @Test
    void given0InchesAnd0Inches_ShouldReturnEqual() {
        Quantity inch1 = new Quantity(0.0, LengthUnit.INCHES);
        Quantity inch2 = new Quantity(0.0, LengthUnit.INCHES);
        assertEquals(inch1, inch2);
    }

    @Test
    void given1FootAnd12Inches_ShouldReturnEqual() {
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);
        Quantity inch = new Quantity(12.0, LengthUnit.INCHES);
        assertEquals(foot, inch); // Proves 1 ft == 12 in
    }

    @Test
    void given12InchesAnd1Foot_ShouldReturnEqual() {
        Quantity inch = new Quantity(12.0, LengthUnit.INCHES);
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);
        assertEquals(inch, foot); // Proves commutative property
    }

    @Test
    void given1FootAnd1Inch_ShouldReturnNotEqual() {
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);
        Quantity inch = new Quantity(1.0, LengthUnit.INCHES);
        assertNotEquals(foot, inch); // Proves 1 ft != 1 in
    }
}
