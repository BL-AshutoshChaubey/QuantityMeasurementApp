package com.bridgelabz.quantitymeasurementApp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private static final HikariDataSource dataSource;

    static {
        // Configuration Management & Connection Pooling setup
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/quantity_measurement");
        config.setUsername("root");
        config.setPassword("tiger");

        // Performance Considerations
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
