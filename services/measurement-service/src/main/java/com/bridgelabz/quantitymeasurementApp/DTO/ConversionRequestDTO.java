package com.bridgelabz.quantitymeasurementApp.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConversionRequestDTO(
        @NotNull(message = "Value cannot be null") Double value,
        @NotBlank(message = "From Unit cannot be blank") String fromUnit,
        @NotBlank(message = "To Unit cannot be blank") String toUnit
) {}
