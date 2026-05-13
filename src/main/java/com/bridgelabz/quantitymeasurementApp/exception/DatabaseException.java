package com.bridgelabz.quantitymeasurementApp.exception;

import java.sql.SQLException;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String failedToSaveConversionHistory, SQLException e) {
    }
}
