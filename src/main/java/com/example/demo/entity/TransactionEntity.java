package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity extends BaseEntity {

    private String bookingCode;
    private LocalDate bookingDateStart;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoomEntity room;

    private String roomCode;
    private String roomName;
    private String meetingDesc;
    private String bookedBy;
    private String state;
    private String meetingType;
    private String participants;
    private String eventId;
}
