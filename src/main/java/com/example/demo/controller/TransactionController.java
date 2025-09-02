package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.api.ApiResponse;
import com.example.demo.dto.transaction.ReservationCreateRequest;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.ReservationService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    private final ReservationService reservationService;

    public TransactionController(TransactionRepository transactionRepository, ReservationService reservationService) {
        this.transactionRepository = transactionRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<Object>> getTransactionById(@PathVariable String transactionId) {
        var tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
        return ResponseEntity.ok(ApiResponse.ok("Transaction found", tx));
    }

    @PostMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Object>> reserve(
            @PathVariable String roomId,
            @RequestBody @Valid ReservationCreateRequest req,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        var response = reservationService.reserve(roomId, token, req);
        return ResponseEntity.ok(ApiResponse.ok("Reservation created", response));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<Object>> deleteTransaction(@PathVariable String transactionId) {
        var tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        transactionRepository.delete(tx);
        return ResponseEntity.ok(ApiResponse.ok("Transaction deleted successfully", null));
    }

}
