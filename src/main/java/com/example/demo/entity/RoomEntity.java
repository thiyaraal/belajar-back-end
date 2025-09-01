package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "rooms", uniqueConstraints = @UniqueConstraint(name = "uk_rooms_passcode", columnNames = "passcode"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RoomEntity extends BaseEntity {

    private String roomCode;
    private String roomName;
    private String roomType;
    private Integer roomCapacity;
    private String picUrl;
    private String picFileName;
    private String roomColorTag;
    private Boolean activeRoom;
    private String roomLocation;
    private String roomDimension;
    private Integer roomDuration;
    private String calendarId;
    @Column(nullable = false, length = 16)
    private String passcode;
    private Boolean inUsed;

}
