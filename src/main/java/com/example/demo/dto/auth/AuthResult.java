package com.example.demo.dto.auth;

import com.example.demo.dto.room.RoomResponse;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResult {
    private RoomResponse room;
}