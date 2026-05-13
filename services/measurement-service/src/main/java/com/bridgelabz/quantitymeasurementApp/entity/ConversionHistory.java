package com.bridgelabz.quantitymeasurementApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversion_history")
@Data
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double inputValue;
    private String inputUnit;
    private String targetUnit;
    private double resultValue;
    private LocalDateTime createdAt = LocalDateTime.now();

    private String userEmail;

    // Default constructor required by JPA
    public ConversionHistory() {}

    public ConversionHistory(double inputValue, String inputUnit, String targetUnit, double resultValue, String userEmail) {
        this.inputValue = inputValue;
        this.inputUnit = inputUnit;
        this.targetUnit = targetUnit;
        this.resultValue = resultValue;
        this.userEmail = userEmail;
    }
}
