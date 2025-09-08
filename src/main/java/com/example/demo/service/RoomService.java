package com.example.demo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.dto.room.RoomCreateRequest;
import com.example.demo.dto.room.RoomResponse;
import com.example.demo.dto.room.RoomSimpleResponse;
import com.example.demo.dto.room.RoomTodayResponse;
import com.example.demo.dto.room.RoomUpdateRequest;
import com.example.demo.dto.transaction.TransactionSimpleResponse;
import com.example.demo.entity.RoomEntity;

import com.example.demo.mapper.RoomMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class RoomService {
    public final RoomRepository roomRepository;
    private final PasscodeService passcodeService;

    private final TransactionRepository transactionRepository;

    public List<RoomSimpleResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::toRoomSimpleResponse)
                .toList();
    }

    public RoomResponse getById(String id) {
        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found: " + id));
        return RoomMapper.toResponse(room);
    }

    public RoomResponse createRoom(RoomCreateRequest req) {
        RoomEntity room = RoomEntity.builder()
                .roomCode(req.getRoomCode())
                .roomName(req.getRoomName())
                .roomCapacity(req.getRoomCapacity())
                .roomType(req.getRoomType())
                .picUrl(req.getPicUrl())
                .picFileName(req.getPicFileName())
                .roomColorTag(req.getRoomColorTag())
                .activeRoom(req.getActiveRoom())
                .roomLocation(req.getRoomLocation())
                .roomDimension(req.getRoomDimension())
                .roomDuration(req.getRoomDuration())
                .calendarId(req.getCalendarId())
                .inUsed(req.getInUsed())
                .passcode(passcodeService.generateUnique())
                .build();

        RoomEntity saved = roomRepository.save(room);
        return RoomMapper.toResponse(saved);
    }

    public RoomResponse updateRoom(String id, RoomUpdateRequest r) {
        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found: " + id));

        room.setRoomCode(r.getRoomCode());
        room.setRoomName(r.getRoomName());
        room.setRoomCapacity(r.getRoomCapacity());
        room.setRoomType(r.getRoomType());
        room.setPicUrl(r.getPicUrl());
        room.setPicFileName(r.getPicFileName());
        room.setRoomColorTag(r.getRoomColorTag());
        room.setActiveRoom(r.getActiveRoom());
        room.setRoomLocation(r.getRoomLocation());
        room.setRoomDimension(r.getRoomDimension());
        room.setRoomDuration(r.getRoomDuration());
        room.setCalendarId(r.getCalendarId());
        room.setInUsed(r.getInUsed());

        RoomEntity updated = roomRepository.save(room);
        return RoomMapper.toResponse(updated);
    }

    public void deleteRoom(String id) {
        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found: " + id));
        roomRepository.delete(room);
    }

    public RoomTodayResponse getRoomWithTodayTransactions(String roomId) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Jakarta"));
        List<TransactionSimpleResponse> txs = transactionRepository
                .findByRoomIdAndBookingDateStartOrderByStartTimeAsc(roomId, today)
                .stream()
                .map(TransactionMapper::toSimpleResponse)
                .toList();

        return buildRoomTodayResponse(room, txs);
    }

    private RoomTodayResponse buildRoomTodayResponse(RoomEntity room,
            List<TransactionSimpleResponse> txs) {
        return RoomTodayResponse.builder()
                .createdById(room.getCreatedById())
                .updatedById(room.getUpdatedById())
                .deletedById(room.getDeletedById())
                .createdDate(room.getCreatedDate() != null ? room.getCreatedDate().toString() : null)
                .updateDate(room.getUpdateDate() != null ? room.getUpdateDate().toString() : null)
                .id(room.getId())
                .roomCode(room.getRoomCode())
                .roomName(room.getRoomName())
                .roomCapacity(room.getRoomCapacity())
                .roomType(room.getRoomType())
                .picUrl(room.getPicUrl())
                .picFileName(room.getPicFileName())
                .roomColorTag(room.getRoomColorTag())
                .activeRoom(room.getActiveRoom())
                .roomLocation(room.getRoomLocation())
                .roomDimension(room.getRoomDimension())
                .roomDuration(room.getRoomDuration())
                .calendarId(room.getCalendarId())
                .passcode(room.getPasscode())
                .inUsed(room.getInUsed())
                .transactions(txs)
                .build();

    }

}
