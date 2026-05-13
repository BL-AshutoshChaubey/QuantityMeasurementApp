package com.bridgelabz.quantitymeasurementApp.service;

import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;
import com.bridgelabz.quantitymeasurementApp.domain.Quantity;
import com.bridgelabz.quantitymeasurementApp.domain.Unit;
import com.bridgelabz.quantitymeasurementApp.entity.ConversionHistory;
import com.bridgelabz.quantitymeasurementApp.repository.ConversionHistoryRepository;
import com.bridgelabz.quantitymeasurementApp.service.util.UnitResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class QuantityMeasurementServiceImpl implements QuantityMeasurementService{
    private final ConversionHistoryRepository historyRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);
    public QuantityMeasurementServiceImpl(ConversionHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"}) // We handle generic type safety internally
    public MeasurementResponseDTO convert(ConversionRequestDTO request) {
        logger.info("Received conversion request: {} {} to {}", request.value(), request.fromUnit(), request.toUnit());
        try {
            Unit fromUnit = UnitResolver.resolveUnit(request.fromUnit());
            Unit toUnit = UnitResolver.resolveUnit(request.toUnit());

            //API BOUNDARY VALIDATION: Prevent Cross-Category math at runtime( as generic safety is suppress)
            if (!fromUnit.getClass().equals(toUnit.getClass())) {
                throw new IllegalArgumentException("Cannot convert across different measurement categories.");
            }

            Quantity originalQuantity = new Quantity(request.value(), fromUnit);
            Quantity convertedQuantity = originalQuantity.convertTo(toUnit);
            double finalResult = convertedQuantity.getValue();

            // Layer Integration: Save to DB
            ConversionHistory history = new ConversionHistory(request.value(), request.fromUnit(), request.toUnit(), finalResult);
            historyRepository.save(history);

            logger.info("Conversion successful. Result: {}", finalResult);
            return new MeasurementResponseDTO(finalResult, request.toUnit());

        } catch (Exception e ) {
            logger.error("Conversion failed: {}", e.getMessage());
            return MeasurementResponseDTO.error(e.getMessage());
        }
    }
}
