package com.bridgelabz.quantitymeasurementApp.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class QuantityTest {
    @Test
    void given0FeetAnd0Feet_ShouldReturnEqual() {
        Quantity<LengthUnit> feet1 = new Quantity<>(0.0, LengthUnit.FEET);
        Quantity<LengthUnit> feet2 = new Quantity<>(0.0, LengthUnit.FEET);
        assertEquals(feet1, feet2);
    }

    @Test
    void given0FeetAnd1Foot_ShouldReturnNotEqual() {
        Quantity<LengthUnit> feet1 = new Quantity<>(0.0, LengthUnit.FEET);
        Quantity<LengthUnit> feet2 = new Quantity<>(1.0, LengthUnit.FEET);
        assertNotEquals(feet1, feet2);
    }

    @Test
    void givenNullQuantity_ShouldReturnNotEqual() {
        Quantity<LengthUnit> feet1 = new Quantity<>(0.0, LengthUnit.FEET);
        assertNotEquals(feet1, null);
    }

    @Test
    void givenReferenceEquality_ShouldReturnEqual() {
        Quantity<LengthUnit> feet1 = new Quantity<>(0.0, LengthUnit.FEET);
        assertEquals(feet1, feet1);
    }

    @Test
    void givenDifferentType_ShouldReturnNotEqual() {
        Quantity<LengthUnit> feet1 = new Quantity<>(0.0, LengthUnit.FEET);
        Object otherObject = new Object();
        assertNotEquals(feet1, otherObject);
    }
    // --- UC2 Tests (Feet and Inches Equality) ---
    @Test
    void given0InchesAnd0Inches_ShouldReturnEqual() {
        Quantity<LengthUnit> inch1 = new Quantity<>(0.0, LengthUnit.INCHES);
        Quantity<LengthUnit> inch2 = new Quantity<>(0.0, LengthUnit.INCHES);
        assertEquals(inch1, inch2);
    }

    @Test
    void given1FootAnd12Inches_ShouldReturnEqual() {
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCHES);
        assertEquals(foot, inch); // Proves 1 ft == 12 in
    }

    @Test
    void given12InchesAnd1Foot_ShouldReturnEqual() {
        Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(inch, foot); // Proves commutative property
    }

    @Test
    void given1FootAnd1Inch_ShouldReturnNotEqual() {
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inch = new Quantity<>(1.0, LengthUnit.INCHES);
        assertNotEquals(foot, inch); // Proves 1 ft != 1 in
    }
    // --- UC4 Tests (Extended Length Units) ---

    @Test
    void given3FeetAnd1Yard_ShouldReturnEqual() {
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        assertEquals(feet, yard); // Proves 3 ft == 1 yd
    }

    @Test
    void given1YardAnd3Feet_ShouldReturnEqual() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        assertEquals(yard, feet);
    }

    @Test
    void given1YardAnd36Inches_ShouldReturnEqual() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> inch = new Quantity<>(36.0, LengthUnit.INCHES);
        assertEquals(yard, inch); // Proves 1 yd == 36 in
    }

    @Test
    void given36InchesAnd1Yard_ShouldReturnEqual() {
        Quantity<LengthUnit> inch = new Quantity<>(36.0, LengthUnit.INCHES);
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        assertEquals(inch, yard);
    }

    @Test
    void given1MileAnd1760Yards_ShouldReturnEqual() {
        Quantity<LengthUnit> mile = new Quantity<>(1.0, LengthUnit.MILE);
        Quantity<LengthUnit> yard = new Quantity<>(1760.0, LengthUnit.YARD);
        assertEquals(mile, yard); // Proves 1 mile == 1760 yds
    }

    @Test
    void given1YardAnd1Mile_ShouldReturnNotEqual() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> mile = new Quantity<>(1.0, LengthUnit.MILE);
        assertNotEquals(yard, mile);
    }
    // --- UC5 Tests (Unit-to-Unit Conversion) ---

    @Test
    void given1Foot_WhenConvertedToInches_ShouldReturn12Inches() {
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> expectedInches = new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> converted = foot.convertTo(LengthUnit.INCHES);

        assertEquals(expectedInches, converted);
    }

    @Test
    void given2Yards_WhenConvertedToFeet_ShouldReturn6Feet() {
        Quantity<LengthUnit> yard = new Quantity<>(2.0, LengthUnit.YARD);
        Quantity<LengthUnit> expectedFeet = new Quantity<>(6.0, LengthUnit.FEET);

        Quantity<LengthUnit> converted = yard.convertTo(LengthUnit.FEET);

        assertEquals(expectedFeet, converted);
    }

    @Test
    void given1Mile_WhenConvertedToYards_ShouldReturn1760Yards() {
        Quantity<LengthUnit> mile = new Quantity<>(1.0, LengthUnit.MILE);
        Quantity<LengthUnit> expectedYards = new Quantity<>(1760.0, LengthUnit.YARD);

        Quantity<LengthUnit> converted = mile.convertTo(LengthUnit.YARD);

        assertEquals(expectedYards, converted);
    }

    @Test
    void givenQuantity_WhenConverted_ShouldReturnBrandNewObject() {
        Quantity<LengthUnit> original = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> converted = original.convertTo(LengthUnit.INCHES);

        // Assert they have equal values...
        assertEquals(original, converted);
        // ...but assert they are NOT the exact same object in memory (Immutability check)
        assertNotSame(original, converted);
    }
    // --- UC6 Tests (Addition of Length Units) ---

    @Test
    void given2InchesAnd2Inches_WhenAdded_ShouldReturn4Inches() {
        Quantity<LengthUnit> inch1 = new Quantity<>(2.0, LengthUnit.INCHES);
        Quantity<LengthUnit> inch2 = new Quantity<>(2.0, LengthUnit.INCHES);
        Quantity<LengthUnit> expected = new Quantity<>(4.0, LengthUnit.INCHES);

        Quantity<LengthUnit> sum = inch1.add(inch2);

        assertEquals(expected, sum);
    }

    @Test
    void given1FootAnd2Inches_WhenAdded_ShouldReturn14Inches() {
        // We expect the result in inches, so we call .add() on the inches object
        Quantity<LengthUnit> inch = new Quantity<>(2.0, LengthUnit.INCHES);
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> expected = new Quantity<>(14.0, LengthUnit.INCHES);

        Quantity<LengthUnit> sum = inch.add(foot);

        assertEquals(expected, sum);
    }

    @Test
    void given1FootAnd1Foot_WhenAdded_ShouldReturn24Inches() {
        Quantity<LengthUnit> foot1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> foot2 = new Quantity<>(1.0, LengthUnit.FEET);

        // Even if we add feet, we can assert it equals 24 inches because of our strong equals() method!
        Quantity<LengthUnit> expectedInches = new Quantity<>(24.0, LengthUnit.INCHES);

        Quantity<LengthUnit> sum = foot1.add(foot2);

        assertEquals(expectedInches, sum);
    }

    @Test
    void given2InchesAnd2Point5Centimeters_WhenAdded_ShouldThrowExceptionForNull() {
        Quantity<LengthUnit> inch = new Quantity<>(2.0, LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class, () ->
            inch.add(null));

    }
    // --- UC7 Tests (Addition with Target Unit Specification) ---

    @Test
    void given1FootAnd2Inches_WhenAddedWithTargetInches_ShouldReturn14Inches() {
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inch = new Quantity<>(2.0, LengthUnit.INCHES);
        Quantity<LengthUnit> expected = new Quantity<>(14.0, LengthUnit.INCHES);

        // Explicitly requesting the answer in INCHES
        Quantity<LengthUnit> sum = foot.add(inch, LengthUnit.INCHES);

        assertEquals(expected, sum);
    }

    @Test
    void given1FootAnd2Feet_WhenAddedWithTargetYards_ShouldReturn1Yard() {
        Quantity<LengthUnit> foot1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> foot2 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> expected = new Quantity<>(1.0, LengthUnit.YARD);

        // Explicitly requesting the answer in YARDS
        Quantity<LengthUnit> sum = foot1.add(foot2, LengthUnit.YARD);

        assertEquals(expected, sum);
    }

    @Test
    void given2InchesAnd2Inches_WhenAddedWithNullTargetUnit_ShouldThrowException() {
        Quantity<LengthUnit> inch1 = new Quantity<>(2.0, LengthUnit.INCHES);
        Quantity<LengthUnit> inch2 = new Quantity<>(2.0, LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class, () ->
            inch1.add(inch2, null));

    }
    // --- UC9 Tests (Weight Support & Type Safety) ---

    @Test
    void given1GramAnd1Gram_ShouldReturnEqual() {
        Quantity<WeightUnit> gram1 = new Quantity<>(1.0, WeightUnit.GRAM);
        Quantity<WeightUnit> gram2 = new Quantity<>(1.0, WeightUnit.GRAM);
        assertEquals(gram1, gram2);
    }

    @Test
    void given1KilogramAnd1000Grams_ShouldReturnEqual() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> gram = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(kg, gram);
    }

    @Test
    void given1TonneAnd1000Kgs_ShouldReturnEqual() {
        Quantity<WeightUnit> tonne = new Quantity<>(1.0, WeightUnit.TONNE);
        Quantity<WeightUnit> kg = new Quantity<>(1000.0, WeightUnit.KILOGRAM);
        assertEquals(tonne, kg);
    }

    @Test
    void given1TonneAnd1000Grams_WhenAdded_ShouldReturn1001Kgs() {
        Quantity<WeightUnit> tonne = new Quantity<>(1.0, WeightUnit.TONNE);
        Quantity<WeightUnit> grams = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> expected = new Quantity<>(1001.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result = tonne.add(grams, WeightUnit.KILOGRAM);
        assertEquals(expected, result);
    }

    // --- UC11 Tests (Volume Support) ---

    @Test
    void given1GallonAnd3Point78Litres_ShouldReturnEqual() {
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> litre = new Quantity<>(3.78, VolumeUnit.LITRE);
        assertEquals(gallon, litre);
    }

    @Test
    void given1LitreAnd1000Millilitres_ShouldReturnEqual() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(litre, ml);
    }

    @Test
    void given1GallonAnd3Point78Litres_WhenAdded_ShouldReturn7Point56Litres() {
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> litre = new Quantity<>(3.78, VolumeUnit.LITRE);
        Quantity<VolumeUnit> expected = new Quantity<>(7.56, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = gallon.add(litre, VolumeUnit.LITRE);
        assertEquals(expected, result);
    }

    @Test
    void given1LitreAnd1000Millilitres_WhenAdded_ShouldReturn2Litres() {
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> expected = new Quantity<>(2.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = litre.add(ml, VolumeUnit.LITRE);
        assertEquals(expected, result);
    }
    // --- UC12 Tests (Subtraction & Division) ---

    @Test
    void given1YardAnd1Foot_WhenSubtracted_ShouldReturn2Feet() {
        Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARD);
        Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> expected = new Quantity<>(2.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = yard.subtract(foot, LengthUnit.FEET);
        assertEquals(expected, result);
    }

    @Test
    void given2InchesAnd1Inch_WhenSubtracted_ShouldReturn1Inch() {
        Quantity<LengthUnit> inch2 = new Quantity<>(2.0, LengthUnit.INCHES);
        Quantity<LengthUnit> inch1 = new Quantity<>(1.0, LengthUnit.INCHES);
        Quantity<LengthUnit> expected = new Quantity<>(1.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = inch2.subtract(inch1);
        assertEquals(expected, result);
    }

    @Test
    void given2Inches_WhenDividedBy2_ShouldReturn1Inch() {
        Quantity<LengthUnit> inch = new Quantity<>(2.0, LengthUnit.INCHES);
        Quantity<LengthUnit> expected = new Quantity<>(1.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = inch.divide(2.0);
        assertEquals(expected, result);
    }

    @Test
    void given1Gallon_WhenDividedBy2_ShouldReturn1Point89Litres() {
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        // 1 Gallon = 3.78 Litres. Divided by 2 = 1.89 Litres
        Quantity<VolumeUnit> expected = new Quantity<>(1.89, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = gallon.divide(2.0, VolumeUnit.LITRE);
        assertEquals(expected, result);
    }

    @Test
    void givenQuantity_WhenDividedByZero_ShouldThrowArithmeticException() {
        Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertThrows(ArithmeticException.class, () -> kg.divide(0.0));
    }
    // --- UC14 Tests (Temperature & Non-Linear Conversions) ---

    @Test
    void given212Fahrenheit_WhenConvertedToCelsius_ShouldReturn100Celsius() {
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> expectedCelsius = new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        // Allows a tiny delta (0.001) for floating-point precision in division
        assertTrue(Math.abs(expectedCelsius.convertTo(TemperatureUnit.CELSIUS).hashCode() - fahrenheit.convertTo(TemperatureUnit.CELSIUS).hashCode()) >= 0);
        // Better yet, use assertEquals which calls our strict equals() method!
        assertEquals(expectedCelsius, fahrenheit);
    }

    @Test
    void given100Celsius_WhenConvertedToFahrenheit_ShouldReturn212Fahrenheit() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> expectedFahrenheit = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);

        assertEquals(expectedFahrenheit, celsius);
    }

    @Test
    void givenTemperature_WhenAdded_ShouldThrowUnsupportedOperationException() {
        Quantity<TemperatureUnit> celsius1 = new Quantity<>(10.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> celsius2 = new Quantity<>(10.0, TemperatureUnit.CELSIUS);

        assertThrows(UnsupportedOperationException.class, () -> celsius1.add(celsius2));
    }



}
