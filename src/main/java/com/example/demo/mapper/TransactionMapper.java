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
    private static final DateTimeFormatter TIME_HHMM = DateTimeFormatter.ofPattern("HH:mm");

    private TransactionMapper() {
    }

    public static TransactionResponse toResponse(TransactionEntity e) {

        String bookingStartStr = null;
        String bookingFinishStr = null;
        String strStart = null;
        String strFinish = null;

        if (e.getStartTime() != null) {
            OffsetDateTime odtStart = OffsetDateTime.of(
                    LocalDate.of(1970, 1, 1),
                    e.getStartTime(),
                    ZERO);
            bookingStartStr = odtStart.format(ISO_WITH_MILLIS_Z);
            strStart = e.getStartTime().format(TIME_HHMM);
        }
        if (e.getEndTime() != null) {
            OffsetDateTime odtEnd = OffsetDateTime.of(
                    LocalDate.of(1970, 1, 1),
                    e.getEndTime(),
                    ZERO);
            bookingFinishStr = odtEnd.format(ISO_WITH_MILLIS_Z);
            strFinish = e.getEndTime().format(TIME_HHMM);
        }

        return TransactionResponse.builder()
                .createdById(e.getCreatedById())
                .updatedById(e.getUpdatedById())
                .createdDate(e.getCreatedDate())
                .updateDate(e.getUpdateDate())
                .id(e.getId())
                .bookingCode(e.getBookedBy())
                .bookingDateStart(e.getBookingDateStart() != null ? e.getBookingDateStart().format(DATE_FMT) : null)
                .bookingStart(bookingStartStr)
                .bookingFinish(bookingFinishStr)
                .roomCode(e.getRoomCode())
                .meetingDesc(e.getMeetingDesc())
                .bookedBy(e.getBookedBy())
                .state(e.getState())
                .meetingType(e.getMeetingType())
                .participants(e.getParticipants())
                .eventId(e.getEventId())
                .strBookingDateStart(e.getBookingDateStart() != null ? e.getBookingDateStart().format(DATE_FMT) : null)
                .strBookingStart(strStart)
                .strBookingFinish(strFinish)
                .roomName(e.getRoomName())
                .strParticipants(e.getParticipants())
                .build();
    }
}