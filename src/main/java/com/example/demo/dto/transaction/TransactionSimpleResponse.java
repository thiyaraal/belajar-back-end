package com.example.demo.dto.transaction;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransactionSimpleResponse {

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