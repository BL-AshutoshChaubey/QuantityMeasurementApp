package com.bridgelabz.quantitymeasurementApp.DTO;

import java.time.LocalDateTime;

public record HistoryItemDTO(
        Long id,
        String actionType, // "CONVERSION" or "ARITHMETIC"
        String detail,     // Formatted context string
        LocalDateTime timestamp
) {}
