package com.bridgelabz.quantitymeasurementApp.DTO;

public record MeasurementResponseDTO(double resultValue,String unit, String errorMessage) {
    // Success Response constructor
    public MeasurementResponseDTO(double resultValue, String unit) {
        this(resultValue, unit, null);
    }

    // Error Response constructor
    public static MeasurementResponseDTO error(String errorMessage) {
        return new MeasurementResponseDTO(0.0, null, errorMessage);
    }
}
