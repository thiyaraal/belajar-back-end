package com.example.demo.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.example.demo.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasscodeService {
    private final RoomRepository roomRepository;
    private static final SecureRandom RND = new SecureRandom();
    private static final int LEN = 16;

    public String generateUnique() {
        for (int tries = 0; tries < 100; tries++) {
            String code = randomDigits(LEN);
            if (!roomRepository.existsByPasscode(code)) return code;
        }
        throw new IllegalStateException("Failed to generate unique passcode after many attempts");
    }

    private String randomDigits(int len) {
        char[] out = new char[len];
        for (int i = 0; i < len; i++) {
            out[i] = (char) ('0' + RND.nextInt(10));
        }
        return new String(out);
    }
}
