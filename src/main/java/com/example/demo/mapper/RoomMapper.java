package com.example.demo.mapper;

import java.time.format.DateTimeFormatter;

import com.example.demo.dto.RoomResponse;
import com.example.demo.entity.RoomEntity;

public class RoomMapper {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static RoomResponse toResponse(RoomEntity e) {

        return RoomResponse.builder()
                .createdById(e.getCreatedById())
                .updatedById(e.getUpdatedById())
                .createdDate(e.getCreatedDate() != null ? e.getCreatedDate().format(FMT) : null)
                .updateDate(e.getUpdateDate() != null ? e.getUpdateDate().format(FMT) : null)
                .id(e.getId())
                .roomCode(e.getRoomCode())
                .roomName(e.getRoomName())
                .roomCapacity(e.getRoomCapacity())
                .roomType(e.getRoomType())
                .picUrl(e.getPicUrl())
                .picFileName(e.getPicFileName())
                .roomColorTag(e.getRoomColorTag())
                .activeRoom(e.getActiveRoom())
                .roomLocation(e.getRoomLocation())
                .roomDimension(e.getRoomDimension())
                .roomDuration(e.getRoomDuration())
                .calendarId(e.getCalendarId())
                .passcode(e.getPasscode())
                .inUsed(e.getInUsed())
                .build();
    }
}