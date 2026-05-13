package com.bridgelabz.quantitymeasurementApp.controller;

import com.bridgelabz.quantitymeasurementApp.DTO.ArithmeticRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.ConversionRequestDTO;
import com.bridgelabz.quantitymeasurementApp.DTO.MeasurementResponseDTO;
import com.bridgelabz.quantitymeasurementApp.service.QuantityMeasurementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/measurements")
public class QuantityMeasurementController {
    private final QuantityMeasurementService service;

    public QuantityMeasurementController(QuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping("/convert")
    public ResponseEntity<MeasurementResponseDTO> convert(@RequestBody ConversionRequestDTO request) {
        MeasurementResponseDTO response = service.convert(request);

        if (response.errorMessage() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/arithmetic")
    public ResponseEntity<MeasurementResponseDTO> arithmetic(@RequestBody ArithmeticRequestDTO request) {
        MeasurementResponseDTO response = service.performArithmetic(request);

        if (response.errorMessage() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<java.util.List<com.bridgelabz.quantitymeasurementApp.DTO.HistoryItemDTO>> getHistory(org.springframework.security.core.Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(service.getHistory(auth.getName()));
    }
}
