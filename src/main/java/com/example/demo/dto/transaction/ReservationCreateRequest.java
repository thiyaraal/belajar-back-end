package com.example.demo.dto.transaction;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationCreateRequest {
    @NotNull
    private LocalDate bookingDateStart;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    @NotBlank
    private String meetingDesc;
    @NotBlank
    private String bookedBy;
    @NotBlank
    private String meetingType;
    @NotBlank
    private String participants;

    private String state;
}