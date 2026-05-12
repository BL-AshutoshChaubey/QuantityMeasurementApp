package com.bridgelabz.quantitymeasurementApp.domain;

public enum Operation {
    ADD((a, b) -> a + b),
    SUBTRACT((a, b) -> a - b),
    DIVIDE((a, b) -> {
        if (b == 0.0) {
            throw new ArithmeticException("Cannot divide a quantity by zero");
        }
        return a / b;
    });

    // Use our custom interface instead of DoubleBinaryOperator
    private final ArithmeticStrategy strategy;

    Operation(ArithmeticStrategy strategy) {
        this.strategy = strategy;
    }

    public double apply(double a, double b) {
        // Call our custom method name 'execute'
        return strategy.execute(a, b);
    }
}
