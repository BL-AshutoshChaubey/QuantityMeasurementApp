package com.bridgelabz.quantitymeasurementApp.controller;

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
}
