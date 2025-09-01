package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.transaction.ReservationCreateRequest;
import com.example.demo.dto.transaction.TransactionResponse;
import com.example.demo.entity.TransactionEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TransactionRepository;
import java.time.Duration;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ReservationService {
    private final RoomRepository roomRepository;
    private final TransactionRepository transactionRepository;

    public TransactionResponse reserve(String roomId, ReservationCreateRequest req) {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));

        if (!req.getEndTime().isAfter(req.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endTime must be after startTime");
        }

        long durationMinutes = Duration.between(req.getStartTime(), req.getEndTime()).toMinutes();

        if (durationMinutes < 15) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum booking duration is 15 minutes");
        }

        int maxDurationPerRoom = room.getRoomDuration();
        if (durationMinutes > maxDurationPerRoom * 60) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Maximum booking duration for this room is " + maxDurationPerRoom + " hours");
        }

        boolean clash = transactionRepository.existsOverlap(
                room.getId(), req.getBookingDateStart(), req.getStartTime(), req.getEndTime());
        if (clash) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Time slot already booked");
        }

        var tx = TransactionEntity.builder()
                .bookingCode(null)
                .bookingDateStart(req.getBookingDateStart())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .room(room)
                .roomCode(room.getRoomCode())
                .roomName(room.getRoomName())
                .meetingDesc(req.getMeetingDesc())
                .bookedBy(req.getBookedBy())
                .state(req.getState() != null ? req.getState() : "BOOKED")
                .meetingType(req.getMeetingType())
                .participants(req.getParticipants())
                .eventId(null)
                .build();

        var saved = transactionRepository.save(tx);
        return TransactionMapper.toResponse(saved);
    }
}
