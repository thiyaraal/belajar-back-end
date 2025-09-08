package com.example.demo.dto.room;

import java.util.List;

import com.example.demo.dto.transaction.TransactionSimpleResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomTodayResponse {
    private String createdById;
    private String updatedById;
    private String deletedById;
    private String createdDate;
    private String updateDate;
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
    private List<TransactionSimpleResponse> transactions;

}
