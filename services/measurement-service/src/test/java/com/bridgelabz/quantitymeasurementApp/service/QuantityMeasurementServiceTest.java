package com.bridgelabz.quantitymeasurementApp.service;

import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;
import com.bridgelabz.quantitymeasurementApp.entity.ConversionHistory;
import com.bridgelabz.quantitymeasurementApp.repository.ArithmeticHistoryRepository;
import com.bridgelabz.quantitymeasurementApp.repository.ConversionHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentCaptor;


public class QuantityMeasurementServiceTest {
    // Dependency Injection setup for testing
    private QuantityMeasurementService service;
    private ConversionHistoryRepository mockDao;
    private ArithmeticHistoryRepository mockArithDao;
    @BeforeEach
    void setUp() {
        mockDao = Mockito.mock(ConversionHistoryRepository.class);
        mockArithDao = Mockito.mock(ArithmeticHistoryRepository.class);
        this.service = new QuantityMeasurementServiceImpl(mockDao, mockArithDao);
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

        //  Create an ArgumentCaptor for the ConversionHistory class
        ArgumentCaptor<ConversionHistory> historyCaptor = ArgumentCaptor.forClass(ConversionHistory.class);
        Mockito.verify(mockDao).save(historyCaptor.capture());
        ConversionHistory savedHistory = historyCaptor.getValue();
        // Assert the fields of the captured object
        assertEquals(1.0, savedHistory.getInputValue());
        assertEquals("FEET", savedHistory.getInputUnit());
        assertEquals("INCHES", savedHistory.getTargetUnit());
        assertEquals(12.0, savedHistory.getResultValue());

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
