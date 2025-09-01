package com.example.demo.dto.room;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomUpdateRequest {

    @NotBlank
    private String roomCode;
    @NotBlank
    private String roomName;
    @NotNull
    @Min(1)
    private Integer roomCapacity;
    private String roomType;
    private String picUrl;
    private String picFileName;
    private String roomColorTag;
    @NotNull
    private Boolean activeRoom;
    private String roomLocation;
    private String roomDimension;
    private Integer roomDuration;
    private String calendarId;
    private Boolean inUsed;
}