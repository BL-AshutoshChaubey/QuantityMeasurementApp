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
    // --- UC5 Tests (Unit-to-Unit Conversion) ---

    @Test
    void given1Foot_WhenConvertedToInches_ShouldReturn12Inches() {
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);
        Quantity expectedInches = new Quantity(12.0, LengthUnit.INCHES);

        Quantity converted = foot.convertTo(LengthUnit.INCHES);

        assertEquals(expectedInches, converted);
    }

    @Test
    void given2Yards_WhenConvertedToFeet_ShouldReturn6Feet() {
        Quantity yard = new Quantity(2.0, LengthUnit.YARD);
        Quantity expectedFeet = new Quantity(6.0, LengthUnit.FEET);

        Quantity converted = yard.convertTo(LengthUnit.FEET);

        assertEquals(expectedFeet, converted);
    }

    @Test
    void given1Mile_WhenConvertedToYards_ShouldReturn1760Yards() {
        Quantity mile = new Quantity(1.0, LengthUnit.MILE);
        Quantity expectedYards = new Quantity(1760.0, LengthUnit.YARD);

        Quantity converted = mile.convertTo(LengthUnit.YARD);

        assertEquals(expectedYards, converted);
    }

    @Test
    void givenQuantity_WhenConverted_ShouldReturnBrandNewObject() {
        Quantity original = new Quantity(1.0, LengthUnit.FEET);
        Quantity converted = original.convertTo(LengthUnit.INCHES);

        // Assert they have equal values...
        assertEquals(original, converted);
        // ...but assert they are NOT the exact same object in memory (Immutability check)
        assertNotSame(original, converted);
    }
    // --- UC6 Tests (Addition of Length Units) ---

    @Test
    void given2InchesAnd2Inches_WhenAdded_ShouldReturn4Inches() {
        Quantity inch1 = new Quantity(2.0, LengthUnit.INCHES);
        Quantity inch2 = new Quantity(2.0, LengthUnit.INCHES);
        Quantity expected = new Quantity(4.0, LengthUnit.INCHES);

        Quantity sum = inch1.add(inch2);

        assertEquals(expected, sum);
    }

    @Test
    void given1FootAnd2Inches_WhenAdded_ShouldReturn14Inches() {
        // We expect the result in inches, so we call .add() on the inches object
        Quantity inch = new Quantity(2.0, LengthUnit.INCHES);
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);
        Quantity expected = new Quantity(14.0, LengthUnit.INCHES);

        Quantity sum = inch.add(foot);

        assertEquals(expected, sum);
    }

    @Test
    void given1FootAnd1Foot_WhenAdded_ShouldReturn24Inches() {
        Quantity foot1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity foot2 = new Quantity(1.0, LengthUnit.FEET);

        // Even if we add feet, we can assert it equals 24 inches because of our strong equals() method!
        Quantity expectedInches = new Quantity(24.0, LengthUnit.INCHES);

        Quantity sum = foot1.add(foot2);

        assertEquals(expectedInches, sum);
    }

    @Test
    void given2InchesAnd2Point5Centimeters_WhenAdded_ShouldThrowExceptionForNull() {
        Quantity inch = new Quantity(2.0, LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class, () -> {
            inch.add(null);
        });
    }
    // --- UC7 Tests (Addition with Target Unit Specification) ---

    @Test
    void given1FootAnd2Inches_WhenAddedWithTargetInches_ShouldReturn14Inches() {
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);
        Quantity inch = new Quantity(2.0, LengthUnit.INCHES);
        Quantity expected = new Quantity(14.0, LengthUnit.INCHES);

        // Explicitly requesting the answer in INCHES
        Quantity sum = foot.add(inch, LengthUnit.INCHES);

        assertEquals(expected, sum);
    }

    @Test
    void given1FootAnd2Feet_WhenAddedWithTargetYards_ShouldReturn1Yard() {
        Quantity foot1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity foot2 = new Quantity(2.0, LengthUnit.FEET);
        Quantity expected = new Quantity(1.0, LengthUnit.YARD);

        // Explicitly requesting the answer in YARDS
        Quantity sum = foot1.add(foot2, LengthUnit.YARD);

        assertEquals(expected, sum);
    }

    @Test
    void given2InchesAnd2Inches_WhenAddedWithNullTargetUnit_ShouldThrowException() {
        Quantity inch1 = new Quantity(2.0, LengthUnit.INCHES);
        Quantity inch2 = new Quantity(2.0, LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class, () -> {
            inch1.add(inch2, null);
        });
    }
    // --- UC9 Tests (Weight Support & Type Safety) ---

    @Test
    void given1GramAnd1Gram_ShouldReturnEqual() {
        Quantity gram1 = new Quantity(1.0, WeightUnit.GRAM);
        Quantity gram2 = new Quantity(1.0, WeightUnit.GRAM);
        assertEquals(gram1, gram2);
    }

    @Test
    void given1KilogramAnd1000Grams_ShouldReturnEqual() {
        Quantity kg = new Quantity(1.0, WeightUnit.KILOGRAM);
        Quantity gram = new Quantity(1000.0, WeightUnit.GRAM);
        assertEquals(kg, gram);
    }

    @Test
    void given1TonneAnd1000Kgs_ShouldReturnEqual() {
        Quantity tonne = new Quantity(1.0, WeightUnit.TONNE);
        Quantity kg = new Quantity(1000.0, WeightUnit.KILOGRAM);
        assertEquals(tonne, kg);
    }

    @Test
    void given1TonneAnd1000Grams_WhenAdded_ShouldReturn1001Kgs() {
        Quantity tonne = new Quantity(1.0, WeightUnit.TONNE);
        Quantity grams = new Quantity(1000.0, WeightUnit.GRAM);
        Quantity expected = new Quantity(1001.0, WeightUnit.KILOGRAM);

        Quantity result = tonne.add(grams, WeightUnit.KILOGRAM);
        assertEquals(expected, result);
    }

    // --- Type Safety Validation Tests ---

    @Test
    void given1FootAnd1Gram_WhenCompared_ShouldNotBeEqual() {
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);
        Quantity gram = new Quantity(1.0, WeightUnit.GRAM);

        assertNotEquals(foot, gram); // Fails safely without an exception
    }

    @Test
    void given1FootAnd1Gram_WhenAdded_ShouldThrowException() {
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);
        Quantity gram = new Quantity(1.0, WeightUnit.GRAM);

        assertThrows(IllegalArgumentException.class, () -> foot.add(gram));
    }

    @Test
    void given1Foot_WhenConvertedToGrams_ShouldThrowException() {
        Quantity foot = new Quantity(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> foot.convertTo(WeightUnit.GRAM));
    }
}
