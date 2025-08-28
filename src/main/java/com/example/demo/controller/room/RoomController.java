package com.example.demo.controller.room;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.api.ApiResponse;
import com.example.demo.dto.room.RoomCreateRequest;
import com.example.demo.dto.room.RoomResponse;
import com.example.demo.dto.room.RoomUpdateRequest;
import com.example.demo.entity.room.RoomEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.repository.RoomRepository;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/find-all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> result = roomRepository.findAll()
                .stream().map(RoomMapper::toResponse).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find-room/{id}")
    public ResponseEntity<ApiResponse<RoomResponse>> getRoomById(@PathVariable String id) {
        var room = roomRepository.findById(id)
                .map(RoomMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));
        return ResponseEntity.ok(ApiResponse.ok("Room found", room));
    }

    @PostMapping("/create-room")
    public ResponseEntity<ApiResponse<RoomResponse>> create(
            @Valid @RequestBody RoomCreateRequest req,
            UriComponentsBuilder uri) {

        RoomEntity e = RoomEntity.builder()
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
                .passcode(req.getPasscode())
                .inUsed(req.getInUsed())
                .build();

        RoomEntity saved = roomRepository.save(e);
        var body = RoomMapper.toResponse(saved);

        return ResponseEntity
                .created(uri.path("/rooms/{id}").buildAndExpand(saved.getId()).toUri())
                .body(ApiResponse.created("Room successfully added", body));
    }

    @PutMapping("/update-room/{id}")
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(@PathVariable String id,
            @Valid @RequestBody RoomUpdateRequest payload) {
        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + id));

        room.setRoomName(payload.getRoomName());
        room.setRoomCode(payload.getRoomCode());
        room.setRoomType(payload.getRoomType());
        room.setRoomCapacity(payload.getRoomCapacity());
        room.setPicUrl(payload.getPicUrl());
        room.setPicFileName(payload.getPicFileName());
        room.setRoomColorTag(payload.getRoomColorTag());
        room.setActiveRoom(payload.getActiveRoom());
        room.setRoomLocation(payload.getRoomLocation());
        room.setRoomDimension(payload.getRoomDimension());
        room.setCalendarId(payload.getCalendarId());
        room.setInUsed(payload.getInUsed());
        RoomEntity saved = roomRepository.save(room);
        RoomResponse body = RoomMapper.toResponse(saved);

        return ResponseEntity.ok(ApiResponse.ok("Room successfully updated", body));

    }

    @DeleteMapping("/delete-room/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable String id) {
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));

        roomRepository.delete(room);
        return ResponseEntity.ok(ApiResponse.ok("Room successfully deleted", null));
    }

}