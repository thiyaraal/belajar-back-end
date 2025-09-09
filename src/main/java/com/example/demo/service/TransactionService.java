package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.dto.transaction.ReservationCreateRequest;
import com.example.demo.dto.transaction.TransactionResponse;
import com.example.demo.dto.transaction.TransactionSimpleResponse;
import com.example.demo.entity.RoomEntity;
import com.example.demo.entity.TransactionEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TransactionRepository;
import java.time.Duration;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class TransactionService {
    private final RoomRepository roomRepository;
    private final TransactionRepository transactionRepository;

    public TransactionResponse reserve(String roomId, ReservationCreateRequest req) {

        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));

        if (req.getBookingDateStart() == null || req.getStartTime() == null || req.getEndTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "bookingDateStart, startTime, and endTime are required");
        }
        if (!req.getEndTime().isAfter(req.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "endTime must be after startTime");
        }

        long durationMinutes = Duration.between(req.getStartTime(), req.getEndTime()).toMinutes();
        if (durationMinutes < 15) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Minimum booking duration is 15 minutes");
        }

        Integer maxHour = room.getRoomDuration();
        if (maxHour != null && maxHour > 0) {
            long maxMinutes = maxHour * 60L;
            if (durationMinutes > maxMinutes) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Maximum booking duration for this room is " + maxHour + " hours");
            }
        }

        boolean clash = transactionRepository.existsOverlap(
                room.getId(),
                req.getBookingDateStart(),
                req.getStartTime(),
                req.getEndTime());
        if (clash) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Time slot already booked");
        }

        TransactionEntity tx = TransactionEntity.builder()
                .bookingDateStart(req.getBookingDateStart())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .room(room)
                .meetingDesc(req.getMeetingDesc())
                .bookedBy(req.getBookedBy())
                .state(req.getState() != null ? req.getState() : "BOOKED")
                .meetingType(req.getMeetingType())
                .participants(req.getParticipants())
                .build();

        TransactionEntity saved = transactionRepository.save(tx);
        return TransactionMapper.toResponse(saved);
    }

    // public TransactionResponse getById(String id) {
    //     TransactionEntity tx = transactionRepository.findById(id)
    //             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found: " + id));
    //     return TransactionMapper.toResponse(tx);
    // }

    // public void deleteById(String id) {
    //     TransactionEntity tx = transactionRepository.findById(id)
    //             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found: " + id));
    //     transactionRepository.delete(tx);
    // }

    public List<TransactionSimpleResponse> getTodayTransactionsByRoomId(String roomId) {
        return transactionRepository.findTodayTransactionsByRoomId(roomId)
                .stream()
                .map(TransactionMapper::toSimpleResponse)
                .toList();
    }

    public TransactionResponse getByIdForRoom(String txId, String roomIdFromToken) {
        TransactionEntity tx = transactionRepository.findById(txId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        String txRoomId = tx.getRoom().getId();
        if (!roomIdFromToken.equals(txRoomId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Transaction not in your room");
        }
        return TransactionMapper.toResponse(tx);
    }

    public void deleteByIdForRoom(String txId, String roomIdFromToken) {
        TransactionEntity tx = transactionRepository.findById(txId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        if (!roomIdFromToken.equals(tx.getRoom().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Transaction not in your room");
        }
        transactionRepository.delete(tx);
    }

}
