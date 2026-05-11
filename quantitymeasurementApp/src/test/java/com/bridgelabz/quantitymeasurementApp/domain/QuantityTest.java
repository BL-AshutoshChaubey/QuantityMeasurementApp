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
    // --- UC4 Tests (Extended Length Units) ---

    @Test
    void given3FeetAnd1Yard_ShouldReturnEqual() {
        Quantity feet = new Quantity(3.0, LengthUnit.FEET);
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        assertEquals(feet, yard); // Proves 3 ft == 1 yd
    }

    @Test
    void given1YardAnd3Feet_ShouldReturnEqual() {
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        Quantity feet = new Quantity(3.0, LengthUnit.FEET);
        assertEquals(yard, feet);
    }

    @Test
    void given1YardAnd36Inches_ShouldReturnEqual() {
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        Quantity inch = new Quantity(36.0, LengthUnit.INCHES);
        assertEquals(yard, inch); // Proves 1 yd == 36 in
    }

    @Test
    void given36InchesAnd1Yard_ShouldReturnEqual() {
        Quantity inch = new Quantity(36.0, LengthUnit.INCHES);
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        assertEquals(inch, yard);
    }

    @Test
    void given1MileAnd1760Yards_ShouldReturnEqual() {
        Quantity mile = new Quantity(1.0, LengthUnit.MILE);
        Quantity yard = new Quantity(1760.0, LengthUnit.YARD);
        assertEquals(mile, yard); // Proves 1 mile == 1760 yds
    }

    @Test
    void given1YardAnd1Mile_ShouldReturnNotEqual() {
        Quantity yard = new Quantity(1.0, LengthUnit.YARD);
        Quantity mile = new Quantity(1.0, LengthUnit.MILE);
        assertNotEquals(yard, mile);
    }
}
