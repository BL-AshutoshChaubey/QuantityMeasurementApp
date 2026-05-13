package com.bridgelabz.quantitymeasurementApp.service;

import com.bridgelabz.quantitymeasurementApp.DTO.ArithmeticRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;
import com.bridgelabz.quantitymeasurementApp.domain.Quantity;
import com.bridgelabz.quantitymeasurementApp.domain.Unit;
import com.bridgelabz.quantitymeasurementApp.entity.ArithmeticHistory;
import com.bridgelabz.quantitymeasurementApp.entity.ConversionHistory;
import com.bridgelabz.quantitymeasurementApp.repository.ArithmeticHistoryRepository;
import com.bridgelabz.quantitymeasurementApp.repository.ConversionHistoryRepository;
import com.bridgelabz.quantitymeasurementApp.service.util.UnitResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.bridgelabz.quantitymeasurementApp.DTO.HistoryItemDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class QuantityMeasurementServiceImpl implements QuantityMeasurementService{
    private final ConversionHistoryRepository historyRepository;
    private final ArithmeticHistoryRepository arithmeticHistoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);
    
    public QuantityMeasurementServiceImpl(ConversionHistoryRepository historyRepository, ArithmeticHistoryRepository arithmeticHistoryRepository) {
        this.historyRepository = historyRepository;
        this.arithmeticHistoryRepository = arithmeticHistoryRepository;
    }

    private String resolveCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return auth.getName();
        }
        return null;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"}) // We handle generic type safety internally
    public MeasurementResponseDTO convert(ConversionRequestDTO request) {
        logger.info("Received conversion request: {} {} to {}", request.value(), request.fromUnit(), request.toUnit());
        try {
            Unit fromUnit = UnitResolver.resolveUnit(request.fromUnit());
            Unit toUnit = UnitResolver.resolveUnit(request.toUnit());

            //API BOUNDARY VALIDATION: Prevent Cross-Category math at runtime( as generic safety is suppress)
            if (!fromUnit.getClass().equals(toUnit.getClass())) {
                throw new IllegalArgumentException("Cannot convert across different measurement categories.");
            }

            Quantity originalQuantity = new Quantity(request.value(), fromUnit);
            Quantity convertedQuantity = originalQuantity.convertTo(toUnit);
            double finalResult = convertedQuantity.getValue();

            // Layer Integration: Save to DB with secure caller mapping (only if logged in)
            String currentUserEmail = resolveCurrentUserEmail();
            if (currentUserEmail != null) {
                ConversionHistory history = new ConversionHistory(request.value(), request.fromUnit(), request.toUnit(), finalResult, currentUserEmail);
                historyRepository.save(history);
            }

            logger.info("Conversion successful. Result: {}", finalResult);
            return new MeasurementResponseDTO(finalResult, request.toUnit());

        } catch (Exception e ) {
            logger.error("Conversion failed: {}", e.getMessage());
            return MeasurementResponseDTO.error(e.getMessage());
        }
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"}) // We handle generic type safety internally
    public MeasurementResponseDTO performArithmetic(ArithmeticRequestDTO request) {
        logger.info("Received arithmetic request: {} {} {} -> target: {}", request.value1(), request.unit1(), request.operation(), request.resultUnit());
        try {
            Unit unit1 = UnitResolver.resolveUnit(request.unit1());
            Unit resultUnit = UnitResolver.resolveUnit(request.resultUnit());

            if (!unit1.getClass().equals(resultUnit.getClass())) {
                throw new IllegalArgumentException("Cannot perform arithmetic across different measurement categories.");
            }

            Quantity q1 = new Quantity(request.value1(), unit1);
            Quantity finalQuantity;

            if ("DIVIDE".equalsIgnoreCase(request.operation())) {
                // Scalar division
                finalQuantity = q1.divide(request.value2(), resultUnit);
            } else {
                Unit unit2 = UnitResolver.resolveUnit(request.unit2());
                if (!unit1.getClass().equals(unit2.getClass())) {
                    throw new IllegalArgumentException("Cannot perform arithmetic across different measurement categories.");
                }
                Quantity q2 = new Quantity(request.value2(), unit2);

                if ("ADD".equalsIgnoreCase(request.operation())) {
                    finalQuantity = q1.add(q2, resultUnit);
                } else if ("SUBTRACT".equalsIgnoreCase(request.operation())) {
                    finalQuantity = q1.subtract(q2, resultUnit);
                } else {
                    throw new IllegalArgumentException("Unsupported operation: " + request.operation());
                }
            }

            double finalResult = finalQuantity.getValue();

            // Save to DB with secure caller mapping (only if logged in)
            String currentUserEmail = resolveCurrentUserEmail();
            if (currentUserEmail != null) {
                ArithmeticHistory history = new ArithmeticHistory(
                        request.value1(), request.unit1(),
                        request.value2(), request.unit2(),
                        request.operation().toUpperCase(), finalResult, request.resultUnit(), currentUserEmail
                );
                arithmeticHistoryRepository.save(history);
            }

            logger.info("Arithmetic successful. Result: {}", finalResult);
            return new MeasurementResponseDTO(finalResult, request.resultUnit());

        } catch (Exception e) {
            logger.error("Arithmetic failed: {}", e.getMessage());
            return MeasurementResponseDTO.error(e.getMessage());
        }
    }

    @Override
    public List<HistoryItemDTO> getHistory(String email) {
        List<HistoryItemDTO> items = new ArrayList<>();

        List<ConversionHistory> convs = historyRepository.findByUserEmailOrderByCreatedAtDesc(email);
        for (ConversionHistory ch : convs) {
            String detail = String.format("Converted %.2f %s to %s -> %.2f",
                    ch.getInputValue(), ch.getInputUnit(), ch.getTargetUnit(), ch.getResultValue());
            items.add(new HistoryItemDTO(ch.getId(), "CONVERSION", detail, ch.getCreatedAt()));
        }

        List<ArithmeticHistory> ariths = arithmeticHistoryRepository.findByUserEmailOrderByCreatedAtDesc(email);
        for (ArithmeticHistory ah : ariths) {
            String detail;
            if ("DIVIDE".equalsIgnoreCase(ah.getOperation())) {
                detail = String.format("Divided %.2f %s by scalar %.2f -> %.2f %s",
                        ah.getValue1(), ah.getUnit1(), ah.getValue2(), ah.getResultValue(), ah.getResultUnit());
            } else {
                detail = String.format("Performed %s: %.2f %s and %.2f %s -> %.2f %s",
                        ah.getOperation(), ah.getValue1(), ah.getUnit1(), ah.getValue2(), ah.getUnit2(), ah.getResultValue(), ah.getResultUnit());
            }
            items.add(new HistoryItemDTO(ah.getId(), "ARITHMETIC", detail, ah.getCreatedAt()));
        }

        return items.stream()
                .sorted(Comparator.comparing(HistoryItemDTO::timestamp).reversed())
                .collect(Collectors.toList());
    }
}
