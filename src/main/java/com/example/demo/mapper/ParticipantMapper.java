package com.example.demo.mapper;

import com.example.demo.dto.participant.ParticipantResponse;
import com.example.demo.dto.participant.ParticipantSimpleResponse;
import com.example.demo.entity.ParticipantEntity;

import java.time.format.DateTimeFormatter;

public class ParticipantMapper {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static ParticipantResponse toResponse(ParticipantEntity p) {
        return ParticipantResponse.builder()
                .createdById(p.getCreatedById())
                .updatedById(p.getUpdatedById())
                .createdDate(p.getCreatedDate() != null ? p.getCreatedDate().format(FMT) : null)
                .updateDate(p.getUpdateDate() != null ? p.getUpdateDate().format(FMT) : null)
                .id(p.getId())
                .companyCode(p.getCompanyCode())
                .name(p.getName())
                .email(p.getEmail())
                .handphone(p.getHandphone())
                .build();
    }

    public static ParticipantSimpleResponse toSimpleResponse(ParticipantEntity p) {
        return ParticipantSimpleResponse.builder()
                .id(p.getId())
                .companyCode(p.getCompanyCode())
                .name(p.getName())
                .email(p.getEmail())
                .handphone(p.getHandphone())
                .build();
    }
}
