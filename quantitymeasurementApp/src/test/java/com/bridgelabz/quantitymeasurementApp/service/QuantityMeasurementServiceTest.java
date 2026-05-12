package com.bridgelabz.quantitymeasurementApp.service;

import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class QuantityMeasurementServiceTest {
    // Dependency Injection setup for testing
    private QuantityMeasurementService service;

    @BeforeEach
    void setUp() {
        // In the future, Spring Boot will automatically inject this!
        this.service = new QuantityMeasurementServiceImpl();
    }

    @Test
    void givenValidLengthConversionRequest_ShouldReturnSuccessDTO() {
        ConversionRequestDTO request = new ConversionRequestDTO(1.0, "FEET", "INCHES");
        MeasurementResponseDTO response = service.convert(request);

        assertNull(response.errorMessage());
        assertEquals("INCHES", response.unit());
        // assertEquals(12.0, response.resultValue()); // Uncomment once getValue() is added to Quantity
    }

    @Test
    void givenCrossCategoryRequest_ShouldReturnErrorDTO() {
        ConversionRequestDTO request = new ConversionRequestDTO(1.0, "FEET", "GRAM");
        MeasurementResponseDTO response = service.convert(request);

        // Verifies "Error Handling as Data"
        assertNotNull(response.errorMessage());
        assertEquals(0.0, response.resultValue());
    }

    @Test
    void givenInvalidUnitString_ShouldReturnErrorDTO() {
        ConversionRequestDTO request = new ConversionRequestDTO(1.0, "FAKE_UNIT", "INCHES");
        MeasurementResponseDTO response = service.convert(request);

        assertNotNull(response.errorMessage());
        assertTrue(response.errorMessage().contains("Unsupported or invalid unit"));
    }
}
