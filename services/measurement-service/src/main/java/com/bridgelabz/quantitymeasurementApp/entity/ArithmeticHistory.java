package com.bridgelabz.quantitymeasurementApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "arithmetic_history")
@Data
public class ArithmeticHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value1;
    private String unit1;
    private double value2;
    private String unit2;
    private String operation;
    private double resultValue;
    private String resultUnit;
    private LocalDateTime createdAt = LocalDateTime.now();

    private String userEmail;

    public ArithmeticHistory() {}

    public ArithmeticHistory(double value1, String unit1, double value2, String unit2, String operation, double resultValue, String resultUnit, String userEmail) {
        this.value1 = value1;
        this.unit1 = unit1;
        this.value2 = value2;
        this.unit2 = unit2;
        this.operation = operation;
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.userEmail = userEmail;
    }
}
