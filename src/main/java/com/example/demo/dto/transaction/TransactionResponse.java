package com.example.demo.dto.transaction;

import lombok.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransactionResponse {
    private String createdById;
    private String updatedById;
    private OffsetDateTime createdDate;
    private OffsetDateTime updateDate;
    private String id;
    private String bookingDateStart;
    private String bookingStart;
    private String bookingFinish;
    private String meetingDesc;
    private String bookedBy;
    private String state;
    private String meetingType;
    private String participants;
}