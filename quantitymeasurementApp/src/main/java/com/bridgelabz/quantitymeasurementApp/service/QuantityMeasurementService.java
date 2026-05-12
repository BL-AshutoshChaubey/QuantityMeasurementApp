package com.bridgelabz.quantitymeasurementApp.service;

import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;

/**
 * Service-Oriented Design interface defining exactly what the business layer can do.
 */
public interface QuantityMeasurementService {
    MeasurementResponseDTO convert(ConversionRequestDTO requestDTO);
}
