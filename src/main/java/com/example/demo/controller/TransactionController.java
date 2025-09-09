package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.dto.common.ApiResponse;
import com.example.demo.dto.transaction.ReservationCreateRequest;
import com.example.demo.dto.transaction.TransactionResponse;
import com.example.demo.dto.transaction.TransactionSimpleResponse;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.security.TokenRoomId;
import com.example.demo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionRepository transactionRepository,
            TransactionService transactionService) {

        this.transactionService = transactionService;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(
            @PathVariable String transactionId,
            @TokenRoomId String tokenRoomId) {

        TransactionResponse tx = transactionService.getByIdForRoom(transactionId, tokenRoomId);
        return ResponseEntity.ok(ApiResponse.ok("Transaction found", tx));
    }

    @GetMapping("/{roomId}/today")
    public ResponseEntity<ApiResponse<List<TransactionSimpleResponse>>> getTodayTransactions(
            @PathVariable String roomId,
            @TokenRoomId String tokenRoomId) {

        if (!roomId.equals(tokenRoomId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token not valid for this room");
        }

        List<TransactionSimpleResponse> result = transactionService.getTodayTransactionsByRoomId(roomId);
        return ResponseEntity.ok(ApiResponse.ok("Success", result));
    }

    @PostMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Object>> reserve(
            @PathVariable String roomId,
            @TokenRoomId String tokenRoomId,
            @RequestBody @Valid ReservationCreateRequest req,
            @RequestHeader("Authorization") String authHeader) {

        if (!roomId.equals(tokenRoomId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token not valid for this room");
        }

        TransactionResponse response = transactionService.reserve(roomId, req);
        return ResponseEntity.ok(ApiResponse.ok("Reservation created", response));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(
            @PathVariable String transactionId,
            @TokenRoomId String tokenRoomId) {

        transactionService.deleteByIdForRoom(transactionId, tokenRoomId);
        return ResponseEntity.ok(ApiResponse.ok("Transaction deleted successfully", null));

    }
}
