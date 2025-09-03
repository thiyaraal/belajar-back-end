package com.example.demo.dto.room;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomSimpleResponse {

    private String id;
    private String roomCode;
    private String roomName;
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
    private Boolean inUsed;
    private Object transactions;

}