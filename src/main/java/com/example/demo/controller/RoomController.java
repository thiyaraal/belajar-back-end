package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.dto.common.ApiResponse;
import com.example.demo.dto.room.RoomCreateRequest;
import com.example.demo.dto.room.RoomResponse;
import com.example.demo.dto.room.RoomUpdateRequest;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.PasscodeService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.RoomService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/rooms")
public class RoomController {

        private final RoomService roomService;

        public RoomController(RoomRepository roomRepository,
                        PasscodeService passcodeService,
                        TransactionRepository transactionRepository,
                        TransactionService reservationService,
                        RoomService roomService) {

                this.roomService = roomService;

        }

        @GetMapping("/find-all")
        public ResponseEntity<List<RoomResponse>> getAllRooms() {
                List<RoomResponse> result = roomService.getAllRooms()
                                .stream().map(RoomMapper::toResponse).toList();
                return ResponseEntity.ok(result);
        }

        @GetMapping("/find-room/{id}")
        public ResponseEntity<ApiResponse<RoomResponse>> getRoomById(@PathVariable String id) {
                RoomResponse room = roomService.getById(id);
                return ResponseEntity.ok(ApiResponse.ok("Room found", room));
        }

        @PostMapping("/create-room")
        public ResponseEntity<ApiResponse<RoomResponse>> createRoom(
                        @Valid @RequestBody RoomCreateRequest req,
                        UriComponentsBuilder uri) {

                RoomResponse saved = roomService.createRoom(req);

                URI location = uri.path("/rooms/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

                return ResponseEntity
                                .created(location)
                                .body(ApiResponse.created("Room successfully added", saved));
        }

        @PutMapping("/update-room/{id}")
        public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(
                        @PathVariable String id,
                        @RequestBody RoomUpdateRequest r) {

                return ResponseEntity.ok(
                                ApiResponse.ok("Room successfully updated", roomService.updateRoom(id, r)));
        }

        @DeleteMapping("/delete-room/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable String id) {
                roomService.deleteRoom(id);
                return ResponseEntity.ok(ApiResponse.ok("Room successfully deleted", null));
        }

}