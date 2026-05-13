package com.bridgelabz.quantitymeasurementApp.DTO;

public record ArithmeticRequestDTO(double value1, String unit1, double value2, String unit2, String operation, String resultUnit) {
}
