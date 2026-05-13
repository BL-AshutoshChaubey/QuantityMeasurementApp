package com.bridgelabz.quantitymeasurementApp.service;

import com.bridgelabz.quantitymeasurementApp.DTO.ArithmeticRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;

import com.bridgelabz.quantitymeasurementApp.DTO.HistoryItemDTO;
import java.util.List;

/**
 * Service-Oriented Design interface defining exactly what the business layer can do.
 */
public interface QuantityMeasurementService {
    MeasurementResponseDTO convert(ConversionRequestDTO requestDTO);
    MeasurementResponseDTO performArithmetic(ArithmeticRequestDTO requestDTO);
    List<HistoryItemDTO> getHistory(String email);
}
