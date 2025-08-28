package com.example.demo.controller;

import com.example.demo.dto.auth.AuthLoginResponse;
import com.example.demo.dto.auth.AuthResult;
import com.example.demo.dto.auth.PasscodeLoginRequest;
import com.example.demo.dto.room.RoomResponse;
import com.example.demo.entity.RoomEntity;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RoomRepository roomRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> loginByPasscode(@Valid @RequestBody PasscodeLoginRequest req) {
        RoomEntity room = roomRepository.findByPasscode(req.getPasscode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid passcode"));

        if (Boolean.TRUE.equals(room.getActiveRoom())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Room already active, cannot login again");
        }

        String token = UUID.randomUUID().toString().replace("-", "");

        room.setActiveRoom(true);
        roomRepository.save(room);

        RoomResponse roomResp = RoomMapper.toResponse(room);

        AuthLoginResponse payload = AuthLoginResponse.builder()
                .success(true)
                .msg("Authentication successful")
                .token(token)
                .result(AuthResult.builder().room(roomResp).build())
                .build();

        return ResponseEntity.ok(payload);
    }

}