package com.bridgelabz.quantitymeasurementApp.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class QuantityTest {
    @Test
    void given0FeetAnd0Feet_ShouldReturnEqual() {
        Quantity feet1 = new Quantity(0.0, MeasurementUnit.FEET);
        Quantity feet2 = new Quantity(0.0, MeasurementUnit.FEET);
        assertEquals(feet1, feet2);
    }

    @Test
    void given0FeetAnd1Foot_ShouldReturnNotEqual() {
        Quantity feet1 = new Quantity(0.0, MeasurementUnit.FEET);
        Quantity feet2 = new Quantity(1.0, MeasurementUnit.FEET);
        assertNotEquals(feet1, feet2);
    }

    @Test
    void givenNullQuantity_ShouldReturnNotEqual() {
        Quantity feet1 = new Quantity(0.0, MeasurementUnit.FEET);
        assertNotEquals(feet1, null);
    }

    @Test
    void givenReferenceEquality_ShouldReturnEqual() {
        Quantity feet1 = new Quantity(0.0, MeasurementUnit.FEET);
        assertEquals(feet1, feet1);
    }

    @Test
    void givenDifferentType_ShouldReturnNotEqual() {
        Quantity feet1 = new Quantity(0.0, MeasurementUnit.FEET);
        Object otherObject = new Object();
        assertNotEquals(feet1, otherObject);
    }
}
