package com.bridgelabz.quantitymeasurementApp.service;

import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;
import com.bridgelabz.quantitymeasurementApp.domain.Quantity;
import com.bridgelabz.quantitymeasurementApp.domain.Unit;
import com.bridgelabz.quantitymeasurementApp.service.util.UnitResolver;

public class QuantityMeasurementServiceImpl implements QuantityMeasurementService{
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"}) // We handle generic type safety internally
    public MeasurementResponseDTO convert(ConversionRequestDTO request) {
        try {
            // 1. Resolve Strings to actual Enums
            Unit fromUnit = UnitResolver.resolveUnit(request.fromUnit());
            Unit toUnit = UnitResolver.resolveUnit(request.toUnit());
            //API BOUNDARY VALIDATION: Prevent Cross-Category math at runtime( as generic safety is suppress)
            if (!fromUnit.getClass().equals(toUnit.getClass())) {
                throw new IllegalArgumentException("Cannot convert across different measurement categories.");
            }

            // 2. Instantiate our core Domain object
            Quantity originalQuantity = new Quantity(request.value(), fromUnit);

            // 3. Perform the conversion
            Quantity convertedQuantity = originalQuantity.convertTo(toUnit);

            // 4. Return the successful data using DTO
            return new MeasurementResponseDTO(convertedQuantity.getValue(), request.toUnit());

        } catch (IllegalArgumentException | UnsupportedOperationException e ) {
            // Error Handling as Data
            return MeasurementResponseDTO.error(e.getMessage());
        }
    }
}
