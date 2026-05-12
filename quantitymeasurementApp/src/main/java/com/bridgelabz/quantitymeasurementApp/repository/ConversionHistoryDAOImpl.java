package com.bridgelabz.quantitymeasurementApp.repository;

import com.bridgelabz.quantitymeasurementApp.config.DatabaseManager;
import com.bridgelabz.quantitymeasurementApp.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConversionHistoryDAOImpl implements ConversionHistoryDAO {
    @Override
    public void saveHistory(double inputValue, String inputUnit, String targetUnit, double resultValue) {
        // SQL Best Practices: Parameterized Queries prevent SQL Injection
        String sql = "INSERT INTO conversion_history (input_value, input_unit, target_unit, result_value) VALUES (?, ?, ?, ?)";

        // Resource Management: Try-with-resources automatically closes connections & statements
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Transaction Management / Parameter mapping
            stmt.setDouble(1, inputValue);
            stmt.setString(2, inputUnit);
            stmt.setString(3, targetUnit);
            stmt.setDouble(4, resultValue);

            stmt.executeUpdate();

        } catch (SQLException e) {
            // Exception Hierarchy: Wrap checked SQL exceptions into unchecked domain exceptions
            throw new DatabaseException("Failed to save conversion history", e);
        }
    }
}
