package com.example.demo.mapper;

import java.time.format.DateTimeFormatter;

import com.example.demo.dto.room.RoomResponse;
import com.example.demo.dto.room.RoomUpdateRequest;
import com.example.demo.entity.RoomEntity;

public class RoomMapper {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static RoomResponse toResponse(RoomEntity e) {

        return RoomResponse.builder()
                .createdById(e.getCreatedById())
                .updatedById(e.getUpdatedById())
                .deletedById(null)
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

    public static void applyUpdate(RoomEntity e, RoomUpdateRequest r) {
        e.setRoomCode(r.getRoomCode());
        e.setRoomName(r.getRoomName());
        e.setRoomCapacity(r.getRoomCapacity());
        e.setRoomType(r.getRoomType());
        e.setPicUrl(r.getPicUrl());
        e.setPicFileName(r.getPicFileName());
        e.setRoomColorTag(r.getRoomColorTag());
        e.setActiveRoom(r.getActiveRoom());
        e.setRoomLocation(r.getRoomLocation());
        e.setRoomDimension(r.getRoomDimension());
        e.setRoomDuration(r.getRoomDuration());
        e.setCalendarId(r.getCalendarId());

        e.setInUsed(r.getInUsed());
    }
}