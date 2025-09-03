package com.example.demo.mapper;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import com.example.demo.dto.room.RoomResponse;
import com.example.demo.dto.room.RoomSimpleResponse;
import com.example.demo.dto.room.RoomUpdateRequest;
import com.example.demo.entity.RoomEntity;

public class RoomMapper {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static RoomResponse toResponse(RoomEntity r) {

        return RoomResponse.builder()
                .createdById(r.getCreatedById())
                .updatedById(r.getUpdatedById())
                .deletedById(null)
                .createdDate(r.getCreatedDate() != null ? r.getCreatedDate().format(FMT) : null)
                .updateDate(r.getUpdateDate() != null ? r.getUpdateDate().format(FMT) : null)
                .id(r.getId())
                .roomCode(r.getRoomCode())
                .roomName(r.getRoomName())
                .roomCapacity(r.getRoomCapacity())
                .roomType(r.getRoomType())
                .picUrl(r.getPicUrl())
                .picFileName(r.getPicFileName())
                .roomColorTag(r.getRoomColorTag())
                .activeRoom(r.getActiveRoom())
                .roomLocation(r.getRoomLocation())
                .roomDimension(r.getRoomDimension())
                .roomDuration(r.getRoomDuration())
                .calendarId(r.getCalendarId())
                .passcode(r.getPasscode())
                .inUsed(r.getInUsed())
                .transactions(
                        r.getTransactions() != null ? r.getTransactions().stream()
                                .map(TransactionMapper::toSimpleResponse)
                                .collect(Collectors.toList())
                                : null)

                .build();
    }

    public static RoomSimpleResponse toRoomSimpleResponse(RoomEntity r) {
        return RoomSimpleResponse.builder()
                .id(r.getId())
                .roomCode(r.getRoomCode())
                .roomName(r.getRoomName())
                .roomCapacity(r.getRoomCapacity())
                .roomType(r.getRoomType())
                .picUrl(r.getPicUrl())
                .picFileName(r.getPicFileName())
                .roomColorTag(r.getRoomColorTag())
                .activeRoom(r.getActiveRoom())
                .roomLocation(r.getRoomLocation())
                .roomDimension(r.getRoomDimension())
                .roomDuration(r.getRoomDuration())
                .calendarId(r.getCalendarId())
                .passcode(r.getPasscode())
                .inUsed(r.getInUsed())
                .transactions(
                        r.getTransactions() != null ? r.getTransactions().stream()
                                .map(TransactionMapper::toSimpleResponse)
                                .collect(Collectors.toList())
                                : null)
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