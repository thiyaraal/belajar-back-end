package com.example.demo.dto.auth;

import lombok.*;
import com.example.demo.dto.room.RoomResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLogoutResponse {
    private boolean success;
    private String msg;
    private RoomResponse room;

}
