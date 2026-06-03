package com.bridgelabz.quantitymeasurementApp.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArithmeticRequestDTO(
        @NotNull(message = "Value1 cannot be null") Double value1,
        @NotBlank(message = "Unit1 cannot be blank") String unit1,
        @NotNull(message = "Value2 cannot be null") Double value2,
        String unit2, // Can be null for scalar division
        @NotBlank(message = "Operation cannot be blank") String operation,
        @NotBlank(message = "Result Unit cannot be blank") String resultUnit
) {}
