package com.example.demo.controller;

import com.example.demo.dto.auth.AuthLoginResponse;
import com.example.demo.dto.auth.AuthLogoutResponse;
import com.example.demo.dto.auth.AuthResult;
import com.example.demo.dto.auth.PasscodeLoginRequest;
import com.example.demo.dto.auth.PasscodeLogoutRequest;
import com.example.demo.dto.room.RoomResponse;
import com.example.demo.entity.RoomEntity;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.repository.RoomRepository;
import com.example.demo.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RoomRepository roomRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> login(@Valid @RequestBody PasscodeLoginRequest req) {
        RoomEntity room = roomRepository.findByPasscode(req.getPasscode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid passcode"));

        if (Boolean.TRUE.equals(room.getInUsed())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Room already active, cannot login again");
        }

        String token = JwtUtil.generateToken(room.getId());

        room.setInUsed(true);
        ;
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

    

    @PostMapping("/logout")
    public ResponseEntity<?> logoutByPasscode(@Valid @RequestBody PasscodeLogoutRequest req) {
        var room = roomRepository.findByPasscode(req.getPasscode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid passcode"));

        if (Boolean.FALSE.equals(room.getInUsed())) {
            var roomResp = RoomMapper.toResponse(room);
            var payload = AuthLogoutResponse.builder()
                    .success(true)
                    .msg("Already logged out")
                    .room(roomResp)
                    .build();
            return ResponseEntity.ok(payload);
        }

        room.setInUsed(false);
        var saved = roomRepository.save(room);
        var roomResp = RoomMapper.toResponse(saved);

        var payload = AuthLogoutResponse.builder()
                .success(true)
                .msg("Logout successful")
                .room(roomResp)
                .build();

        return ResponseEntity.ok(payload);
    }

    @GetMapping("/status/{passcode}")
    public ResponseEntity<?> getStatus(@PathVariable String passcode) {
        var room = roomRepository.findByPasscode(passcode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
        var roomResp = RoomMapper.toResponse(room);
        return ResponseEntity.ok(roomResp);
    }

}