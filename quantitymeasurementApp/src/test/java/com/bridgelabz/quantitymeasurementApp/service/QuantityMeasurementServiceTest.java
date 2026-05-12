package com.bridgelabz.quantitymeasurementApp.service;

import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;
import com.bridgelabz.quantitymeasurementApp.repository.ConversionHistoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;



public class QuantityMeasurementServiceTest {
    // Dependency Injection setup for testing
    private QuantityMeasurementService service;
    private ConversionHistoryDAO mockDao;
    @BeforeEach
    void setUp() {
        mockDao = Mockito.mock(ConversionHistoryDAO.class);
        this.service = new QuantityMeasurementServiceImpl(mockDao);
    }

    @Test
    void givenValidLengthConversionRequest_ShouldReturnSuccessDTO() {
        ConversionRequestDTO request = new ConversionRequestDTO(1.0, "FEET", "INCHES");
        MeasurementResponseDTO response = service.convert(request);

        assertNull(response.errorMessage());
        assertEquals("INCHES", response.unit());

    }
    @Test
    void givenValidRequest_ShouldReturnSuccessAndSaveToDb() {
        ConversionRequestDTO request = new ConversionRequestDTO(1.0, "FEET", "INCHES");
        MeasurementResponseDTO response = service.convert(request);

        assertNull(response.errorMessage());
        assertEquals(12.0, response.resultValue());

        // Verify the DAO was actually called with the right parameters!
        Mockito.verify(mockDao).saveHistory(1.0, "FEET", "INCHES", 12.0);
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
