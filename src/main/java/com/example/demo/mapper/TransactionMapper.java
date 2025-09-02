package com.example.demo.mapper;

import com.example.demo.dto.transaction.TransactionResponse;
import com.example.demo.entity.TransactionEntity;

import java.time.*;
import java.time.format.DateTimeFormatter;

public final class TransactionMapper {
    private static final ZoneOffset ZERO = ZoneOffset.UTC;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter ISO_WITH_MILLIS_Z = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private TransactionMapper() {
    }

    public static TransactionResponse toResponse(TransactionEntity e) {

        String bookingStartStr = null;
        String bookingFinishStr = null;

        if (e.getStartTime() != null) {
            OffsetDateTime odtStart = OffsetDateTime.of(
                    LocalDate.of(1970, 1, 1),
                    e.getStartTime(),
                    ZERO);
            bookingStartStr = odtStart.format(ISO_WITH_MILLIS_Z);
        }
        if (e.getEndTime() != null) {
            OffsetDateTime odtEnd = OffsetDateTime.of(
                    LocalDate.of(1970, 1, 1),
                    e.getEndTime(),
                    ZERO);
            bookingFinishStr = odtEnd.format(ISO_WITH_MILLIS_Z);
        }

        return TransactionResponse.builder()
                .createdById(e.getCreatedById())
                .updatedById(e.getUpdatedById())
                .createdDate(e.getCreatedDate())
                .updateDate(e.getUpdateDate())
                .id(e.getId())
                .bookingDateStart(e.getBookingDateStart() != null ? e.getBookingDateStart().format(DATE_FMT) : null)
                .bookingStart(bookingStartStr)
                .bookingFinish(bookingFinishStr)
                .meetingDesc(e.getMeetingDesc())
                .bookedBy(e.getBookedBy())
                .state(e.getState())
                .meetingType(e.getMeetingType())
                .participants(e.getParticipants())
                .build();
    }
}