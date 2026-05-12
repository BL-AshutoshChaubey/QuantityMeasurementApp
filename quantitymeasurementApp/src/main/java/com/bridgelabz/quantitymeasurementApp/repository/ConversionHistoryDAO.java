package com.bridgelabz.quantitymeasurementApp.repository;

public interface ConversionHistoryDAO {
    void saveHistory(double inputValue, String inputUnit, String targetUnit, double resultValue);
}
