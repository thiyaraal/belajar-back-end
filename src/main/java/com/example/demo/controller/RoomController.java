package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.demo.api.ApiResponse;
import com.example.demo.dto.room.RoomCreateRequest;
import com.example.demo.dto.room.RoomResponse;
import com.example.demo.dto.room.RoomUpdateRequest;
import com.example.demo.dto.transaction.ReservationCreateRequest;
import com.example.demo.entity.RoomEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.PasscodeService;
import com.example.demo.service.ReservationService;

import jakarta.validation.Valid;
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
        private final RoomRepository roomRepository;
        private final PasscodeService passcodeService;
        private final ReservationService reservationService;

        public RoomController(RoomRepository roomRepository,
                        PasscodeService passcodeService,
                        TransactionRepository transactionRepository,
                        ReservationService reservationService) {
                this.roomRepository = roomRepository;
                this.passcodeService = passcodeService;
                this.reservationService = reservationService;

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

                                .inUsed(req.getInUsed())
                                .build();

                String passcode = passcodeService.generateUnique();
                e.setPasscode(passcode);

                RoomEntity saved = roomRepository.save(e);

                var body = RoomMapper.toResponse(saved);

                return ResponseEntity
                                .created(uri.path("/rooms/{id}").buildAndExpand(saved.getId()).toUri())
                                .body(ApiResponse.created("Room successfully added", body));
        }

        @PutMapping("/update-room/{id}")
        public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(
                        @PathVariable String id,
                        @Valid @RequestBody RoomUpdateRequest req) {

                var entity = roomRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));

                RoomMapper.applyUpdate(entity, req);

                var saved = roomRepository.save(entity);

                return ResponseEntity.ok(
                                ApiResponse.ok("Room successfully updated", RoomMapper.toResponse(saved)));
        }

        @DeleteMapping("/delete-room/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable String id) {
                var room = roomRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));

                roomRepository.delete(room);
                return ResponseEntity.ok(ApiResponse.ok("Room successfully deleted", null));
        }

        @PostMapping("/{roomId}/reservations")
        public ResponseEntity<ApiResponse<Object>> reserve(
                        @PathVariable String roomId,
                        @Valid @RequestBody ReservationCreateRequest req) {

                var response = reservationService.reserve(roomId, req);
                return ResponseEntity.ok(ApiResponse.ok("Reservation created", response));
        }

       
}