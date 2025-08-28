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
    @Min(1)
    private Integer roomCapacity;

    private String roomType;
    private String picUrl;
    private String picFileName;
    private String roomColorTag;
    private Boolean activeRoom;
    private String roomLocation;
    private String roomDimension;
    private Integer roomDuration;
    private String calendarId;
    private String passcode;
    private String inUsed;

}
