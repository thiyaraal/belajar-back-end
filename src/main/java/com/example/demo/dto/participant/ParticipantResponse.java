package com.example.demo.dto.participant;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantResponse {
    private String createdById;
    private String updatedById;
    private String createdDate;
    private String updateDate;
    private String id;
    private String companyCode;
    private String name;
    private String email;
    private String handphone;
}