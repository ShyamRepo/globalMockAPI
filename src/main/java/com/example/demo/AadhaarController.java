package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AadhaarController {

    // POST /api/verify-aadhaar
    @PostMapping("/verify-aadhaar")
    public ResponseEntity<?> verifyAadhaar(@RequestBody Map<String, String> request) {
        String aadhaarNumber = request.get("aadhaarNumber");

        if (aadhaarNumber == null || !aadhaarNumber.matches("\\d{12}")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid Aadhaar number format."));
        }

        // For mock: treat 123412341234 as valid, others invalid
        boolean isValid = "123412341234".equals(aadhaarNumber);

        if (isValid) {
            return ResponseEntity.ok(Map.of(
                    "aadhaarNumber", aadhaarNumber,
                    "isValid", true,
                    "message", "Aadhaar number is valid."
            ));
        } else {
            return ResponseEntity.ok(Map.of(
                    "aadhaarNumber", aadhaarNumber,
                    "isValid", false,
                    "message", "Aadhaar number is not valid."
            ));
        }
    }

    // GET /api/aadhaar-details/{aadhaarNumber}
    @GetMapping("/aadhaar-details/{aadhaarNumber}")
    public ResponseEntity<?> getAadhaarDetails(@PathVariable String aadhaarNumber) {
        if (!aadhaarNumber.matches("\\d{12}")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid Aadhaar number format."));
        }

        if ("123412341234".equals(aadhaarNumber)) {
            return ResponseEntity.ok(Map.of(
                    "aadhaarNumber", aadhaarNumber,
                    "name", "Shyam DHeer",
                    "dob", "1985-06-15",
                    "gender", "Male",
                    "address", Map.of(
                            "house", "123",
                            "street", "MG Road",
                            "city", "New Delhi",
                            "state", "Karnataka",
                            "pincode", "560001"
                    )
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Aadhaar number not found."));
        }
    }

    // POST /api/update-address
    @PostMapping("/update-address")
    public ResponseEntity<?> updateAddress(@RequestBody Map<String, Object> request) {
        String aadhaarNumber = (String) request.get("aadhaarNumber");
        Map<String, String> newAddress = (Map<String, String>) request.get("newAddress");

        if (aadhaarNumber == null || !aadhaarNumber.matches("\\d{12}")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid Aadhaar number format."));
        }

        if (!"123412341234".equals(aadhaarNumber)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Aadhaar number not found."));
        }

        // For mock, we just return success with the address provided.
        return ResponseEntity.ok(Map.of(
                "message", "Address updated successfully.",
                "aadhaarNumber", aadhaarNumber,
                "updatedAddress", newAddress
        ));
    }
}